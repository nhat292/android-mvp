
package com.techco.igotrip.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.techco.common.KeyboardUtils;
import com.techco.igotrip.R;
import com.techco.igotrip.ui.base.BaseActivity;
import com.techco.igotrip.ui.dialog.DialogCallback;
import com.techco.igotrip.ui.dialog.app.AppDialog;
import com.techco.igotrip.ui.signup.SignUpActivity;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Nhat on 12/13/17.
 */


public class LoginActivity extends BaseActivity implements LoginBaseView, TextView.OnEditorActionListener {

    public static final int SIGNUP_REQUEST = 100;

    @Inject
    LoginMvpPresenter<LoginBaseView> mPresenter;

    @BindView(R.id.editUsernameEmail)
    EditText editUsernameEmail;
    @BindView(R.id.editPassword)
    EditText editPassword;

    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;

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
        callbackManager = CallbackManager.Factory.create();
        twitterAuthClient = new TwitterAuthClient();

        mPresenter.initFacebookLogin(callbackManager);

        setUp();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkUsername();
    }

    @Override
    protected void setUp() {
        editUsernameEmail.setOnEditorActionListener(this);
        editPassword.setOnEditorActionListener(this);
    }

    @OnClick(R.id.imgClose)
    public void onCloseClick(View v) {
        finish();
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClick(View v) {
        mPresenter.onLoginClick(editUsernameEmail.getText().toString().trim(), editPassword.getText().toString().trim());
    }

    @OnClick(R.id.txtForgotPassword)
    public void onForgotPasswordClick(View v) {
        AppDialog dialog = AppDialog.newInstance();
        dialog.show(getSupportFragmentManager(), getString(R.string.forgot_password),
                getString(R.string.message_forgot_password), null, getString(android.R.string.cancel),
                getString(R.string.username_or_email_hint), InputType.TYPE_CLASS_TEXT);
        dialog.setCallback(new DialogCallback<AppDialog>() {
            @Override
            public void onNegative(AppDialog dialog) {
                dialog.dismissDialog(AppDialog.TAG);
            }

            @Override
            public void onPositive(AppDialog dialog, Object o) {
                String username = (String) o;
                if (username.isEmpty()) {
                    showMessage(R.string.message_enter_username_or_email);
                } else {
                    dialog.dismissDialog(AppDialog.TAG);
                    mPresenter.forgotPassword(username);
                }
            }
        });
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClick(View v) {
        mPresenter.onRegisterClick();
    }

    @OnClick(R.id.llLoginFacebook)
    public void onLoginFacebookClick(View v) {
        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("public_profile", "email"));
    }

    @OnClick(R.id.llLoginTwitter)
    public void onLoginTwitterClick(View v) {
        mPresenter.loginTwitter(twitterAuthClient, this);
    }

    @Override
    public void openSignUpActivity() {
        startActivityForResult(SignUpActivity.getStartIntent(this), SIGNUP_REQUEST);
    }

    @Override
    public void onCheckUsernameSuccess(String username) {
        editUsernameEmail.setText(username);
        editUsernameEmail.setSelection(username.length());
    }

    @Override
    public void onForgotPasswordSuccess() {
        showSimpleDialog(null, getString(R.string.message_forgot_password_success));
    }

    @Override
    public void onLoginSuccess() {
        finish();
    }

    @Override
    public void onUsernameAvailable(String username) {
        editUsernameEmail.setText(username);
        editUsernameEmail.setSelection(username.length());
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (textView.getId() == R.id.editPassword) {
            KeyboardUtils.hideSoftInput(this);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == SIGNUP_REQUEST) {
            finish();
        }
    }
}
