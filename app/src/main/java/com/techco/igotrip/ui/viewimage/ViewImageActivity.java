package com.techco.igotrip.ui.viewimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.adapter.ImageViewPagerAdapter;
import com.techco.igotrip.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nhat on 1/22/18.
 */

public class ViewImageActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ArrayList<String> mData;
    private ImageViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUnBinder(ButterKnife.bind(this));

        setUp();
    }

    @Override
    protected void setUp() {
        Intent intent = getIntent();
        mData = intent.getStringArrayListExtra("IMAGES");
        int position = intent.getIntExtra("POSITION", 0);


        mAdapter = new ImageViewPagerAdapter(this, mData);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(position);
    }
}
