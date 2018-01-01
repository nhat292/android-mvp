package com.techco.igotrip.ui.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techco.common.CommonUtils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Comment;
import com.techco.igotrip.ui.adapter.CommentAdapter;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nhat on 12/15/17.
 */

public class CommentViewHolder extends BaseViewHolder {

    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.txtMessage)
    TextView txtMessage;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Comment> comments, int position, OnItemClickListener listener, CommentAdapter adapter) {
        Comment comment = comments.get(position);
        txtName.setText(comment.getUsername() + "(" + comment.getFullname() + ")");
        txtTime.setText(comment.getUpdatedAt());
        Glide.with(itemView.getContext())
                .load(comment.getUserImage())
                .placeholder(R.drawable.bg_avatar)
                .error(R.drawable.bg_avatar)
                .into(imgAvatar);
        if (comment.getEdited() == Comment.EDITED) {
            String content = comment.getContent() + " <font color=\"#f44336\">(Edited)</font>";
            txtMessage.setText(CommonUtils.fromHtml(content));
        } else {
            txtMessage.setText(comment.getContent());
        }
        llRoot.setOnClickListener(view -> listener.onClick(comment, position));
    }

}
