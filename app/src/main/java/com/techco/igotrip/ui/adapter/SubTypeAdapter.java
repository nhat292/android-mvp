package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.ui.viewholder.SubTypeViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class SubTypeAdapter extends RecyclerView.Adapter<SubTypeViewHolder> {

    private List<SubType> subTypes;
    private OnItemClickListener listener;

    public SubTypeAdapter(List<SubType> subTypes, OnItemClickListener listener) {
        this.subTypes = subTypes;
        this.listener = listener;
    }

    @Override
    public SubTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_type_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        return new SubTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubTypeViewHolder holder, int position) {
        holder.bind(subTypes, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return subTypes.size();
    }
}
