package com.techco.igotrip.ui.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Nation;
import com.techco.igotrip.ui.adapter.NationAdapter;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class NationViewHolder extends BaseViewHolder {

    @BindView(R.id.llItem)
    LinearLayout llItem;
    @BindView(R.id.txtNationName)
    TextView txtNationName;

    public NationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<Nation> nations, int posision, OnItemClickListener listener, NationAdapter adapter) {
        Nation nation = nations.get(posision);
        llItem.setOnClickListener(view -> {
            if(listener != null) {
                listener.onClick(nation, posision);
            }
        });
        txtNationName.setText(nation.getName());
    }

}
