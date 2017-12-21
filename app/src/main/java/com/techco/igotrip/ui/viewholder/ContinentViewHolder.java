package com.techco.igotrip.ui.viewholder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techco.common.Utils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Continent;
import com.techco.igotrip.ui.adapter.ContinentAdapter;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class ContinentViewHolder extends BaseViewHolder {

    @BindView(R.id.llItem)
    LinearLayout llItem;
    @BindView(R.id.imgContinent)
    ImageView imgContinent;
    @BindView(R.id.txtContinent)
    TextView txtContinent;


    public ContinentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Continent> continents, int position, OnItemClickListener listener, ContinentAdapter adapter) {
        Continent continent = continents.get(position);
        llItem.setOnClickListener((view) -> {
            if (listener != null) {
                for (int i = 0; i < continents.size(); i++) {
                    if (i == position) {
                        continents.get(i).setSelected(true);
                    } else {
                        continents.get(i).setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
                listener.onClick(continent, position);
            }
        });
        txtContinent.setText(continent.getName().toUpperCase());
        if (continent.isSelected()) {
            llItem.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
            txtContinent.setTextColor(Color.WHITE);
            imgContinent.setColorFilter(Color.WHITE);
            continent.setColor(0);
        } else {
            llItem.setBackgroundColor(Color.WHITE);
            txtContinent.setTextColor(Color.BLACK);
            int color = continent.getColor() == 0 ? Utils.getRandomColor() : continent.getColor();
            imgContinent.setColorFilter(color);
            continent.setColor(color);
        }
    }

}
