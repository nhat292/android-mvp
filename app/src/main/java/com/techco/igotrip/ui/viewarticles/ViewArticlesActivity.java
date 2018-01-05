
package com.techco.igotrip.ui.viewarticles;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.techco.common.AppLogger;
import com.techco.igotrip.R;
import com.techco.igotrip.callback.ArticleActionCallback;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.comment.CommentActivity;
import com.techco.igotrip.ui.createtrip.CreateTripActivity;
import com.techco.igotrip.ui.custom.carousellayout.CarouselPagerAdapter;
import com.techco.igotrip.ui.detail.DetailActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.simplelist.SimpleListDialog;
import com.techco.igotrip.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class ViewArticlesActivity extends BaseActivity implements ViewArticlesBaseView, ArticleActionCallback {

    private static final String EXTRA_ARTICLES = "ARTICLES";
    public static final int REQUEST_ADD_TRIP = 100;
    private static final String TAG = "ViewArticlesActivity";

    public static int VIEW_PAGER_WIDTH = 0;
    public static int VIEW_PAGER_HEIGHT = 0;

    @Inject
    ViewArticlesMvpPresenter<ViewArticlesBaseView> mPresenter;


    public static Intent getStartIntent(Context context, ArrayList<Article> articles) {
        Intent intent = new Intent(context, ViewArticlesActivity.class);
        intent.putParcelableArrayListExtra(ViewArticlesActivity.EXTRA_ARTICLES, articles);
        return intent;
    }

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtArticleTitle)
    TextView txtArticleTitle;
    @BindView(R.id.pagerArticle)
    ViewPager pagerArticle;


    private CarouselPagerAdapter carouselPagerAdapter;
    private ArrayList<Article> articles;
    private int position = 0;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_articles);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ViewArticlesActivity.this);

        setUp();
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick(R.id.imgBack)
    public void onBackClick() {
        finish();
    }

    @Override
    protected void setUp() {
        articles = getIntent().getParcelableArrayListExtra(EXTRA_ARTICLES);

        txtTitle.setText(getString(R.string.view_articles));

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
                carouselPagerAdapter = new CarouselPagerAdapter(ViewArticlesActivity.this, pagerArticle, getSupportFragmentManager(), articles);
                pagerArticle.setAdapter(carouselPagerAdapter);
                pagerArticle.addOnPageChangeListener(carouselPagerAdapter);
                pagerArticle.setCurrentItem(0);
                pagerArticle.setOffscreenPageLimit(0);

                pagerArticle.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        AppLogger.d(getClass().getSimpleName(), "onPageScrolled");
                        txtArticleTitle.setText(articles.get(position).getTitle());
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
        });

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

    @Override
    public void onItemClick(int position) {
        startActivity(DetailActivity.getStartIntent(this, articles.get(position)));
    }

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
    public void onCreateShareLinkSuccess(String link) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(link))
                    .build();
            shareDialog.show(linkContent);
        }
    }

    @Override
    public void onAddOrRemoveJourneySuccess() {
        articles.get(position).setMytrip(!articles.get(position).isMytrip());
        carouselPagerAdapter.notifyDataSetChanged();
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
                    startActivityForResult(CreateTripActivity.getStartIntent(ViewArticlesActivity.this, articles.get(position).getId()), REQUEST_ADD_TRIP);
                } else {
                    mPresenter.actionTrip(articles.get(position).getId(), journeys.get(position - 1).getId(), true);
                }
            }
        });
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
