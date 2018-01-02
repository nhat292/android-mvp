
package com.techco.igotrip.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.techco.igotrip.R;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.login.LoginActivity;
import com.techco.igotrip.ui.main.MainActivity;
import com.techco.igotrip.ui.youarehere.YouAreHereActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class MenuActivity extends BaseActivity implements MenuBaseView {

    @Inject
    MenuMvpPresenter<MenuBaseView> mPresenter;

    @BindView(R.id.imgLogin)
    ImageView imgLogin;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MenuActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MenuActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkLoginStatus();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void isLogged(boolean isLogged) {
        if (isLogged) {
            imgLogin.setVisibility(View.INVISIBLE);
        } else {
            imgLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(this));
    }

    @Override
    public void openYouAreHereActivity() {
        startActivity(YouAreHereActivity.getStartIntent(this));
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @Override
    protected void setUp() {

    }

    @OnClick(R.id.imgLogin)
    public void onLoginClick(View v) {
        mPresenter.onLoginClick();
    }

    @OnClick(R.id.imgYouAreHere)
    public void onYouAreHereClick(View v) {
        mPresenter.onYouAreHereClick();
    }

    @OnClick(R.id.rlExplore)
    public void onExploreClick(View v) {
        mPresenter.onExploreClick();
    }
}
