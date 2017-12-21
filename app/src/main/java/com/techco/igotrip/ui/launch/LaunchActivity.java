
package com.techco.igotrip.ui.launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.menu.MenuActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created by Nhat on 12/13/17.
 */


public class LaunchActivity extends BaseActivity implements LaunchBaseView {

    @Inject
    LaunchMvpPresenter<LaunchBaseView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LaunchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LaunchActivity.this);
    }

    @Override
    public void openMenuActivity() {
        startActivity(MenuActivity.getStartIntent(this));
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

    }
}
