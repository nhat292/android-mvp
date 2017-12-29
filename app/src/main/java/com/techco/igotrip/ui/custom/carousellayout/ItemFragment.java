package com.techco.igotrip.ui.custom.carousellayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.techco.igotrip.R;
import com.techco.igotrip.data.network.ApiEndPoint;
import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.ui.custom.views.MaterialBadgeTextView;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemFragment extends Fragment {

    public static final String POSITION = "position";
    public static final String SCALE = "scale";
    public static final String DATA = "data";

    @BindView(R.id.llRoot)
    CarouselLinearLayout llRoot;
    @BindView(R.id.llContain)
    LinearLayout llContain;
    @BindView(R.id.imgItem)
    ImageView imgItem;
    @BindView(R.id.webViewItem)
    WebView webViewItem;
    @BindView(R.id.txtBadgeCount)
    MaterialBadgeTextView txtBadgeCount;
    @BindView(R.id.imgJourney)
    ImageView imgJourney;
    @BindView(R.id.imgShare)
    ImageView imgShare;
    @BindView(R.id.imgComment)
    ImageView imgComment;
    @BindView(R.id.imgHeart)
    ImageView imgHeart;

    private int position;
    private Activity activity;


    public static Fragment newInstance(Context context, int pos, float scale, Article article) {
        Bundle b = new Bundle();
        b.putInt(POSITION, pos);
        b.putFloat(SCALE, scale);
        b.putParcelable(DATA, article);
        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        position = this.getArguments().getInt(POSITION);
        float scale = this.getArguments().getFloat(SCALE);
        final Article article = this.getArguments().getParcelable(DATA);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (ProvinceDetailActivity.VIEW_PAGER_WIDTH * 0.9),
                (int) (ProvinceDetailActivity.VIEW_PAGER_HEIGHT * 0.98));
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);

        llContain.setLayoutParams(layoutParams);
        llRoot.setScaleBoth(scale);

        update(article);

        llContain.setOnClickListener(view1 -> {
            if (activity instanceof ProvinceDetailActivity) {
                ((ProvinceDetailActivity) activity).onItemClick(position);
            }
        });


        return view;
    }

    @OnClick(R.id.imgJourney)
    public void onJourneyClick() {
        if (activity instanceof ProvinceDetailActivity) {
            ((ProvinceDetailActivity) activity).onAddJourneyClick(position);
        }
    }

    @OnClick(R.id.imgShare)
    public void onShareClick() {
        if (activity instanceof ProvinceDetailActivity) {
            ((ProvinceDetailActivity) activity).onShareClick(position);
        }
    }

    @OnClick(R.id.imgComment)
    public void onCommentClick() {
        if (activity instanceof ProvinceDetailActivity) {
            ((ProvinceDetailActivity) activity).onCommentClick(position);
        }
    }

    @OnClick(R.id.imgHeart)
    public void onAddFavoriteClick() {
        if (activity instanceof ProvinceDetailActivity) {
            ((ProvinceDetailActivity) activity).onAddFavoriteClick(position);
        }
    }

    public void update(Article article) {
        Glide.with(getActivity())
                .load(ApiEndPoint.BASE_URL + article.getImage())
                .centerCrop()
                .into(imgItem);
        webViewItem.loadData(article.getDescription(), "text/html", "UTF-8");
        txtBadgeCount.setBadgeCount(article.getFavoriteCount(), false);

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
}
