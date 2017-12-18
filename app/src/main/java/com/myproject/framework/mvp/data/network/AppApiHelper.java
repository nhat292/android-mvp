
package com.myproject.framework.mvp.data.network;

import com.myproject.framework.mvp.data.network.model.BlogResponse;
import com.myproject.framework.mvp.data.network.model.LoginRequest;
import com.myproject.framework.mvp.data.network.model.LoginResponse;
import com.myproject.framework.mvp.data.network.model.LogoutResponse;
import com.myproject.framework.mvp.data.network.model.OpenSourceResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Observable<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest
                                                                  request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    @Override
    public Observable<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest
                                                                    request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest
                                                                  request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    @Override
    public Observable<LogoutResponse> doLogoutApiCall() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectObservable(LogoutResponse.class);
    }

    @Override
    public Observable<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectObservable(BlogResponse.class);
    }

    @Override
    public Observable<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectObservable(OpenSourceResponse.class);
    }
}

