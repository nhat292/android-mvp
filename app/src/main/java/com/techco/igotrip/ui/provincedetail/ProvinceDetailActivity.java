package com.techco.igotrip.ui.provincedetail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.techco.common.AppLogger;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.ui.adapter.SubTypeAdapter;
import com.techco.igotrip.ui.adapter.TypeAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.utils.permission.ErrorPermissionRequestListener;
import com.techco.igotrip.utils.permission.PermissionResultListener;
import com.techco.igotrip.utils.permission.SinglePermissionListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.location.GpsStatus.GPS_EVENT_STARTED;
import static android.location.GpsStatus.GPS_EVENT_STOPPED;


/**
 * Created by Nhat on 12/13/17.
 */


public class ProvinceDetailActivity extends BaseActivity implements ProvinceDetailBaseView, PermissionResultListener {

    private static final String TAG = "ProvinceDetailActivity";
    public static final String EXTRA_PROVINCE = "PROVINCE";

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
    private Province province;

    private double minDistance = 0.0;
    private Location location;
    private PermissionListener locationPermissionListenner;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager manager;

    public static Intent getStartIntent(Context context, Province province) {
        Intent intent = new Intent(context, ProvinceDetailActivity.class);
        intent.putExtra(ProvinceDetailActivity.EXTRA_PROVINCE, province);
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

        createPermissionListener();

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        province = getIntent().getParcelableExtra(ProvinceDetailActivity.EXTRA_PROVINCE);
        txtTitle.setText(province.getName());

        typeAdapter = new TypeAdapter(this.types, (object, position) -> {
            this.currentType = (Type) object;
            mPresenter.selectType(currentType.getId());
        });
        subTypeAdapter = new SubTypeAdapter(this.subTypes, ((object, position) -> {
            this.currentSubType = (SubType) object;

        }));

        recyclerType.setAdapter(typeAdapter);
        recyclerSubType.setAdapter(subTypeAdapter);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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

        checkLocaionPermission();
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

    @Override
    public void onExploreArticleSuccess(ArticleResponse response) {
        AppLogger.d(TAG, "Article size: " + response.getArticles().size());
    }

    private void checkLocaionPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(locationPermissionListenner)
                .withErrorListener(new ErrorPermissionRequestListener(this))
                .check();
    }

    private void createPermissionListener() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        PermissionListener feedbackViewPermissionListener = new SinglePermissionListener(this);
        locationPermissionListenner = new CompositePermissionListener(feedbackViewPermissionListener,
                SnackbarOnDeniedPermissionListener.Builder.with(viewGroup,
                        R.string.message_camera_permission_denied)
                        .withOpenSettingsButton(R.string.setting)
                        .withCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar snackbar) {
                                super.onShown(snackbar);
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                super.onDismissed(snackbar, event);
                            }
                        })
                        .build());
    }

    @Override
    public void onPermissionError(String error) {
        AppLogger.d(TAG, "Permission requested error: " + error);
    }

    @Override
    public void onPermissionGranted(String permissionName) {
        AppLogger.d(TAG, "Permission granted: " + permissionName);
        if (permissionName.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, location -> {
                                if (location != null) {
                                    this.location = location;
                                    exploreArticle();
                                }
                            });
                } else {
                    manager.addGpsStatusListener(event -> {
                        if (this.location != null) return;
                        switch (event) {
                            case GPS_EVENT_STARTED:
                                mFusedLocationClient.getLastLocation()
                                        .addOnSuccessListener(this, location -> {
                                            if (location != null) {
                                                this.location = location;
                                                exploreArticle();
                                            }
                                        });
                                break;
                            case GPS_EVENT_STOPPED:

                                break;
                        }
                    });
                    showConfirmDialog(getString(R.string.location),
                            getString(R.string.message_ask_enable_location),
                            null,
                            getString(android.R.string.cancel),
                            new DialogCallback<AppDialog>() {
                                @Override
                                public void onNegative(AppDialog dialog) {
                                    dialog.dismissDialog(AppDialog.TAG);
                                }

                                @Override
                                public void onPositive(AppDialog dialog, Object o) {
                                    dialog.dismissDialog(AppDialog.TAG);
                                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            });
                }
            }
        }
    }

    @Override
    public void onPermissionDenied(String permissionName, boolean isPermanentDenied) {
        AppLogger.d(TAG, "Permission denied: " + permissionName, ", Permanent denied: " + isPermanentDenied);
        exploreArticle();
    }

    @Override
    public void onPermissionRationale(PermissionToken token) {
        showConfirmDialog(getString(R.string.permission), getString(R.string.message_permission_rational), null, getString(android.R.string.cancel), new DialogCallback<AppDialog>() {
            @Override
            public void onNegative(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
                token.cancelPermissionRequest();
            }

            @Override
            public void onPositive(AppDialog dialog, Object o) {
                dialog.dismissDialog(AppDialog.TAG);
                token.continuePermissionRequest();
            }
        });
    }

    private void exploreArticle() {
        double lat = location != null ? location.getLatitude() : 0.0;
        double lng = location != null ? location.getLongitude() : 0.0;
        mPresenter.exploreArticle(
                province.getId(),
                lat,
                lng,
                minDistance,
                currentType.getId(),
                currentSubType.getId()
        );
    }
}