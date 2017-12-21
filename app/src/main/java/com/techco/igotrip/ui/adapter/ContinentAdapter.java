package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Continent;
import com.techco.igotrip.ui.viewholder.ContinentViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class ContinentAdapter extends RecyclerView.Adapter<ContinentViewHolder> {

    private List<Continent> continents;
    private OnItemClickListener listener;

    public ContinentAdapter(List<Continent> continents, OnItemClickListener listener) {
        this.continents = continents;
        this.listener = listener;
    }

    @Override
    public ContinentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.continent_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) parent.getContext().getResources().getDimension(R.dimen.continent_item_height));
        view.setLayoutParams(layoutParams);
        return new ContinentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContinentViewHolder holder, int position) {
        holder.bind(continents, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return continents.size();
    }
}
