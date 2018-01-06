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

    public static final int TEXT_ALIGN_TYPE_CENTER = 0;
    public static final int TEXT_ALIGN_TYPE_LEFT = 1;

    private List<String> datas;
    private OnItemClickListener listener;
    private int textAlignType;

    public SimpleListDialogAdapter(List<String> datas, OnItemClickListener listener, int textAlignType) {
        this.datas = datas;
        this.listener = listener;
        this.textAlignType = textAlignType;
    }


    @Override
    public SimpleListDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new SimpleListDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleListDialogViewHolder holder, int position) {
        holder.bind(datas, position, listener, textAlignType);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setTextAlignType(int textAlignType) {
        this.textAlignType = textAlignType;
    }
}
