package com.techco.igotrip.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.adapter.SubTypeAdapter;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class SubTypeViewHolder extends BaseViewHolder {

    @BindView(R.id.txtSubType)
    TextView txtSubType;


    public SubTypeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<SubType> subTypes, int position, OnItemClickListener listener, SubTypeAdapter adapter) {
        SubType subType = subTypes.get(position);
        txtSubType.setText(subType.getName());
        txtSubType.setOnClickListener(view -> {
            for(int i = 0; i < subTypes.size(); i++) {
                if(i == position) {
                    subTypes.get(i).setSelected(true);
                } else {
                    subTypes.get(i).setSelected(false);
                }
            }
            adapter.notifyDataSetChanged();
            if(listener != null) {
                listener.onClick(subType, position);
            }
        });
        if(subType.isSelected()) {
            txtSubType.setTextColor(itemView.getContext().getResources().getColor(R.color.accent_black));
        } else {
            txtSubType.setTextColor(itemView.getContext().getResources().getColor(R.color.colorSubTypeNormal));
        }
    }

}
