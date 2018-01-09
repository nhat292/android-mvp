
package com.techco.igotrip.ui.journey;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.adapter.JourneyArticlesAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.custom.itemtouchhelper.OnStartDragListener;
import com.techco.igotrip.ui.custom.itemtouchhelper.SimpleItemTouchHelperCallback;
import com.techco.igotrip.ui.custom.views.SpacesItemDecoration;
import com.techco.igotrip.ui.detail.DetailActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.utils.permission.ErrorPermissionRequestListener;
import com.techco.igotrip.utils.permission.PermissionResultListener;
import com.techco.igotrip.utils.permission.SinglePermissionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class JourneyActivity extends BaseActivity implements JourneyBaseView,
        SwipeRefreshLayout.OnRefreshListener,
        PermissionResultListener, OnStartDragListener {

    private static final String TAG = "JourneyActivity";
    private static final String EXTRA_JOURNEY = "JOURNEY";

    @Inject
    JourneyMvpPresenter<JourneyBaseView> mPresenter;

    public static Intent getStartIntent(Context context, Journey journey) {
        Intent intent = new Intent(context, JourneyActivity.class);
        intent.putExtra(JourneyActivity.EXTRA_JOURNEY, journey);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerList)
    RecyclerView recyclerList;
    @BindView(R.id.imgRight)
    ImageView imgRight;

    private Location location;
    private PermissionListener locationPermissionListener;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager manager;

    private Journey journey;

    private List<Article> articles = new ArrayList<>();
    private JourneyArticlesAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(JourneyActivity.this);

        setUp();

        createPermissionListener();

        checkLocationPermission();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    protected void setUp() {
        journey = getIntent().getParcelableExtra(EXTRA_JOURNEY);
        txtTitle.setText(journey.getName());
        imgRight.setImageResource(R.drawable.ic_save);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerList.addItemDecoration(new SpacesItemDecoration(5, true));

        adapter = new JourneyArticlesAdapter(articles, (object, position) -> startActivity(DetailActivity.getStartIntent(JourneyActivity.this, articles.get(position))), this);
        recyclerList.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerList);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


    }

    @Override
    public void hideRefreshLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadArticlesSuccess(List<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        Collections.sort(this.articles, (a1, a2) -> {
            if (a1.getIndex() > a2.getIndex()) {
                return 1;
            } else if (a1.getIndex() < a2.getIndex()) {
                return -1;
            }
            return 0;
        });
        adapter.notifyDataSetChanged();
        if (this.articles.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
            recyclerList.setVisibility(View.INVISIBLE);
        } else {
            txtNoData.setVisibility(View.INVISIBLE);
            recyclerList.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.imgRight)
    public void onRightClick() {
        imgRight.setVisibility(View.INVISIBLE);
        if (articles.size() == 0) return;
        String data = "";
        for (int i = 0; i < articles.size(); i++) {
            int index = i + 1;
            data += articles.get(i).getId() + "," + index + "@@";
        }
        mPresenter.updateIndex(data, journey.getId());
    }

    @Override
    public void onRefresh() {
        loadArticles(false);
    }

    private void checkLocationPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(locationPermissionListener)
                .withErrorListener(new ErrorPermissionRequestListener(this))
                .check();
    }

    private void createPermissionListener() {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        PermissionListener feedbackViewPermissionListener = new SinglePermissionListener(this);
        locationPermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
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
                                    loadArticles(true);
                                }
                            });
                } else {
                    new Handler().postDelayed(() -> {
                        showAskForGPS();
                    }, 500);
                }
            }
        }
    }

    @Override
    public void onPermissionDenied(String permissionName, boolean isPermanentDenied) {
        AppLogger.d(TAG, "Permission denied: " + permissionName, ", Permanent denied: " + isPermanentDenied);
        loadArticles(true);
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

    private void showAskForGPS() {
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

    private void loadArticles(boolean showLoading) {
        double lat = location != null ? location.getLatitude() : 0.0;
        double lng = location != null ? location.getLongitude() : 0.0;
        mPresenter.loadArticles(lat, lng, journey.getId(), showLoading);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
        imgRight.setVisibility(View.VISIBLE);
    }
}
