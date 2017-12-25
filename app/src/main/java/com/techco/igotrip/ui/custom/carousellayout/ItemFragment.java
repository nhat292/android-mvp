package com.techco.igotrip.ui.custom.carousellayout;

import android.annotation.SuppressLint;
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
import com.techco.igotrip.ui.provincedetail.ProvinceDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemFragment extends Fragment {

    private static final String POSITON = "position";
    private static final String SCALE = "scale";
    private static final String DATA = "data";

    @BindView(R.id.llRoot)
    CarouselLinearLayout llRoot;
    @BindView(R.id.llContain)
    LinearLayout llContain;
    @BindView(R.id.imgItem)
    ImageView imgItem;
    @BindView(R.id.webViewItem)
    WebView webViewItem;


    public static Fragment newInstance(Context context, int pos, float scale, Article article) {
        Bundle b = new Bundle();
        b.putInt(POSITON, pos);
        b.putFloat(SCALE, scale);
        b.putParcelable(DATA, article);

        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final int postion = this.getArguments().getInt(POSITON);
        float scale = this.getArguments().getFloat(SCALE);
        final Article article = this.getArguments().getParcelable(DATA);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (ProvinceDetailActivity.VIEW_PAGER_WIDTH * 0.9),
                (int) (ProvinceDetailActivity.VIEW_PAGER_HEIGHT * 0.98));
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);

        llContain.setLayoutParams(layoutParams);
        llRoot.setScaleBoth(scale);

        Glide.with(getActivity())
                .load(ApiEndPoint.BASE_URL + article.getImage())
                .centerCrop()
                .into(imgItem);
        webViewItem.loadData(article.getDescription(), "text/html", "UTF-8");


        llContain.setOnClickListener(view1 -> {

        });


        return view;
    }

    @OnClick(R.id.imgJourney)
    public void onJourneyClick() {

    }

    @OnClick(R.id.imgShare)
    public void onShareClick() {

    }

    @OnClick(R.id.imgComment)
    public void onCommentClick() {

    }

    @OnClick(R.id.imgJourney)
    public void onAddFavoriteClick() {

    }
}
