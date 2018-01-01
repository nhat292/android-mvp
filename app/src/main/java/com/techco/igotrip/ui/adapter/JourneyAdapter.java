package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.viewholder.JourneyViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class JourneyAdapter extends RecyclerView.Adapter<JourneyViewHolder> {

    private List<Journey> journeys;
    private OnItemClickListener listener;

    public JourneyAdapter(List<Journey> journeys, OnItemClickListener listener) {
        this.journeys = journeys;
        this.listener = listener;
    }

    @Override
    public JourneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trip_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) parent.getContext().getResources().getDimension(R.dimen.my_trip_item_height));
        view.setLayoutParams(layoutParams);
        return new JourneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JourneyViewHolder holder, int position) {
        holder.bind(journeys, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }
}
