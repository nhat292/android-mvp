package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.ui.custom.itemtouchhelper.ItemTouchHelperAdapter;
import com.techco.igotrip.ui.custom.itemtouchhelper.OnStartDragListener;
import com.techco.igotrip.ui.viewholder.JourneyArticlesViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class JourneyArticlesAdapter extends RecyclerView.Adapter<JourneyArticlesViewHolder> implements ItemTouchHelperAdapter {

    private List<Article> articles;
    private OnItemClickListener listener;
    private final OnStartDragListener mDragStartListener;

    public JourneyArticlesAdapter(List<Article> articles, OnItemClickListener listener, OnStartDragListener dragStartListener) {
        this.articles = articles;
        this.listener = listener;
        this.mDragStartListener = dragStartListener;
    }

    @Override
    public JourneyArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_item, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new JourneyArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JourneyArticlesViewHolder holder, int position) {
        holder.bind(articles, position, listener, this);
        holder.getRlItem().setOnLongClickListener(view -> {
            mDragStartListener.onStartDrag(holder);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(articles, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        articles.remove(position);
        notifyItemRemoved(position);
    }
}
