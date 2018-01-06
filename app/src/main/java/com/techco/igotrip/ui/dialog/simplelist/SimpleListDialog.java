
package com.techco.igotrip.ui.dialog.simplelist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.dagger.component.ActivityComponent;
import com.techco.igotrip.ui.adapter.SimpleListDialogAdapter;
import com.techco.igotrip.ui.base.BaseDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nhat on 12/13/17.
 */


public class SimpleListDialog extends BaseDialog {

    public static final String TAG = "AppDialog";

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.recyclerList)
    RecyclerView recyclerList;

    private SimpleListDialogAdapter adapter;

    public static SimpleListDialog newInstance() {
        SimpleListDialog fragment = new SimpleListDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_simple_list, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            setUnBinder(ButterKnife.bind(this, view));
        }

        return view;
    }

    private String title;
    private List<String> datas;
    private int textAlignType = SimpleListDialogAdapter.TEXT_ALIGN_TYPE_CENTER;

    public void show(FragmentManager fragmentManager, String title, List<String> datas) {
        super.show(fragmentManager, TAG);
        this.title = title;
        this.datas = datas;
    }

    public void show(FragmentManager fragmentManager, String title, List<String> datas, int textAlignType) {
        super.show(fragmentManager, TAG);
        this.title = title;
        this.datas = datas;
        this.textAlignType = textAlignType;
    }

    @Override
    protected void setUp(View view) {
        if (title != null) {
            txtTitle.setText(title);
        } else {
            txtTitle.setVisibility(View.GONE);
        }

        adapter = new SimpleListDialogAdapter(datas, ((object, position) -> {
            callback.onPositive(this, position);
        }), textAlignType);
        recyclerList.setAdapter(adapter);
    }

    @OnClick(R.id.btnCancel)
    public void onCancelClick(View v) {
        if (callback != null) {
            callback.onNegative(this);
        }
    }
}