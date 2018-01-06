
package com.techco.igotrip.ui.post;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.techco.common.AppLogger;
import com.techco.common.Utils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.response.PlacesResponse;
import com.techco.igotrip.ui.adapter.SimpleListDialogAdapter;
import com.techco.igotrip.ui.addpictures.AddPicturesActivity;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.dialog.simplelist.SimpleListDialog;
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


public class PostActivity extends BaseActivity implements PostBaseView, OnMapReadyCallback, PermissionResultListener, GoogleMap.OnMapClickListener {

    private static final String TAG = "PostActivity";

    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 0;

    @Inject
    PostMvpPresenter<PostBaseView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PostActivity.class);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.editTitle)
    EditText editTitle;
    @BindView(R.id.editDescription)
    EditText editDescription;
    @BindView(R.id.btnSelectType)
    Button btnSelectType;
    @BindView(R.id.btnAddPictures)
    Button btnAddPictures;
    @BindView(R.id.imgRight)
    ImageView imgRight;

    public static List<Bitmap> bitmaps = new ArrayList<>();

    private PermissionListener locationPermissionListener;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager manager;
    private GoogleMap mGoogleMap;
    private LatLng latLng;
    private Marker marker;

    private Type type, tempType;
    private SubType subType;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(PostActivity.this);

        setUp();

        createPermissionListener();

        checkLocationPermission();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        bitmaps.clear();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bitmaps.size() > 0) {
            btnAddPictures.setText(String.format(getString(R.string.pictures_format), bitmaps.size()));
        } else {
            btnAddPictures.setText(getString(R.string.post_add_pictures));
        }
    }

    @Override
    protected void setUp() {
        txtTitle.setText(getString(R.string.post));
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(R.drawable.ic_search);

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

    @OnClick(R.id.imgMyLocation)
    public void onMyLocationClick() {
        checkLocationPermission();
    }

    @OnClick(R.id.btnSelectType)
    public void onSelectTypeClick() {
        mPresenter.getTypes();
    }

    @OnClick(R.id.btnAddPictures)
    public void onAddPicturesClick() {
        startActivity(AddPicturesActivity.getStartIntent(this));
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick() {
        mPresenter.postArticle(
                latLng,
                address,
                editTitle.getText().toString(),
                editDescription.getText().toString(),
                type,
                subType,
                bitmaps);
    }

    @OnClick(R.id.imgRight)
    public void onRightClick() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {

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
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setMaxZoomPreference(17.0f);
        mGoogleMap.setOnMapClickListener(this);
    }

    @Override
    public void onSearchPlacesSuccess(PlacesResponse response) {
        List<String> datas = new ArrayList<>();
        if (response.getResults().size() > 0) {
            for (int i = 0; i < response.getResults().size(); i++) {
                datas.add(response.getResults().get(i).getAddressName());
            }
        }
        SimpleListDialog dialog = SimpleListDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getString(R.string.post_select_address).toUpperCase(), datas, SimpleListDialogAdapter.TEXT_ALIGN_TYPE_LEFT);
        dialog.setCallback(new DialogCallback<SimpleListDialog>() {
            @Override
            public void onNegative(SimpleListDialog dialog) {
                dialog.dismissDialog(SimpleListDialog.TAG);
            }

            @Override
            public void onPositive(SimpleListDialog dialog, Object o) {
                dialog.dismissDialog(SimpleListDialog.TAG);
                int position = (int) o;
                latLng = new LatLng(response.getResults().get(position).getGeometry().getLocation().getLat(),
                        response.getResults().get(position).getGeometry().getLocation().getLng());
                address = response.getResults().get(position).getAddressName();
                addMarker();
            }
        });
    }

    @Override
    public void onGetTypesSuccess(List<Type> types) {
        List<String> datas = new ArrayList<>();
        if (types.size() > 0) {
            for (int i = 0; i < types.size(); i++) {
                datas.add(types.get(i).getName());
            }
        }
        SimpleListDialog dialog = SimpleListDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getString(R.string.post_select_type).toUpperCase(), datas);
        dialog.setCallback(new DialogCallback<SimpleListDialog>() {
            @Override
            public void onNegative(SimpleListDialog dialog) {
                dialog.dismissDialog(SimpleListDialog.TAG);
            }

            @Override
            public void onPositive(SimpleListDialog dialog, Object o) {
                dialog.dismissDialog(SimpleListDialog.TAG);
                int position = (int) o;
                tempType = types.get(position);
                mPresenter.selectType(tempType.getId());
            }
        });
    }

    @Override
    public void onSelectTypeSuccess(List<SubType> subTypes) {
        List<String> datas = new ArrayList<>();
        if (subTypes.size() > 0) {
            for (int i = 0; i < subTypes.size(); i++) {
                datas.add(subTypes.get(i).getName());
            }
        }
        SimpleListDialog dialog = SimpleListDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getString(R.string.post_select_sub_type).toUpperCase(), datas);
        dialog.setCallback(new DialogCallback<SimpleListDialog>() {
            @Override
            public void onNegative(SimpleListDialog dialog) {
                dialog.dismissDialog(SimpleListDialog.TAG);
            }

            @Override
            public void onPositive(SimpleListDialog dialog, Object o) {
                dialog.dismissDialog(SimpleListDialog.TAG);
                int position = (int) o;
                type = tempType;
                subType = subTypes.get(position);
                btnSelectType.setText(type.getName() + " > " + subType.getName());
            }
        });
    }

    @Override
    public void onPostArticleSuccess() {
        editTitle.setText("");
        editDescription.setText("");
        type = null;
        tempType = null;
        subType = null;
        btnSelectType.setText(getString(R.string.post_select_type));
        btnAddPictures.setText(getString(R.string.post_add_pictures));
        bitmaps.clear();
        if (marker != null) {
            marker.remove();
        }
        showSimpleDialog(getString(R.string.success), getString(R.string.message_post_success));
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
                                    mPresenter.searchPlaces(location.getLatitude(), location.getLongitude());
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
    public void onMapClick(LatLng latLng) {
        mPresenter.searchPlaces(latLng.latitude, latLng.longitude);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            Place place = PlaceAutocomplete.getPlace(this, data);
            address = place.getAddress().toString();
            latLng = place.getLatLng();
            addMarker();
        }
    }

    private void addMarker() {
        CameraPosition cameraPosition =
                new CameraPosition.Builder().target(latLng)
                        .zoom(15.0f)
                        .bearing(0)
                        .tilt(25)
                        .build();
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(Utils.createBitmapWithSize(PostActivity.this, R.drawable.ic_marker,
                        (int) getResources().getDimension(R.dimen.icon_marker_size_width),
                        (int) getResources().getDimension(R.dimen.icon_marker_size_height))));
        if (marker != null) {
            marker.remove();
        }
        marker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
