package com.techco.igotrip.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techco.common.AppConstants;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.ApiEndPoint;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.ui.adapter.JourneyArticlesAdapter;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;
import com.techco.igotrip.ui.custom.views.SquareRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class JourneyArticlesViewHolder extends BaseViewHolder {

    @BindView(R.id.rlItem)
    SquareRelativeLayout rlItem;
    @BindView(R.id.imgItem)
    ImageView imgItem;
    @BindView(R.id.txtItem)
    TextView txtItem;

    public JourneyArticlesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Article> articles, int position, OnItemClickListener listener, JourneyArticlesAdapter adapter) {
        Article article = articles.get(position);
        txtItem.setText(article.getTitle());
        if (article.getImage().startsWith(AppConstants.HTTP_PREFIX)) {
            Glide.with(itemView.getContext())
                    .load(article.getImage())
                    .into(imgItem);
        } else {
            Glide.with(itemView.getContext())
                    .load(ApiEndPoint.BASE_URL + article.getImage())
                    .into(imgItem);
        }
        rlItem.setOnClickListener(view -> listener.onClick(article, position));
    }

    public SquareRelativeLayout getRlItem() {
        return rlItem;
    }
}
