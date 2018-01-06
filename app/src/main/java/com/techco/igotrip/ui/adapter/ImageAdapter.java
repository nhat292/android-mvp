package com.techco.igotrip.ui.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.viewholder.ImageViewHolder;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Bitmap> bitmaps;
    private OnItemClickListener listener;

    public ImageAdapter(List<Bitmap> bitmaps, OnItemClickListener listener) {
        this.bitmaps = bitmaps;
        this.listener = listener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(bitmaps, position, listener, this);
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }
}
