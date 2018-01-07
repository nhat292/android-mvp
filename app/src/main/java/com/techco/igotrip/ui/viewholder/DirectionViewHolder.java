package com.techco.igotrip.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import com.techco.common.CommonUtils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.response.DResponseResult;
import com.techco.igotrip.ui.adapter.DirectionAdapter;
import com.techco.igotrip.ui.adapter.OnItemClickListener;
import com.techco.igotrip.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 12/15/17.
 */

public class DirectionViewHolder extends BaseViewHolder {

    @BindView(R.id.txtStep)
    TextView txtStep;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.txtDuration)
    TextView txtDuration;
    @BindView(R.id.txtGuide)
    TextView txtGuide;


    public DirectionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void clear() {

    }

    public void bind(List<DResponseResult.DStep> steps, int position, OnItemClickListener listener, DirectionAdapter adapter) {
        DResponseResult.DStep step = steps.get(position);
        txtStep.setText(String.format(itemView.getContext().getString(R.string.step_format), position + 1));
        txtDistance.setText(String.format(itemView.getContext().getString(R.string.distance_format),
                step.getDistance().getText()));
        txtDuration.setText(String.format(itemView.getContext().getString(R.string.duration_format),
                step.getDuration().getText()));
        txtGuide.setText(CommonUtils.fromHtml(step.getHtmlInstruction()));
    }

}
