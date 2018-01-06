package com.techco.igotrip.ui.viewholder;

import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.adapter.SimpleListDialogAdapter;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class SimpleListDialogViewHolder extends BaseViewHolder {

    @BindView(R.id.rlItem)
    RelativeLayout rlItem;
    @BindView(R.id.txtContent)
    TextView txtContent;


    public SimpleListDialogViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<String> datas, int position, OnItemClickListener listener, int textAlignType) {
        String data = datas.get(position);
        txtContent.setText(data);
        if (textAlignType == SimpleListDialogAdapter.TEXT_ALIGN_TYPE_LEFT) {
            txtContent.setGravity(Gravity.LEFT);
        }
        rlItem.setOnClickListener(view -> listener.onClick(data, position));
    }

}
