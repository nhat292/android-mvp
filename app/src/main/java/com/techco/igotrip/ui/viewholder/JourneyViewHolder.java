package com.techco.igotrip.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.adapter.JourneyAdapter;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class JourneyViewHolder extends BaseViewHolder {

    public static final short TYPE_ITEM = 1;
    public static final short TYPE_DELETE = 2;

    @BindView(R.id.rlItem)
    RelativeLayout rlItem;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.imgDelete)
    ImageView imgDelete;


    public JourneyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Journey> journeys, int position, OnItemClickListener listener, JourneyAdapter adapter) {
        Journey journey = journeys.get(position);
        txtName.setText(journey.getName());
        imgDelete.setOnClickListener(view -> listener.onClick(TYPE_DELETE, position));
        rlItem.setOnClickListener(view -> listener.onClick(TYPE_ITEM, position));
    }

}
