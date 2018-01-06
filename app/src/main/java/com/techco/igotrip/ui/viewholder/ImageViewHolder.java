package com.techco.igotrip.ui.viewholder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.adapter.ImageAdapter;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class ImageViewHolder extends BaseViewHolder {

    @BindView(R.id.imgItem)
    ImageView imgItem;
    @BindView(R.id.imgDelete)
    ImageView imgDelete;


    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Bitmap> bitmaps, int position, OnItemClickListener listener, ImageAdapter adapter) {
        Bitmap bitmap = bitmaps.get(position);
        imgItem.setImageBitmap(bitmap);
        imgDelete.setOnClickListener(view -> {
            bitmaps.remove(position);
            adapter.notifyItemRemoved(position);
        });
    }

}
