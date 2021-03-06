package com.techco.igotrip.ui.custom.carousellayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.techco.igotrip.R;
import com.techco.igotrip.data.network.model.object.Article;

import java.util.ArrayList;

public class CarouselPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    private Context context;
    private FragmentManager fragmentManager;
    private float scale;
    private ArrayList<Article> articles;
    private ViewPager pager;

    public CarouselPagerAdapter(Context context, ViewPager pager, FragmentManager fm, ArrayList<Article> articles) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
        this.articles = articles;
        this.pager = pager;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            if (position == 0)
                scale = BIG_SCALE;
            else
                scale = SMALL_SCALE;

            position = position % articles.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ItemFragment.newInstance(context, position, scale, articles.get(position));
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {
            if (positionOffset >= 0f && positionOffset <= 1f) {
                CarouselLinearLayout cur = getRootView(position);
                CarouselLinearLayout next = getRootView(position + 1);

                cur.setScaleBoth(BIG_SCALE - DIFF_SCALE * positionOffset);
                next.setScaleBoth(SMALL_SCALE + DIFF_SCALE * positionOffset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressWarnings("ConstantConditions")
    public CarouselLinearLayout getRootView(int position) {
        return (CarouselLinearLayout) fragmentManager.findFragmentByTag(this.getFragmentTag(position))
                .getView().findViewById(R.id.llRoot);
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + pager.getId() + ":" + position;
    }
}