
package com.techco.igotrip.data.network;

import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.LoginResponse;
import com.techco.igotrip.data.network.model.response.SelectNationResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;

import java.util.Map;

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
    public Observable<FirstDataResponse> getFirstData() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_FIRST_DATA)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectObservable(FirstDataResponse.class);
    }

    /**
     * nation_id: integer value
     */
    @Override
    public Observable<SelectNationResponse> selectNation(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SELECT_NATION)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SelectNationResponse.class);
    }

    /**
     * none
     */
    @Override
    public Observable<ExploreDataResponse> getExploreData() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_EXPLORE_DATA)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectObservable(ExploreDataResponse.class);
    }

    /**
     * type_id: integer value
     */
    @Override
    public Observable<SelectTypeResponse> selectType(int typeId) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SELECT_TYPE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter("type_id", String.valueOf(typeId))
                .build()
                .getObjectObservable(SelectTypeResponse.class);
    }

    /**
     * username: string value
     * password: string value
     * device_type: string value --> android
     * device_id: string value
     */
    @Override
    public Observable<LoginResponse> login(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGIN)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    /**
     * user_name: string value
     * full_name: string value
     * password: string value
     * gender: int value
     * device_type: string value --> android
     * device_id: string value
     */
    @Override
    public Observable<LoginResponse> signUp(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SIGNUP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    /**
     * user_name: string value
     * full_name: string value
     * gender: int value
     * image: string value
     * type: string value --> facebook or twitter
     * device_type: string value --> android
     * device_id: string value
     */
    @Override
    public Observable<LoginResponse> loginSocial(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGIN_SOCIAL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(LoginResponse.class);
    }
}

