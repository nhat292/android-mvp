
package com.techco.igotrip.data.network;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.techco.igotrip.data.network.model.response.ArticleImagesResponse;
import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.CommentListResponse;
import com.techco.igotrip.data.network.model.response.CommentResponse;
import com.techco.igotrip.data.network.model.response.CreateShareLinkResponse;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.GetCommentCountResponse;
import com.techco.igotrip.data.network.model.response.JourneyResponse;
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

    /**
     * article_id: int value
     */
    @Override
    public Observable<CommentListResponse> getComments(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_COMMENTS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(CommentListResponse.class);
    }


    /**
     * id: int value
     */
    @Override
    public Observable<SimpleDataResponse> deleteComment(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELETE_COMMENT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }

    /**
     * id: int value
     * new_content: string value
     */
    @Override
    public Observable<CommentResponse> updateComment(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_UPDATE_COMMENT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(CommentResponse.class);
    }


    /**
     * user_id: int value
     * comment_content: string value
     * article_id: int value
     */
    @Override
    public Observable<CommentResponse> createComment(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_COMMENT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(CommentResponse.class);
    }


    /**
     * article_id: int value
     */
    @Override
    public Observable<CreateShareLinkResponse> createShareLink(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_SHARE_LINK)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(CreateShareLinkResponse.class);
    }

    /**
     * user_id: int value
     */
    @Override
    public Observable<JourneyResponse> getJourneys(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_JOURNEYS)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(JourneyResponse.class);
    }

    /**
     * user_id: int value
     * action: remove or add
     * article_id: int value
     * journey_id: int value (-1 is remove)
     */
    @Override
    public Observable<SimpleDataResponse> actionTrip(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_ACTION_TRIP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }

    /**
     * user_id: int value
     * journey_name: string value
     * journey_description: string value
     * article_id: int value
     */
    @Override
    public Observable<SimpleDataResponse> createJourneyAndAddTrip(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_JOURNEY_ADD_TRIP)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }

    /**
     * user_id: int value
     * journey_name: string value
     * journey_description: string value
     */
    @Override
    public Observable<SimpleDataResponse> createJourney(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_CREATE_JOURNEY)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }

    /**
     * user_name: string value (username or email)
     */
    @Override
    public Observable<SimpleDataResponse> forgotPassword(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FORGOT_PASSWORD)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }

    /**
     * article_id: int value
     */
    @Override
    public Observable<ArticleImagesResponse> getImages(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_IMAGES)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(ArticleImagesResponse.class);
    }

    /**
     * article_id: int value
     */
    @Override
    public Observable<GetCommentCountResponse> getCommentCount(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_COMMENT_COUNT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(GetCommentCountResponse.class);
    }

    /**
     * id: int value
     */
    @Override
    public Observable<UserResponse> getUserInfo(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_USER_INFO)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(UserResponse.class);
    }

    /**
     * journey_id: int value
     */
    @Override
    public Observable<SimpleDataResponse> deleteJourney(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELETE_JOURNEY)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }

    /**
     * user_id: int value
     * lat: double value
     * lng: double value
     */
    @Override
    public Observable<ArticleResponse> getFavorites(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_FAVORITES)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(ArticleResponse.class);
    }

    /**
     * user_id: int value
     * lat: double value
     * lng: double value
     */
    @Override
    public Observable<ArticleResponse> getUserArticles(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GET_USER_ARTICLES)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(ArticleResponse.class);
    }

    /**
     * article_id: int value
     */
    @Override
    public Observable<SimpleDataResponse> deleteArticle(Map<String, String> params) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_DELETE_ARTICLE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addBodyParameter(params)
                .build()
                .getObjectObservable(SimpleDataResponse.class);
    }
}

