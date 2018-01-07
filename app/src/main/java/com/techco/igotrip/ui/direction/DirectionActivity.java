
package com.techco.igotrip.ui.direction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.techco.common.AppLogger;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.response.DResponseResult;
import com.techco.igotrip.ui.adapter.DirectionAdapter;
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


/**
 * Created by Nhat on 12/13/17.
 */


public class DirectionActivity extends BaseActivity implements DirectionBaseView, PermissionResultListener, OnMapReadyCallback {

    private static final String TAG = "DirectionActivity";
    @Inject
    DirectionMvpPresenter<DirectionBaseView> mPresenter;

    private static final String EXTRA_LAT = "LAT";
    private static final String EXTRA_LNG = "LNG";


    public static Intent getStartIntent(Context context, double lat, double lng) {
        Intent intent = new Intent(context, DirectionActivity.class);
        intent.putExtra(DirectionActivity.EXTRA_LAT, lat);
        intent.putExtra(DirectionActivity.EXTRA_LNG, lng);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtDistance)
    TextView txtDistance;
    @BindView(R.id.txtDuration)
    TextView txtDuration;
    @BindView(R.id.txtStart)
    TextView txtStart;
    @BindView(R.id.txtEnd)
    TextView txtEnd;
    @BindView(R.id.recyclerDirection)
    RecyclerView recyclerDirection;
    @BindView(R.id.llTop)
    LinearLayout llTop;

    private double lat, lng;
    private PermissionListener locationPermissionListener;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager manager;
    private DResponseResult responseResult;
    private GoogleMap mGoogleMap;
    private List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(DirectionActivity.this);

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
        txtTitle.setText(getString(R.string.direction));
        llTop.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra(EXTRA_LAT, 0.0);
        lng = intent.getDoubleExtra(EXTRA_LNG, 0.0);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.imgMap)
    public void onMapClick() {
        recyclerDirection.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.imgList)
    public void onListClick() {
        recyclerDirection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchDirectionSuccess(DResponseResult responseResult) {
        this.responseResult = responseResult;
        if (this.responseResult == null || this.responseResult.getStatus().equals(DResponseResult.STATUS_ZERO)) {
            showMessage(R.string.message_error_direction);
            return;
        }
        llTop.setVisibility(View.VISIBLE);
        recyclerDirection.setVisibility(View.INVISIBLE);
        txtDistance.setText(String.format(getString(R.string.distance_format), responseResult.getRoutes().get(0).getLegs().get(0).getDistance().getText()));
        txtDuration.setText(String.format(getString(R.string.duration_format), responseResult.getRoutes().get(0).getLegs().get(0).getDuration().getText()));
        txtStart.setText(String.format(getString(R.string.start_format), responseResult.getRoutes().get(0).getLegs().get(0).getStartAddress()));
        txtEnd.setText(String.format(getString(R.string.end_format), responseResult.getRoutes().get(0).getLegs().get(0).getEndAddress()));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        LatLng originalLatLng = new LatLng(responseResult.getRoutes().get(0).getLegs().get(0).getStartLocation().getLat(),
                responseResult.getRoutes().get(0).getLegs().get(0).getStartLocation().getLng());
        builder.include(originalLatLng);
        Marker marker1 = mGoogleMap.addMarker(new MarkerOptions()
                .position(originalLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        markers.add(marker1);

        LatLng destinationLatLng = new LatLng(responseResult.getRoutes().get(0).getLegs().get(0).getEndLocation().getLat(),
                responseResult.getRoutes().get(0).getLegs().get(0).getEndLocation().getLng());
        builder.include(destinationLatLng);
        Marker marker2 = mGoogleMap.addMarker(new MarkerOptions()
                .position(destinationLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        markers.add(marker2);

        int padding = 60;
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), padding));

        PolylineOptions options = new PolylineOptions()
                .width(8)
                .color(Color.RED);
        List<LatLng> pointList = new ArrayList<>();
        for (int i = 0; i < responseResult.getRoutes().get(0).getLegs().get(0).getSteps().size(); i++) {
            String points = responseResult.getRoutes().get(0).getLegs().get(0).getSteps().get(i).getPolyLine().getPoints();
            List<LatLng> singlePolyline = decodePoly(points);
            for (LatLng direction : singlePolyline){
                pointList.add(direction);
            }
        }
        options.addAll(pointList);
        mGoogleMap.addPolyline(options);

        recyclerDirection.setAdapter(new DirectionAdapter(responseResult.getRoutes().get(0).getLegs().get(0).getSteps(), null));

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
                                    String query = "origin=" + location.getLatitude() + "," + location.getLongitude() +
                                            "&destination=" + lat + "," + lng;
                                    mPresenter.searchDirection(query);
                                }
                            });
                } else {
                    showAskForGPS();
                }
            }
        }
    }

    @Override
    public void onPermissionDenied(String permissionName, boolean isPermanentDenied) {
        AppLogger.d(TAG, "Permission denied: " + permissionName, ", Permanent denied: " + isPermanentDenied);
        showSimpleDialog(
                getString(R.string.error_title),
                getString(R.string.message_require_permission_when_denied_by_user)
        );
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setMaxZoomPreference(17.0f);
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
