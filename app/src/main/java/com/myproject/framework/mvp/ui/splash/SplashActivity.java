
package com.myproject.framework.mvp.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.myproject.framework.mvp.R;
import com.myproject.framework.mvp.ui.base.BaseActivity;
import com.myproject.framework.mvp.ui.login.LoginActivity;
import com.myproject.framework.mvp.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created by Nhat on 12/13/17.
 */


public class SplashActivity extends BaseActivity implements SplashBaseView {

    @Inject
    SplashMvpPresenter<SplashBaseView> mPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(SplashActivity.this);
    }

    /**
     * Making the screen wait so that the  branding can be shown
     */
    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void startSyncService() {
//        SyncService.start(this);
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
