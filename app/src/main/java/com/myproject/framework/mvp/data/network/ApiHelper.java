
package com.myproject.framework.mvp.data.network;

import com.myproject.framework.mvp.data.network.model.BlogResponse;
import com.myproject.framework.mvp.data.network.model.LoginRequest;
import com.myproject.framework.mvp.data.network.model.LoginResponse;
import com.myproject.framework.mvp.data.network.model.LogoutResponse;
import com.myproject.framework.mvp.data.network.model.OpenSourceResponse;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ApiHelper {

    ApiHeader getApiHeader();

    Observable<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Observable<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    Observable<LogoutResponse> doLogoutApiCall();

    Observable<BlogResponse> getBlogApiCall();

    Observable<OpenSourceResponse> getOpenSourceApiCall();
}
