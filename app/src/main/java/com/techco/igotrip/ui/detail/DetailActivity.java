
package com.techco.igotrip.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techco.common.AppLogger;
import com.techco.common.CommonUtils;
import com.techco.common.Utils;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.ArticleImage;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.adapter.ImagePagerAdapter;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.comment.CommentActivity;
import com.techco.igotrip.ui.createtrip.CreateTripActivity;
import com.techco.igotrip.ui.custom.circleindicator.CirclePageIndicator;
import com.techco.igotrip.ui.custom.views.MaterialBadgeTextView;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.simplelist.SimpleListDialog;
import com.techco.igotrip.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Nhat on 12/13/17.
 */


public class DetailActivity extends BaseActivity implements DetailBaseView, OnMapReadyCallback,
        ViewPager.OnPageChangeListener {

    public static final int REQUEST_ADD_TRIP = 100;
    private static final String TAG = "DetailActivity";
    private static final int CHECK_TYPE_NORMAL = 0;

    private static final int IMAGE_AUTO_CHANGE_PERIOD = 5000;
    private static final String EXTRA_ARTICLE = "ARTICLE";

    @Inject
    DetailMvpPresenter<DetailBaseView> mPresenter;

    public static Intent getStartIntent(Context context, Article article) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE, article);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.circleIndicator)
    CirclePageIndicator circleIndicator;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.imgPoster)
    CircleImageView imgPoster;
    @BindView(R.id.txtPoster)
    TextView txtPoster;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.btnComment)
    Button btnComment;
    @BindView(R.id.imgJourney)
    ImageView imgJourney;
    @BindView(R.id.imgShare)
    ImageView imgShare;
    @BindView(R.id.imgComment)
    ImageView imgComment;
    @BindView(R.id.imgHeart)
    ImageView imgHeart;
    @BindView(R.id.txtBadgeCount)
    MaterialBadgeTextView txtBadgeCount;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.txtNoImages)
    TextView txtNoImages;

    private Article article;
    private GoogleMap mGoogleMap;
    private List<ArticleImage> images = new ArrayList<>();
    private ImagePagerAdapter adapter;
    private int currentPosition;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(DetailActivity.this);

        setUp();

        mPresenter.getImages(article.getId());

        mPresenter.getCommentCount(article.getId());

        mPresenter.getUserInfo(article.getUserId());
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoScrollImage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoScrollImage();
    }

    @Override
    protected void setUp() {
        txtNoImages.setVisibility(View.INVISIBLE);

        article = getIntent().getParcelableExtra(EXTRA_ARTICLE);
        txtTitle.setText(article.getTitle());
        txtAddress.setText("        " + article.getAddressDetail());
        txtDescription.setText(CommonUtils.fromHtml(article.getDescription()));
        txtBadgeCount.setBadgeCount(article.getFavoriteCount());
        txtDate.setText(CommonUtils.getPostDate(article.getUpdatedAt()));
        updateStatus();
        mPresenter.checkUserInfo(CHECK_TYPE_NORMAL);
        btnComment.setText(String.format(getString(R.string.comment_format), 0));

        adapter = new ImagePagerAdapter(this, images);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        circleIndicator.setOnPageChangeListener(this);

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        LatLng latLng = new LatLng(article.getLat(), article.getLng());
        CameraPosition cameraPosition =
                new CameraPosition.Builder().target(latLng)
                        .zoom(15.0f)
                        .bearing(0)
                        .tilt(25)
                        .build();
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(article.getTitle())
                .snippet(article.getAddressDetail())
                .icon(BitmapDescriptorFactory.fromBitmap(Utils.createBitmapWithSize(this, R.drawable.ic_marker,
                        (int) getResources().getDimension(R.dimen.icon_marker_size_width),
                        (int) getResources().getDimension(R.dimen.icon_marker_size_height))));
        Marker marker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        marker.showInfoWindow();
    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @OnClick(R.id.btnComment)
    public void onCommentClick() {
        startActivity(CommentActivity.getStartIntent(this, article));
    }

    @OnClick(R.id.imgJourney)
    public void onJourneyClick() {
        boolean action = article.isMytrip() ? true : false;
        mPresenter.actionTrip(article.getId(), -1, action);
    }

    @OnClick(R.id.imgShare)
    public void onShareClick() {
        mPresenter.createShareLink(article.getId());
    }

    @OnClick(R.id.imgComment)
    public void onCommentImgClick() {
        onCommentClick();
    }

    @OnClick(R.id.imgHeart)
    public void onHeartClick() {
        mPresenter.actionFavorite(article.isFavorite() ? "remove" : "add", article.getId());
    }

    @OnClick(R.id.btnEdit)
    public void onEditClick() {

    }

    @Override
    public void onCheckUserSuccess(User user, int type) {
        if (type == CHECK_TYPE_NORMAL) {
            if (user != null && user.getId() == article.getUserId()) {
                btnEdit.setVisibility(View.VISIBLE);
            } else {
                btnEdit.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetImagesSuccess(List<ArticleImage> images) {
        this.images.clear();
        this.images.addAll(images);
        adapter.notifyDataSetChanged();
        if (this.images.size() > 0) {
            txtNoImages.setVisibility(View.INVISIBLE);
        } else {
            txtNoImages.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onGetCommentCountSuccess(int count) {
        btnComment.setText(String.format(getString(R.string.comment_format), count));
    }

    @Override
    public void onGetUserInfoSuccess(User user) {
        if (user != null) {
            if (!user.getImage().isEmpty()) {
                Glide.with(this)
                        .load(user.getImage())
                        .into(imgPoster);
            }
            txtPoster.setText(user.getUsername());
        }
    }

    @Override
    public void openLogin() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @Override
    public void onActionFavoriteSuccess() {
        article.setFavorite(!article.isFavorite());
        updateStatus();
        int count = article.isFavorite() ? article.getFavoriteCount() + 1 : article.getFavoriteCount() - 1;
        article.setFavoriteCount(count);
        txtBadgeCount.setBadgeCount(count);
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
                    startActivityForResult(CreateTripActivity.getStartIntent(DetailActivity.this, article.getId()), REQUEST_ADD_TRIP);
                } else {
                    mPresenter.actionTrip(article.getId(), journeys.get(position - 1).getId(), true);
                }
            }
        });
    }

    @Override
    public void onAddOrRemoveJourneySuccess() {
        article.setMytrip(!article.isMytrip());
        updateStatus();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private Timer timer;

    private void startAutoScrollImage() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (images.size() <= 0) {
                    return;
                }
                if (currentPosition < images.size() - 1) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
                }
                runOnUiThread(() -> viewPager.setCurrentItem(currentPosition, true));
            }
        }, 0, IMAGE_AUTO_CHANGE_PERIOD);
    }

    private void stopAutoScrollImage() {
        timer.cancel();
    }

    private void updateStatus() {
        if (article.isMytrip()) {
            imgJourney.setColorFilter(getResources().getColor(R.color.colorPrimary));
        } else {
            imgJourney.setColorFilter(getResources().getColor(R.color.dark_gray));
        }
        if (article.isFavorite()) {
            imgHeart.setColorFilter(getResources().getColor(R.color.colorPrimary));
        } else {
            imgHeart.setColorFilter(getResources().getColor(R.color.dark_gray));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_ADD_TRIP) {
            article.setMytrip(true);
            updateStatus();
        }
    }
}
