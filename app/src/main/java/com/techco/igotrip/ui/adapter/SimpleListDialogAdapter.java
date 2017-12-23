package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.viewholder.SimpleListDialogViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class SimpleListDialogAdapter extends RecyclerView.Adapter<SimpleListDialogViewHolder> {

    private List<String> datas;
    private OnItemClickListener listener;

    public SimpleListDialogAdapter(List<String> datas, OnItemClickListener listener) {
        this.datas = datas;
        this.listener = listener;
    }


    @Override
    public SimpleListDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) parent.getContext().getResources().getDimension(R.dimen.dialog_simple_item_height));
        view.setLayoutParams(layoutParams);
        return new SimpleListDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleListDialogViewHolder holder, int position) {
        holder.bind(datas, position, listener);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
