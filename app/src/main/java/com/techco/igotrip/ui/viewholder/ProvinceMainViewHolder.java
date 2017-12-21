package com.techco.igotrip.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techco.igotrip.BuildConfig;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class ProvinceMainViewHolder extends BaseViewHolder {

    public static final short CLICK_TYPE_ITEM = 0;
    public static final short CLICK_TYPE_SHOW_MAP = 1;

    @BindView(R.id.llItem)
    LinearLayout llItem;
    @BindView(R.id.imgArticle)
    ImageView imgArticle;
    @BindView(R.id.txtProvinceName)
    TextView txtProvinceName;
    @BindView(R.id.llShowMap)
    LinearLayout llShowMap;


    public ProvinceMainViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Province> provinces, int position, OnItemClickListener listener) {
        Province province = provinces.get(position);
        txtProvinceName.setText(province.getName());
        Glide.with(itemView.getContext())
                .load(BuildConfig.BASE_URL + province.getImage())
                .centerCrop()
                .error(R.drawable.ic_default)
                .into(imgArticle);

        llItem.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick(CLICK_TYPE_ITEM, position);
            }
        });

        llShowMap.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick(CLICK_TYPE_SHOW_MAP, position);
            }
        });
    }

}
