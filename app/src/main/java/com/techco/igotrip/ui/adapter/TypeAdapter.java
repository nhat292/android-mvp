package com.techco.igotrip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.ui.viewholder.TypeViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeViewHolder> {

    private List<Type> types;
    private OnItemClickListener listener;

    public TypeAdapter(List<Type> types, OnItemClickListener listener) {
        this.types = types;
        this.listener = listener;
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                (int) parent.getContext().getResources().getDimension(R.dimen.recycler_view_type_height),
                ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        holder.bind(types, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
}
