package com.techco.igotrip.ui.viewholder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.techco.igotrip.BuildConfig;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.adapter.TypeAdapter;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class TypeViewHolder extends BaseViewHolder {

    @BindView(R.id.imgType)
    ImageView imgType;


    public TypeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Type> types, int position, OnItemClickListener listener, TypeAdapter adapter) {
        Type type = types.get(position);
        Glide.with(itemView.getContext())
                .load(BuildConfig.BASE_URL + type.getIcon())
                .into(imgType);
        imgType.setOnClickListener(view -> {
            if (listener != null) {
                for (int i = 0; i < types.size(); i++) {
                    if (i == position) {
                        types.get(i).setSelected(true);
                    } else {
                        types.get(i).setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
                listener.onClick(type, position);
            }
        });
        if (type.isSelected()) {
            imgType.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.accent_black));
        } else {
            imgType.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.colorTypeNormal));
        }
    }

}
