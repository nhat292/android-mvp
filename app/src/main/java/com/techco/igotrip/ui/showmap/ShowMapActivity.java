
package com.techco.igotrip.ui.showmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Continent;
import com.techco.igotrip.data.network.model.object.Nation;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
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


public class ShowMapActivity extends BaseActivity implements ShowMapBaseView, OnMapReadyCallback {

    private static final String EXTRA_PROVINCE = "PROVINCE";
    private static final String EXTRA_NATION_NAME = "NATION_NAME";
    private static final String EXTRA_NATIONS = "NATIONS";
    private static final String EXTRA_CONTINENTS = "CONTINENTS";

    @Inject
    ShowMapMvpPresenter<ShowMapBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtNationName)
    TextView txtNationName;
    @BindView(R.id.recyclerSubType)
    RecyclerView recyclerSubType;
    @BindView(R.id.recyclerType)
    RecyclerView recyclerType;

    private GoogleMap mGoogleMap;
    private Province mProvince;
    private String mNationName;
    private ArrayList<Nation> mNations;
    private ArrayList<Continent> mContinents;

    private List<Type> mTypes = new ArrayList<>();
    private List<SubType> mSubTypes = new ArrayList<>();
    private Type mCurrentType;
    private SubType mCurrentSubType;
    private TypeAdapter mTypeAdapter;
    private SubTypeAdapter mSubTypeAdapter;

    public static Intent getStartIntent(Context context, Province province, String nationName, ArrayList<Nation> nations, ArrayList<Continent> continents) {
        Intent intent = new Intent(context, ShowMapActivity.class);
        intent.putExtra(ShowMapActivity.EXTRA_PROVINCE, province);
        intent.putExtra(ShowMapActivity.EXTRA_NATION_NAME, nationName);
        intent.putParcelableArrayListExtra(ShowMapActivity.EXTRA_NATIONS, nations);
        intent.putParcelableArrayListExtra(ShowMapActivity.EXTRA_CONTINENTS, continents);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ShowMapActivity.this);

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
        Intent intent = getIntent();
        mProvince = intent.getParcelableExtra(EXTRA_PROVINCE);
        mNationName = intent.getStringExtra(EXTRA_NATION_NAME);
        mNations = intent.getParcelableArrayListExtra(EXTRA_NATIONS);
        mContinents = intent.getParcelableArrayListExtra(EXTRA_CONTINENTS);

        txtTitle.setText(mProvince.getName());
        txtNationName.setText(mNationName);

        mTypeAdapter = new TypeAdapter(this.mTypes, (object, position) -> {
            this.mCurrentType = (Type) object;

        });
        mSubTypeAdapter = new SubTypeAdapter(this.mSubTypes, ((object, position) -> {
            this.mCurrentSubType = (SubType) object;
        }));

        recyclerType.setAdapter(mTypeAdapter);
        recyclerSubType.setAdapter(mSubTypeAdapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.llCountry)
    public void onCountyClick() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    @Override
    public void onGetExploreDataSuccess(ExploreDataResponse response) {
        this.mTypes.clear();
        this.mSubTypes.clear();

        this.mTypes.addAll(response.getData().getTypes());
        this.mSubTypes.addAll(response.getData().getSubTypes());

        if (this.mTypes.size() > 0) {
            this.mTypes.get(0).setSelected(true);
            mCurrentType = this.mTypes.get(0);
        }
        if (this.mSubTypes.size() > 0) {
            this.mSubTypes.get(0).setSelected(true);
            mCurrentSubType = this.mSubTypes.get(0);
        }

        mTypeAdapter.notifyDataSetChanged();
        mSubTypeAdapter.notifyDataSetChanged();
    }
}
