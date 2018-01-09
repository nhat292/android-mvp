
package com.techco.igotrip.ui.showmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techco.common.Utils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Continent;
import com.techco.igotrip.data.network.model.object.Nation;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SearchPlaceResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.enums.ArticleMarkerType;
import com.techco.igotrip.ui.adapter.ContinentAdapter;
import com.techco.igotrip.ui.adapter.MapInfoWindowAdapter;
import com.techco.igotrip.ui.adapter.NationAdapter;
import com.techco.igotrip.ui.adapter.SubTypeAdapter;
import com.techco.igotrip.ui.adapter.TypeAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.detail.DetailActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.simplelist.SimpleListDialog;
import com.techco.igotrip.ui.viewarticles.ViewArticlesActivity;
import com.techco.igotrip.utils.NationComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class ShowMapActivity extends BaseActivity implements ShowMapBaseView, OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationChangeListener {
    private static final String TAG = "ShowMapActivity";

    private static final String EXTRA_PROVINCE = "PROVINCE";
    private static final String EXTRA_NATION_NAME = "NATION_NAME";
    private static final String EXTRA_NATIONS = "NATIONS";
    private static final String EXTRA_CONTINENTS = "CONTINENTS";

    @Inject
    ShowMapMvpPresenter<ShowMapBaseView> mPresenter;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.llCountry)
    LinearLayout llCountry;
    @BindView(R.id.rlRightMenu)
    RelativeLayout rlRightMenu;
    @BindView(R.id.rlMenuNation)
    RelativeLayout rlMenuNation;
    @BindView(R.id.txtNationEmpty)
    TextView txtNationEmpty;
    @BindView(R.id.recyclerContinent)
    RecyclerView recyclerContinent;
    @BindView(R.id.recyclerNation)
    RecyclerView recyclerNation;

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
    private ArrayList<Nation> mAllNations;
    private ArrayList<Nation> mNations = new ArrayList<>();
    private ArrayList<Continent> mContinents;
    private ContinentAdapter mContinentAdapter;
    private int mNationId = 0;
    private NationAdapter mNationAdapter;

    private List<Type> mTypes = new ArrayList<>();
    private List<SubType> mSubTypes = new ArrayList<>();
    private Type mCurrentType;
    private SubType mCurrentSubType;
    private TypeAdapter mTypeAdapter;
    private SubTypeAdapter mSubTypeAdapter;
    private double mLat, mLng;
    private List<Marker> markers = new ArrayList<>();
    private Map<Marker, Article> markerMap = new HashMap<>();
    private ArrayList<Article> articles = new ArrayList<>();

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
        mAllNations = intent.getParcelableArrayListExtra(EXTRA_NATIONS);
        mContinents = intent.getParcelableArrayListExtra(EXTRA_CONTINENTS);

        txtTitle.setText(mProvince.getName());
        txtNationName.setText(mNationName);

        mTypeAdapter = new TypeAdapter(this.mTypes, (object, position) -> {
            this.mCurrentType = (Type) object;
            mPresenter.selectType(mCurrentType.getId());
        });
        mSubTypeAdapter = new SubTypeAdapter(this.mSubTypes, ((object, position) -> {
            this.mCurrentSubType = (SubType) object;
            this.exploreArticle();
        }));

        recyclerType.setAdapter(mTypeAdapter);
        recyclerSubType.setAdapter(mSubTypeAdapter);

        mContinents.get(0).setSelected(true);
        rlRightMenu.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        mContinentAdapter = new ContinentAdapter(mContinents, (object, position) -> {
            if (rlMenuNation.getVisibility() == View.GONE) {
                rlMenuNation.setVisibility(View.VISIBLE);
                Animation slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
                rlMenuNation.startAnimation(slideInRight);
            }
            selectContinent((Continent) object);
        });
        recyclerContinent.setAdapter(mContinentAdapter);

        mNationAdapter = new NationAdapter(mNations, (o, n) -> {
            drawerLayout.closeDrawer(Gravity.END);
            mNationId = ((Nation) o).getId();
            txtNationName.setText(((Nation) o).getName());
            mPresenter.getProvinces(mNationId);
        });
        recyclerNation.setAdapter(mNationAdapter);
        selectContinent(mContinents.get(0));


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void selectContinent(Continent continent) {
        mNations.clear();
        for (int i = 0; i < mAllNations.size(); i++) {
            Nation n = mAllNations.get(i);
            if (n.getContinentId() == continent.getId()) {
                mNations.add(n);
            }
        }
        if (mNations.size() == 0) {
            txtNationEmpty.setVisibility(View.VISIBLE);
        } else {
            txtNationEmpty.setVisibility(View.GONE);
        }
        Collections.sort(mNations, new NationComparator());
        mNationAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.llCountry)
    public void onCountyClick() {
        rlMenuNation.setVisibility(View.GONE);
        drawerLayout.openDrawer(Gravity.END);
    }

    @OnClick(R.id.imgList)
    public void onListClick() {
        if (articles.size() == 0) {
            showMessage(R.string.no_data_found);
        } else {
            startActivity(ViewArticlesActivity.getStartIntent(this, articles));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        //Settings Google maps
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnMyLocationChangeListener(this);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setMyLocationEnabled(false);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setMaxZoomPreference(17.0f);
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

        mPresenter.searchPlaces(mProvince.getName() + " " + mNationName, null);
    }

    @Override
    public void onSelectTypeSuccess(SelectTypeResponse response) {
        this.mSubTypes.clear();
        this.mSubTypes.addAll(response.getSubTypes());
        if (this.mSubTypes.size() > 0) {
            this.mSubTypes.get(0).setSelected(true);
            mCurrentSubType = this.mSubTypes.get(0);
        }
        mSubTypeAdapter.notifyDataSetChanged();
        this.exploreArticle();
    }

    @Override
    public void onSearchPlacesSuccess(SearchPlaceResponse response) {
        if (response.getResults().size() > 0) {
            SearchPlaceResponse.SResult result = response.getResults().get(0);
            zoomToLocation(result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng());
            mLat = result.getGeometry().getLocation().getLat();
            mLng = result.getGeometry().getLocation().getLng();
            exploreArticle();
        } else {
            hideLoading();
            showSimpleDialog(getString(R.string.error_title), getString(R.string.message_unknown_error));
        }
    }

    @Override
    public void onGetArticlesSuccess(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        if (this.articles.size() == 0) {
            showMessage(R.string.no_data_found);
        }
        clearAllMarkers();
        if (this.articles.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Article data : this.articles) {
                LatLng latLng = new LatLng(data.getLat(), data.getLng());
                builder.include(latLng);
                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(data.getTitle())
                        .snippet(data.getDescription())
                        .icon(BitmapDescriptorFactory.fromBitmap(Utils.createBitmapWithSize(this, getIcon(), 55, 60))));
                markers.add(marker);
                markerMap.put(marker, data);
            }
            int padding = 30;
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), padding));
            mGoogleMap.setInfoWindowAdapter(new MapInfoWindowAdapter(this, markerMap));
        }
    }

    @Override
    public void onGetProvincesSuccess(List<Province> provinces) {
        List<String> datas = new ArrayList<>();
        if (provinces.size() > 0) {
            for (int i = 0; i < provinces.size(); i++) {
                datas.add(provinces.get(i).getName());
            }
        }
        SimpleListDialog dialog = SimpleListDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getString(R.string.select_province), datas);
        dialog.setCallback(new DialogCallback<SimpleListDialog>() {
            @Override
            public void onNegative(SimpleListDialog dialog) {
                dialog.dismissDialog(SimpleListDialog.TAG);
            }

            @Override
            public void onPositive(SimpleListDialog dialog, Object o) {
                dialog.dismissDialog(SimpleListDialog.TAG);
                int position = (int) o;
                txtTitle.setText(provinces.get(position).getName());
                mPresenter.searchPlaces(provinces.get(position).getName() + " " + mNationName, null);
            }
        });
    }

    private void zoomToLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        CameraPosition cameraPosition =
                new CameraPosition.Builder().target(latLng)
                        .zoom(10.0f)
                        .bearing(0)
                        .tilt(25)
                        .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void exploreArticle() {
        mPresenter.exploreArticle(mProvince.getId(), mLat, mLng, mCurrentType.getId(), mCurrentSubType.getId());
    }

    private int getIcon() {
        int icon = R.drawable.ic_marker;
        if (mCurrentType.getId() == ArticleMarkerType.PLACES.getValue()) {
            icon = R.drawable.ic_marker;
        } else if (mCurrentType.getId() == ArticleMarkerType.ROOM.getValue()) {
            icon = R.drawable.ic_marker_hotel;
        } else if (mCurrentType.getId() == ArticleMarkerType.RESTAURANT.getValue()) {
            icon = R.drawable.ic_marker_restaurant;
        } else if (mCurrentType.getId() == ArticleMarkerType.SHOPPING.getValue()) {
            icon = R.drawable.ic_marker_shopping;
        }
        return icon;
    }

    private void clearAllMarkers() {
        if (markers.size() <= 0) return;
        for (Marker marker : markers) {
            marker.remove();
        }
        markerMap.clear();
        markers.clear();
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Article data = markerMap.get(marker);
        startActivity(DetailActivity.getStartIntent(this, data));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        new Handler().postDelayed(() -> marker.showInfoWindow(), 200);
        return true;
    }


    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            mLat = location.getLatitude();
            mLng = location.getLongitude();
        }
    }
}
