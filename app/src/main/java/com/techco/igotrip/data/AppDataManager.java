
package com.techco.igotrip.data;


import android.content.Context;

import com.techco.igotrip.dagger.ApplicationContext;
import com.techco.igotrip.data.network.ApiHeader;
import com.techco.igotrip.data.network.ApiHelper;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.data.network.model.response.ArticleImagesResponse;
import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.CommentListResponse;
import com.techco.igotrip.data.network.model.response.CreateShareLinkResponse;
import com.techco.igotrip.data.network.model.response.DResponseResult;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.GetCommentCountResponse;
import com.techco.igotrip.data.network.model.response.JourneyResponse;
import com.techco.igotrip.data.network.model.response.PlacesResponse;
import com.techco.igotrip.data.network.model.response.SearchPlaceResponse;
import com.techco.igotrip.data.network.model.response.SimpleDataResponse;
import com.techco.igotrip.data.network.model.response.CommentResponse;
import com.techco.igotrip.data.network.model.response.TypesResponse;
import com.techco.igotrip.data.network.model.response.UserResponse;
import com.techco.igotrip.data.network.model.response.ProvinceResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.data.prefs.PreferencesHelper;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<FirstDataResponse> getFirstData() {
        return mApiHelper.getFirstData();
    }

    @Override
    public Observable<ProvinceResponse> selectNation(Map<String, String> params) {
        return mApiHelper.selectNation(params);
    }

    @Override
    public Observable<ExploreDataResponse> getExploreData() {
        return mApiHelper.getExploreData();
    }

    @Override
    public Observable<SelectTypeResponse> selectType(int typeId) {
        return mApiHelper.selectType(typeId);
    }

    @Override
    public Observable<UserResponse> login(Map<String, String> params) {
        return mApiHelper.login(params);
    }

    @Override
    public Observable<UserResponse> signUp(Map<String, String> params) {
        return mApiHelper.signUp(params);
    }

    @Override
    public Observable<UserResponse> loginSocial(Map<String, String> params) {
        return mApiHelper.loginSocial(params);
    }

    @Override
    public Observable<UserResponse> changePassword(Map<String, String> params) {
        return mApiHelper.changePassword(params);
    }

    @Override
    public Observable<UserResponse> updateInfo(Map<String, String> params) {
        return mApiHelper.updateInfo(params);
    }

    @Override
    public Observable<ArticleResponse> exploreArticle(Map<String, String> params) {
        return mApiHelper.exploreArticle(params);
    }

    @Override
    public Observable<SimpleDataResponse> actionFavorite(Map<String, String> params) {
        return mApiHelper.actionFavorite(params);
    }

    @Override
    public Observable<CommentListResponse> getComments(Map<String, String> params) {
        return mApiHelper.getComments(params);
    }

    @Override
    public Observable<SimpleDataResponse> deleteComment(Map<String, String> params) {
        return mApiHelper.deleteComment(params);
    }

    @Override
    public Observable<CommentResponse> updateComment(Map<String, String> params) {
        return mApiHelper.updateComment(params);
    }

    @Override
    public Observable<CommentResponse> createComment(Map<String, String> params) {
        return mApiHelper.createComment(params);
    }

    @Override
    public Observable<CreateShareLinkResponse> createShareLink(Map<String, String> params) {
        return mApiHelper.createShareLink(params);
    }

    @Override
    public Observable<JourneyResponse> getJourneys(Map<String, String> params) {
        return mApiHelper.getJourneys(params);
    }

    @Override
    public Observable<SimpleDataResponse> actionTrip(Map<String, String> params) {
        return mApiHelper.actionTrip(params);
    }

    @Override
    public Observable<SimpleDataResponse> createJourneyAndAddTrip(Map<String, String> params) {
        return mApiHelper.createJourneyAndAddTrip(params);
    }

    @Override
    public Observable<SimpleDataResponse> createJourney(Map<String, String> params) {
        return mApiHelper.createJourney(params);
    }

    @Override
    public Observable<SimpleDataResponse> forgotPassword(Map<String, String> params) {
        return mApiHelper.forgotPassword(params);
    }

    @Override
    public Observable<ArticleImagesResponse> getImages(Map<String, String> params) {
        return mApiHelper.getImages(params);
    }

    @Override
    public Observable<GetCommentCountResponse> getCommentCount(Map<String, String> params) {
        return mApiHelper.getCommentCount(params);
    }

    @Override
    public Observable<UserResponse> getUserInfo(Map<String, String> params) {
        return mApiHelper.getUserInfo(params);
    }

    @Override
    public Observable<SimpleDataResponse> deleteJourney(Map<String, String> params) {
        return mApiHelper.deleteJourney(params);
    }

    @Override
    public Observable<ArticleResponse> getFavorites(Map<String, String> params) {
        return mApiHelper.getFavorites(params);
    }

    @Override
    public Observable<ArticleResponse> getUserArticles(Map<String, String> params) {
        return mApiHelper.getUserArticles(params);
    }

    @Override
    public Observable<SimpleDataResponse> deleteArticle(Map<String, String> params) {
        return mApiHelper.deleteArticle(params);
    }

    @Override
    public Observable<SearchPlaceResponse> searchPlaces(String url) {
        return mApiHelper.searchPlaces(url);
    }

    @Override
    public Observable<ArticleResponse> getArticleProvince(Map<String, String> params) {
        return mApiHelper.getArticleProvince(params);
    }

    @Override
    public Observable<ProvinceResponse> getProvinces(Map<String, String> params) {
        return mApiHelper.getProvinces(params);
    }

    @Override
    public Observable<ArticleResponse> getArticlesNearBy(Map<String, String> params) {
        return mApiHelper.getArticlesNearBy(params);
    }

    @Override
    public Observable<PlacesResponse> searchPlacesPost(String url) {
        return mApiHelper.searchPlacesPost(url);
    }

    @Override
    public Observable<TypesResponse> getType() {
        return mApiHelper.getType();
    }

    @Override
    public Observable<SimpleDataResponse> postArticle(Map<String, String> params) {
        return mApiHelper.postArticle(params);
    }

    @Override
    public Observable<SimpleDataResponse> updateArticle(Map<String, String> params) {
        return mApiHelper.updateArticle(params);
    }

    @Override
    public Observable<DResponseResult> searchDirection(String url) {
        return mApiHelper.searchDirection(url);
    }

    @Override
    public Observable<ArticleResponse> loadJourneyArticles(Map<String, String> params) {
        return mApiHelper.loadJourneyArticles(params);
    }

    @Override
    public Observable<SimpleDataResponse> updateIndex(Map<String, String> params) {
        return mApiHelper.updateIndex(params);
    }

    @Override
    public void setUserInfo(User user) {
        mPreferencesHelper.setUserInfo(user);
    }

    @Override
    public User getUserInfo() {
        return mPreferencesHelper.getUserInfo();
    }

    @Override
    public void setUsername(String username) {
        mPreferencesHelper.setUsername(username);
    }

    @Override
    public String getUsername() {
        return mPreferencesHelper.getUsername();
    }
}
