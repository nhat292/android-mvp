
package com.myproject.framework.mvp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.myproject.framework.mvp.R;
import com.myproject.framework.mvp.ui.base.BaseActivity;
import com.myproject.framework.mvp.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class LoginActivity extends BaseActivity implements LoginBaseView {

    @Inject
    LoginMvpPresenter<LoginBaseView> mPresenter;

    @BindView(R.id.et_email)
    EditText mEmailEditText;

    @BindView(R.id.et_password)
    EditText mPasswordEditText;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);
    }

    @OnClick(R.id.btn_server_login)
    void onServerLoginClick(View v) {
        mPresenter.onServerLoginClick(mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.ib_google_login)
    void onGoogleLoginClick(View v) {
        mPresenter.onGoogleLoginClick();
    }

    @OnClick(R.id.ib_fb_login)
    void onFbLoginClick(View v) {
        mPresenter.onFacebookLoginClick();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
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
