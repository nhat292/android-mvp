package com.techco.igotrip.ui.provincedetail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.techco.common.AppLogger;
import com.techco.igotrip.R;
import com.techco.igotrip.callback.ArticleActionCallback;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.ui.adapter.SubTypeAdapter;
import com.techco.igotrip.ui.adapter.TypeAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.comment.CommentActivity;
import com.techco.igotrip.ui.createtrip.CreateTripActivity;
import com.techco.igotrip.ui.custom.carousellayout.CarouselPagerAdapter;
import com.techco.igotrip.ui.detail.DetailActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.dialog.simplelist.SimpleListDialog;
import com.techco.igotrip.ui.login.LoginActivity;
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


public class ProvinceDetailActivity extends BaseActivity implements ProvinceDetailBaseView, PermissionResultListener, ArticleActionCallback {

    private static final String TAG = "ProvinceDetailActivity";
    public static final String EXTRA_PROVINCE = "PROVINCE";

    public static final int REQUEST_ADD_TRIP = 100;

    public static int VIEW_PAGER_WIDTH = 0;
    public static int VIEW_PAGER_HEIGHT = 0;

    @Inject
    ProvinceDetailMvpPresenter<ProvinceDetailBaseView> mPresenter;

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.recyclerSubType)
    RecyclerView recyclerSubType;
    @BindView(R.id.recyclerType)
    RecyclerView recyclerType;
    @BindView(R.id.pagerArticle)
    ViewPager pagerArticle;
    @BindView(R.id.txtArticleTitle)
    TextView txtArticleTitle;
    @BindView(R.id.imgEmpty)
    ImageView imgEmpty;
    @BindView(R.id.btnReload)
    Button btnReload;

    private List<Type> types = new ArrayList<>();
    private List<SubType> subTypes = new ArrayList<>();
    private Type currentType;
    private SubType currentSubType;
    private TypeAdapter typeAdapter;
    private SubTypeAdapter subTypeAdapter;
    private Province province;
    private CarouselPagerAdapter carouselPagerAdapter;
    private ArrayList<Article> articles = new ArrayList<>();

    private double minDistance = 0.0;
    private boolean enableLazyLoad = true, resultOfLazyLoad;
    private Location location;
    private PermissionListener locationPermissionListener;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationManager manager;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void setUp() {
        txtArticleTitle.setVisibility(View.INVISIBLE);
        province = getIntent().getParcelableExtra(ProvinceDetailActivity.EXTRA_PROVINCE);
        txtTitle.setText(province.getName());

        typeAdapter = new TypeAdapter(this.types, (object, position) -> {
            this.currentType = (Type) object;
            mPresenter.selectType(currentType.getId());
        });
        subTypeAdapter = new SubTypeAdapter(this.subTypes, ((object, position) -> {
            this.currentSubType = (SubType) object;
            minDistance = 0.0f;
            resultOfLazyLoad = false;
            exploreArticle();
        }));

        recyclerType.setAdapter(typeAdapter);
        recyclerSubType.setAdapter(subTypeAdapter);

        ViewTreeObserver vto = pagerArticle.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    pagerArticle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    pagerArticle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                VIEW_PAGER_WIDTH = pagerArticle.getMeasuredWidth();
                VIEW_PAGER_HEIGHT = pagerArticle.getMeasuredHeight();
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int pageMargin = (int) (metrics.widthPixels * 0.21);
                pagerArticle.setPageMargin(-pageMargin);
                carouselPagerAdapter = new CarouselPagerAdapter(ProvinceDetailActivity.this, pagerArticle, getSupportFragmentManager(), articles);
                pagerArticle.setAdapter(carouselPagerAdapter);
                pagerArticle.addOnPageChangeListener(carouselPagerAdapter);
                pagerArticle.setCurrentItem(0);
                pagerArticle.setOffscreenPageLimit(0);

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    pagerArticle.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            AppLogger.d(getClass().getSimpleName(), "onPageScrolled");
                            txtArticleTitle.setText(articles.get(position).getTitle());
                            if (position == articles.size() - 1 && enableLazyLoad) {
                                enableLazyLoad = false;
                                resultOfLazyLoad = true;
                                exploreArticle();
                            }
                        }

                        @Override
                        public void onPageSelected(int position) {
                            AppLogger.d(getClass().getSimpleName(), "onPageSelected");
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                            AppLogger.d(getClass().getSimpleName(), "onPageScrollStateChanged");
                        }
                    });
                } else {
                    pagerArticle.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            AppLogger.d(getClass().getSimpleName(), "onPageScrolled");
                            txtArticleTitle.setText(articles.get(position).getTitle());
                            if (position == articles.size() - 1 && enableLazyLoad) {
                                enableLazyLoad = false;
                                resultOfLazyLoad = true;
                                exploreArticle();
                            }
                        }

                        @Override
                        public void onPageSelected(int position) {
                            AppLogger.d(getClass().getSimpleName(), "onPageSelected");
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                            AppLogger.d(getClass().getSimpleName(), "onPageScrollStateChanged");
                        }
                    });
                }
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                AppLogger.d(TAG, "Shared succeed");
            }

            @Override
            public void onCancel() {
                AppLogger.d(TAG, "Shared canceled");
            }

            @Override
            public void onError(FacebookException error) {
                AppLogger.d(TAG, "Shared error");
            }
        });
    }

    @OnClick(R.id.imgBack)
    public void onBackClick(View v) {
        finish();
    }

    @OnClick(R.id.btnReload)
    public void onReloadClick(View v) {
        btnReload.setVisibility(View.INVISIBLE);
        checkLocationPermission();
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

        checkLocationPermission();
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
        resultOfLazyLoad = false;
        minDistance = 0.0f;
        exploreArticle();
    }

    @Override
    public void onExploreArticleSuccess(ArticleResponse response) {
        AppLogger.d(TAG, "Article size: " + response.getArticles().size());
        if (response.getArticles().size() < 20) {
            enableLazyLoad = false;
        } else {
            enableLazyLoad = true;
        }
        if (!resultOfLazyLoad) {
            articles.clear();
        }
        articles.addAll(response.getArticles());
        if (articles.size() == 0) {
            txtArticleTitle.setVisibility(View.INVISIBLE);
            pagerArticle.setVisibility(View.INVISIBLE);
            imgEmpty.setVisibility(View.VISIBLE);
        } else {
            minDistance = articles.get(articles.size() - 1).getDistance();
            txtArticleTitle.setVisibility(View.VISIBLE);
            pagerArticle.setVisibility(View.VISIBLE);
            imgEmpty.setVisibility(View.INVISIBLE);
        }
        carouselPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateShareLinkSuccess(String link) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(link))
                    .build();
            shareDialog.show(linkContent);
        }
    }

    @Override
    public void onGetJourneysSuccess(List<Journey> journeys) {
        AppLogger.d(TAG, "Journeys: " + journeys.size());
        List<String> datas = new ArrayList<>();
        datas.add(getString(R.string.add_new_trip));
        if (journeys.size() > 0) {
            for (int i = 0; i < journeys.size(); i++) {
                datas.add(journeys.get(i).getName());
            }
        }
        SimpleListDialog dialog = SimpleListDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getString(R.string.select_trip), datas);
        dialog.setCallback(new DialogCallback<SimpleListDialog>() {
            @Override
            public void onNegative(SimpleListDialog dialog) {
                dialog.dismissDialog(SimpleListDialog.TAG);
            }

            @Override
            public void onPositive(SimpleListDialog dialog, Object o) {
                dialog.dismissDialog(SimpleListDialog.TAG);
                int position = (int) o;
                if (position == 0) {
                    startActivityForResult(CreateTripActivity.getStartIntent(ProvinceDetailActivity.this, articles.get(position).getId()), REQUEST_ADD_TRIP);
                } else {
                    mPresenter.actionTrip(articles.get(ProvinceDetailActivity.this.position).getId(), journeys.get(position - 1).getId(), true);
                }
            }
        });
    }

    @Override
    public void onAddOrRemoveJourneySuccess() {
        articles.get(position).setMytrip(!articles.get(position).isMytrip());
        carouselPagerAdapter.notifyDataSetChanged();
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
                                    exploreArticle();
                                }
                            });
                } else {
                    new Handler().postDelayed(() -> {
                        hideLoading();
                        btnReload.setVisibility(View.VISIBLE);
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


    private int position;

    @Override
    public void onAddJourneyClick(int position) {
        this.position = position;
        boolean action = articles.get(position).isMytrip() ? true : false;
        mPresenter.actionTrip(articles.get(position).getId(), -1, action);
    }


    @Override
    public void onShareClick(int position) {
        mPresenter.createShareLink(articles.get(position).getId());
    }

    @Override
    public void onCommentClick(int position) {
        startActivity(CommentActivity.getStartIntent(this, articles.get(position)));
    }

    @Override
    public void onAddFavoriteClick(int position) {
        this.position = position;
        mPresenter.actionFavorite(articles.get(position).isFavorite() ? "remove" : "add", articles.get(position).getId());
    }

    @Override
    public void onItemClick(int position) {
        startActivity(DetailActivity.getStartIntent(this, articles.get(position)));
    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void openLogin() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @Override
    public void onActionFavoriteSuccess() {
        boolean favorite = !articles.get(position).isFavorite();
        articles.get(position).setFavorite(favorite);
        articles.get(position).setFavoriteCount(favorite ? articles.get(position).getFavoriteCount() + 1 : articles.get(position).getFavoriteCount() - 1);
        carouselPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_ADD_TRIP) {
            articles.get(position).setMytrip(true);
            carouselPagerAdapter.notifyDataSetChanged();
        }
    }
}
