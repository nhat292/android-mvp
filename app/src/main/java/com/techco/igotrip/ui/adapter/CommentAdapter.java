package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Comment;
import com.techco.igotrip.ui.viewholder.CommentViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<Comment> comments;
    private OnItemClickListener listener;

    public CommentAdapter(List<Comment> comments, OnItemClickListener listener) {
        this.comments = comments;
        this.listener = listener;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bind(comments, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
