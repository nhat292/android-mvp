
package com.techco.igotrip.ui.login;

import android.app.Activity;
import android.os.Bundle;

import com.androidnetworking.error.ANError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.techco.common.AppLogger;
import com.techco.igotrip.App;
import com.techco.igotrip.R;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;

/**
 * Created by Nhat on 12/13/17.
 */


public class LoginPresenter<V extends LoginBaseView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    private static final String TAG = "LoginPresenter";


    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        User user = getDataManager().getUserInfo();
        if (user != null) {
            if (user.getAccountType() == User.NORMAL) {
                getMvpView().onUsernameAvailable(user.getUsername());
            }
        }
    }

    @Override
    public void checkUsername() {
        getMvpView().onCheckUsernameSuccess(getDataManager().getUsername());
    }

    @Override
    public void onRegisterClick() {
        getMvpView().openSignUpActivity();
    }

    @Override
    public void onLoginClick(String username, String password) {
        if (username.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_empty_username_email));
            return;
        }
        if (password.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_empty_password));
            return;
        }
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("user_name", username);
        params.put("password", password);
        params.put("device_type", "android");
        params.put("device_id", FirebaseInstanceId.getInstance().getToken());
        getCompositeDisposable().add(getDataManager()
                .login(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    if (response.getStatus() == 0) {
                        getDataManager().setUserInfo(response.getUser());
                        getDataManager().setUsername(response.getUser().getUsername());
                        getMvpView().onLoginSuccess();
                    } else if (response.getStatus() == 1) {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_password_incorrect));
                    } else {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_account_does_not_exist));
                    }
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void initFacebookLogin(CallbackManager callbackManager) {
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
                            try {
                                final String id = object.getString("id");
                                final String name = object.optString("name");
                                final String email = object.getString("email");
                                final String genderString = object.getString("gender");
                                final String image = "https://graph.facebook.com/" + id + "/picture?type=large";
                                final int gender = genderString.equals("male") ? User.MALE : User.FEMALE;
                                Map<String, String> params = new HashMap<>();
                                params.put("user_name", email);
                                params.put("full_name", name);
                                params.put("gender", String.valueOf(gender));
                                params.put("image", image);
                                params.put("type", "facebook");
                                params.put("device_type", "android");
                                params.put("device_id", "");
                                getMvpView().showLoading();
                                getCompositeDisposable().add(getDataManager()
                                        .loginSocial(params)
                                        .subscribeOn(getSchedulerProvider().io())
                                        .observeOn(getSchedulerProvider().ui())
                                        .subscribe(loginResponse -> {
                                            getMvpView().hideLoading();
                                            getDataManager().setUserInfo(loginResponse.getUser());
                                            getMvpView().onLoginSuccess();
                                        }, throwable -> {
                                            if (!isViewAttached()) {
                                                return;
                                            }
                                            getMvpView().hideLoading();
                                            // handle the error here
                                            if (throwable instanceof ANError) {
                                                ANError anError = (ANError) throwable;
                                                handleApiError(anError);
                                            }
                                        }));
                            } catch (JSONException e) {
                                LoginManager.getInstance().logOut();
                                e.printStackTrace();
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email, gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_unknown_error));
                    }
                });
    }

    @Override
    public void loginTwitter(TwitterAuthClient twitterAuthClient, Activity context) {
        twitterAuthClient.authorize(context, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                getMvpView().showLoading();
                TwitterSession session = result.data;
                Call<com.twitter.sdk.android.core.models.User> call = TwitterCore.getInstance().getApiClient(session).getAccountService()
                        .verifyCredentials(true, false, true);
                call.enqueue(new Callback<com.twitter.sdk.android.core.models.User>() {
                    @Override
                    public void success(Result<com.twitter.sdk.android.core.models.User> userResult) {
                        clearTwitterSession();
                        String name = userResult.data.name;
                        String email = userResult.data.email;
                        String image = userResult.data.profileImageUrl;
                        Map<String, String> params = new HashMap<>();
                        params.put("user_name", email);
                        params.put("full_name", name);
                        params.put("gender", "0");
                        params.put("image", image);
                        params.put("type", "twitter");
                        params.put("device_type", "android");
                        params.put("device_id", "");
                        getCompositeDisposable().add(getDataManager()
                                .loginSocial(params)
                                .subscribeOn(getSchedulerProvider().io())
                                .observeOn(getSchedulerProvider().ui())
                                .subscribe(loginResponse -> {
                                    getMvpView().hideLoading();
                                    getDataManager().setUserInfo(loginResponse.getUser());
                                    getMvpView().onLoginSuccess();
                                }, throwable -> {
                                    if (!isViewAttached()) {
                                        return;
                                    }
                                    getMvpView().hideLoading();
                                    // handle the error here
                                    if (throwable instanceof ANError) {
                                        ANError anError = (ANError) throwable;
                                        handleApiError(anError);
                                    }
                                }));
                    }

                    @Override
                    public void failure(TwitterException e) {
                        AppLogger.d(TAG, e.getMessage());
                        clearTwitterSession();
                        getMvpView().hideLoading();
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_unknown_error));
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                AppLogger.d(TAG, exception.getMessage());
                clearTwitterSession();
                getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                        App.getInstance().getString(R.string.message_unknown_error));
            }
        });
    }

    private void clearTwitterSession() {
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
    }

    @Override
    public void forgotPassword(String username) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("user_name", username);
        getCompositeDisposable().add(getDataManager()
                .forgotPassword(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    if (response.getStatus() == 0) {
                        getMvpView().onForgotPasswordSuccess();
                    } else {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_username_or_email_does_not_exists));
                    }
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }
}
