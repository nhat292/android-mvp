
package com.techco.igotrip.ui.login;


import android.app.Activity;

import com.facebook.CallbackManager;
import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface LoginMvpPresenter<V extends LoginBaseView> extends MvpPresenter<V> {

    void onRegisterClick();

    void onLoginClick(String username, String password);

    void initFacebookLogin(CallbackManager callbackManager);

    void loginTwitter(TwitterAuthClient twitterAuthClient, Activity context);
}
