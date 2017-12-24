package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.ui.viewholder.ProvinceMainViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class ProvinceMainAdapter extends RecyclerView.Adapter<ProvinceMainViewHolder> {

    private List<Province> provinces;
    private OnItemClickListener listener;

    public ProvinceMainAdapter(List<Province> provinces, OnItemClickListener listener) {
        this.provinces = provinces;
        this.listener = listener;
    }

    @Override
    public ProvinceMainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_province_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) parent.getContext().getResources().getDimension(R.dimen.province_item_height));
        view.setLayoutParams(layoutParams);
        return new ProvinceMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProvinceMainViewHolder holder, int position) {
        holder.bind(provinces, position, listener);
    }

    @Override
    public int getItemCount() {
        return provinces.size();
    }
}
