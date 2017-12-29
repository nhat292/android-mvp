
package com.techco.igotrip.data.network;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.SelectNationResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.data.network.model.response.SimpleDataResponse;
import com.techco.igotrip.data.network.model.response.UserResponse;

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
    public Observable<UserResponse> login(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGIN)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(UserResponse.class);
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
    public Observable<UserResponse> signUp(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SIGNUP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(UserResponse.class);
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
    public Observable<UserResponse> loginSocial(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGIN_SOCIAL)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(UserResponse.class);
    }

    /**
     * id: int value
     * old_pass: string value
     * new_pass: string value
     */
    @Override
    public Observable<UserResponse> changePassword(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CHANGE_PASSWORD)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(UserResponse.class);
    }

    /**
     * id: int value
     * phone: string value
     * email: string value
     * birthday: string value
     * address: string value
     * gender: int value
     * image: base64 string
     */
    @Override
    public Observable<UserResponse> updateInfo(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_UPDATE_INFO)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(UserResponse.class);
    }

    /**
     * provinceId: int value
     * lat: double value
     * lng: double value
     * minDistance: double value
     * typeId: int value
     * houseTypeId: int value
     * user_id: int string
     */
    @Override
    public Observable<ArticleResponse> exploreArticle(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_EXPLORE_ARTICLE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(ArticleResponse.class);
    }

    /**
     * user_id: int value
     * action: string -> add or remove
     * article_id: int value
     */
    @Override
    public Observable<SimpleDataResponse> actionFavorite(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ACTION_FAVORITE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }
}

