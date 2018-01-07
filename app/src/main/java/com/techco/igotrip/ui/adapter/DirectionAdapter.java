package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.response.DResponseResult;
import com.techco.igotrip.ui.viewholder.DirectionViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class DirectionAdapter extends RecyclerView.Adapter<DirectionViewHolder> {

    private List<DResponseResult.DStep> steps;
    private OnItemClickListener listener;

    public DirectionAdapter(List<DResponseResult.DStep> steps, OnItemClickListener listener) {
        this.steps = steps;
        this.listener = listener;
    }

    @Override
    public DirectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new DirectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DirectionViewHolder holder, int position) {
        holder.bind(steps, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
