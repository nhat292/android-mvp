
package com.techco.igotrip.ui.youarehere;

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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.techco.common.AppLogger;
import com.techco.common.Utils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.enums.ArticleMarkerType;
import com.techco.igotrip.ui.adapter.MapInfoWindowAdapter;
import com.techco.igotrip.ui.adapter.SubTypeAdapter;
import com.techco.igotrip.ui.adapter.TypeAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.detail.DetailActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.experience.ExperienceActivity;
import com.techco.igotrip.ui.favorite.FavoriteActivity;
import com.techco.igotrip.ui.info.InfoActivity;
import com.techco.igotrip.ui.login.LoginActivity;
import com.techco.igotrip.ui.mytrip.MyTripActivity;
import com.techco.igotrip.ui.post.PostActivity;
import com.techco.igotrip.ui.viewarticles.ViewArticlesActivity;
import com.techco.igotrip.utils.permission.ErrorPermissionRequestListener;
import com.techco.igotrip.utils.permission.PermissionResultListener;
import com.techco.igotrip.utils.permission.SinglePermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Nhat on 12/13/17.
 */


public class YouAreHereActivity extends BaseActivity implements YouAreHereBaseView, OnMapReadyCallback, PermissionResultListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "YouAreHereActivity";

    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 0;

    @Inject
    YouAreHereMvpPresenter<YouAreHereBaseView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, YouAreHereActivity.class);
        return intent;
    }

    //Main
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    //LeftMenu
    @BindView(R.id.imgUser)
    CircleImageView imgUser;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.btnMenuLogin)
    Button btnMenuLogin;

    @BindView(R.id.recyclerSubType)
    RecyclerView recyclerSubType;
    @BindView(R.id.recyclerType)
    RecyclerView recyclerType;

    private List<Type> mTypes = new ArrayList<>();
    private List<SubType> mSubTypes = new ArrayList<>();
    private Type mCurrentType;
    private SubType mCurrentSubType;
    private TypeAdapter mTypeAdapter;
    private SubTypeAdapter mSubTypeAdapter;

    private boolean isUserLogged = false;

    private GoogleMap mGoogleMap;
    private PermissionListener locationPermissionListener;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager manager;
    private Location mLocation;
    private List<Marker> markers = new ArrayList<>();
    private Map<Marker, Article> markerMap = new HashMap<>();
    private ArrayList<Article> articles = new ArrayList<>();
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_are_here);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(YouAreHereActivity.this);

        setUp();

        createPermissionListener();

        mPresenter.getExploreData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkUserStatus();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    protected void setUp() {
        mTypeAdapter = new TypeAdapter(this.mTypes, (object, position) -> {
            this.mCurrentType = (Type) object;
            mPresenter.selectType(mCurrentType.getId());
        });
        mSubTypeAdapter = new SubTypeAdapter(this.mSubTypes, ((object, position) -> {
            this.mCurrentSubType = (SubType) object;
            exploreArticle();
        }));

        recyclerType.setAdapter(mTypeAdapter);
        recyclerSubType.setAdapter(mSubTypeAdapter);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.imgMenu)
    public void onMenuClick() {
        drawer.openDrawer(Gravity.START);
    }

    @OnClick(R.id.imgLogo)
    public void onLogoClick() {
        finish();
    }

    @OnClick(R.id.imgRight)
    public void onRightClick() {
        mPresenter.onPostClick();
    }

    @OnClick(R.id.btnMenuInfo)
    public void onMenuInfoClick() {
        if (isUserLogged()) {
            startActivity(InfoActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuMyTrip)
    public void onMenuMyTripClick() {
        if (isUserLogged()) {
            startActivity(MyTripActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuExperiences)
    public void onMenuExperiencesClick() {
        if (isUserLogged()) {
            startActivity(ExperienceActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuFavorites)
    public void onMenuFavoritesClick() {
        if (isUserLogged()) {
            startActivity(FavoriteActivity.getStartIntent(this));
            closeLeftMenu();
        }
    }

    @OnClick(R.id.btnMenuSupport)
    public void onMenuSupportClick() {
        startActivity(com.techco.igotrip.ui.support.SupportActivity.getStartIntent(this));
        closeLeftMenu();
    }

    @OnClick(R.id.btnMenuLogin)
    public void onMenuLoginClick() {
        if (isUserLogged()) {
            showConfirmDialog(getString(R.string.confirm_title), getString(R.string.message_ask_logout), null, getString(android.R.string.cancel), new DialogCallback<AppDialog>() {
                @Override
                public void onNegative(AppDialog dialog) {
                    dialog.dismissDialog(AppDialog.TAG);
                }

                @Override
                public void onPositive(AppDialog dialog, Object o) {
                    dialog.dismissDialog(AppDialog.TAG);
                    mPresenter.logout();
                    closeLeftMenu();
                }
            });
        }
    }

    @OnClick(R.id.imgList)
    public void onListClick() {
        if (articles.size() == 0) {
            showMessage(R.string.no_data_found);
        } else {
            startActivity(ViewArticlesActivity.getStartIntent(this, articles));
        }
    }

    @OnClick(R.id.imgSearch)
    public void onSearchClick() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    @OnClick(R.id.imgMyLocation)
    public void onMyLocationClick() {
        checkLocationPermission();
    }


    @OnClick(R.id.imgUser)
    public void onUserClick() {
        if (mUser != null && !mUser.getImage().isEmpty()) {
            viewImage(mUser.getImage());
        }
    }

    @Override
    public void openLogin() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @Override
    public void openPost() {
        startActivity(PostActivity.getStartIntent(this));
    }

    @Override
    public void onCheckUserStatusSuccess(User user) {
        mUser = user;
        if (user != null) {
            isUserLogged = true;
            txtUser.setText(user.getFullName());
            btnMenuLogin.setText(getString(R.string.menu_logout));
            if (!user.getImage().isEmpty()) {
                Glide.with(this)
                        .load(user.getImage())
                        .into(imgUser);
            }
        } else {
            isUserLogged = false;
            txtUser.setText(getString(R.string.guest_text));
            btnMenuLogin.setText(getString(R.string.menu_login));
            imgUser.setImageResource(0);
        }
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

        checkLocationPermission();
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
        exploreArticle();
    }

    @Override
    public void onExploreArticleSuccess(List<Article> articles) {
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

    private boolean isUserLogged() {
        if (isUserLogged) {
            return true;
        }
        startActivity(LoginActivity.getStartIntent(this));
        return false;
    }

    private void closeLeftMenu() {
        drawer.closeDrawer(Gravity.START);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setMaxZoomPreference(17.0f);
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
                mGoogleMap.setMyLocationEnabled(true);
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, location -> {
                                if (location != null) {
                                    this.mLocation = location;
                                    zoomToLocation(mLocation.getLatitude(), mLocation.getLongitude());
                                    exploreArticle();
                                }
                            });
                } else {
                    new Handler().postDelayed(() -> {
                        hideLoading();
                        showAskForGPS();
                    }, 500);
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

    private void exploreArticle() {
        double lat = mLocation != null ? mLocation.getLatitude() : 0.0;
        double lng = mLocation != null ? mLocation.getLongitude() : 0.0;
        mPresenter.exploreArticle(lat, lng, mCurrentType.getId(), mCurrentSubType.getId());
    }

    private void clearAllMarkers() {
        if (markers.size() <= 0) return;
        for (Marker marker : markers) {
            marker.remove();
        }
        markerMap.clear();
        markers.clear();
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        new Handler().postDelayed(() -> marker.showInfoWindow(), 200);
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Article data = markerMap.get(marker);
        startActivity(DetailActivity.getStartIntent(this, data));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            Place place = PlaceAutocomplete.getPlace(this, data);
            mLocation = new Location("");
            mLocation.setLatitude(place.getLatLng().latitude);
            mLocation.setLongitude(place.getLatLng().longitude);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
            exploreArticle();
        }
    }

    private void viewImage(String url) {
        List<String> items = new ArrayList<>();
        items.add(url);
        new ImageViewer.Builder<>(this, items)
                .setFormatter(item -> item)
                .setStartPosition(0)
                .allowZooming(true)
                .allowSwipeToDismiss(true)
                .show();
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
}
