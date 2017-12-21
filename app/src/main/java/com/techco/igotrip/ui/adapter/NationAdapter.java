package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Nation;
import com.techco.igotrip.ui.viewholder.NationViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class NationAdapter extends RecyclerView.Adapter<NationViewHolder> {

    private List<Nation> nations;
    private OnItemClickListener listener;

    public NationAdapter(List<Nation> nations, OnItemClickListener listener) {
        this.nations = nations;
        this.listener = listener;
    }


    @Override
    public NationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nation_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) parent.getContext().getResources().getDimension(R.dimen.nation_item_height));
        view.setLayoutParams(layoutParams);
        return new NationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NationViewHolder holder, int position) {
        holder.bind(nations, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return nations.size();
    }
}
