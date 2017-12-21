
package com.techco.igotrip.ui.provincedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.ui.adapter.SubTypeAdapter;
import com.techco.igotrip.ui.adapter.TypeAdapter;
import com.techco.igotrip.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class ProvinceDetailActivity extends BaseActivity implements ProvinceDetailBaseView {

    public static final String EXTRA_TITLE = "TITLE";

    @Inject
    ProvinceDetailMvpPresenter<ProvinceDetailBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.recyclerSubType)
    RecyclerView recyclerSubType;
    @BindView(R.id.recyclerType)
    RecyclerView recyclerType;

    private List<Type> types = new ArrayList<>();
    private List<SubType> subTypes = new ArrayList<>();
    private Type currentType;
    private SubType currentSubType;
    private TypeAdapter typeAdapter;
    private SubTypeAdapter subTypeAdapter;

    public static Intent getStartIntent(Context context, String title) {
        Intent intent = new Intent(context, ProvinceDetailActivity.class);
        intent.putExtra(ProvinceDetailActivity.EXTRA_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province_detail);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ProvinceDetailActivity.this);

        setUp();

        mPresenter.getExploreData();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getIntent().getStringExtra(ProvinceDetailActivity.EXTRA_TITLE));

        typeAdapter = new TypeAdapter(this.types, (object, position) -> {
            this.currentType = (Type) object;
            mPresenter.selectType(currentType.getId());
        });
        subTypeAdapter = new SubTypeAdapter(this.subTypes, ((object, position) -> {
            this.currentSubType = (SubType) object;

        }));

        recyclerType.setAdapter(typeAdapter);
        recyclerSubType.setAdapter(subTypeAdapter);
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @Override
    public void onGetExploreDataSuccess(ExploreDataResponse response) {
        this.types.clear();
        this.subTypes.clear();

        this.types.addAll(response.getData().getTypes());
        this.subTypes.addAll(response.getData().getSubTypes());

        if (this.types.size() > 0) {
            this.types.get(0).setSelected(true);
            currentType = this.types.get(0);
        }
        if (this.subTypes.size() > 0) {
            this.subTypes.get(0).setSelected(true);
            currentSubType = this.subTypes.get(0);
        }

        typeAdapter.notifyDataSetChanged();
        subTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelectTypeSuccess(SelectTypeResponse response) {
        this.subTypes.clear();
        this.subTypes.addAll(response.getSubTypes());
        if (this.subTypes.size() > 0) {
            this.subTypes.get(0).setSelected(true);
            currentSubType = this.subTypes.get(0);
        }
        subTypeAdapter.notifyDataSetChanged();
    }
}
