
package com.techco.igotrip.data.network;

import com.techco.igotrip.data.network.model.response.ArticleImagesResponse;
import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.CommentListResponse;
import com.techco.igotrip.data.network.model.response.CommentResponse;
import com.techco.igotrip.data.network.model.response.CreateShareLinkResponse;
import com.techco.igotrip.data.network.model.response.DResponseResult;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.GetCommentCountResponse;
import com.techco.igotrip.data.network.model.response.JourneyResponse;
import com.techco.igotrip.data.network.model.response.PlacesResponse;
import com.techco.igotrip.data.network.model.response.SearchPlaceResponse;
import com.techco.igotrip.data.network.model.response.ProvinceResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.data.network.model.response.SimpleDataResponse;
import com.techco.igotrip.data.network.model.response.TypesResponse;
import com.techco.igotrip.data.network.model.response.UserResponse;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ApiHelper {

    ApiHeader getApiHeader();

    Observable<FirstDataResponse> getFirstData();

    Observable<ProvinceResponse> selectNation(Map<String, String> params);

    Observable<ExploreDataResponse> getExploreData();

    Observable<SelectTypeResponse> selectType(int typeId);

    Observable<UserResponse> login(Map<String, String> params);

    Observable<UserResponse> signUp(Map<String, String> params);

    Observable<UserResponse> loginSocial(Map<String, String> params);

    Observable<UserResponse> changePassword(Map<String, String> params);

    Observable<UserResponse> updateInfo(Map<String, String> params);

    Observable<ArticleResponse> exploreArticle(Map<String, String> params);

    Observable<SimpleDataResponse> actionFavorite(Map<String, String> params);

    Observable<CommentListResponse> getComments(Map<String, String> params);

    Observable<SimpleDataResponse> deleteComment(Map<String, String> params);

    Observable<CommentResponse> updateComment(Map<String, String> params);

    Observable<CommentResponse> createComment(Map<String, String> params);

    Observable<CreateShareLinkResponse> createShareLink(Map<String, String> params);

    Observable<JourneyResponse> getJourneys(Map<String, String> params);

    Observable<SimpleDataResponse> actionTrip(Map<String, String> params);

    Observable<SimpleDataResponse> createJourneyAndAddTrip(Map<String, String> params);

    Observable<SimpleDataResponse> createJourney(Map<String, String> params);

    Observable<SimpleDataResponse> forgotPassword(Map<String, String> params);

    Observable<ArticleImagesResponse> getImages(Map<String, String> params);

    Observable<GetCommentCountResponse> getCommentCount(Map<String, String> params);

    Observable<UserResponse> getUserInfo(Map<String, String> params);

    Observable<SimpleDataResponse> deleteJourney(Map<String, String> params);

    Observable<ArticleResponse> getFavorites(Map<String, String> params);

    Observable<ArticleResponse> getUserArticles(Map<String, String> params);

    Observable<SimpleDataResponse> deleteArticle(Map<String, String> params);

    Observable<SearchPlaceResponse> searchPlaces(String url);

    Observable<ArticleResponse> getArticleProvince(Map<String, String> params);

    Observable<ProvinceResponse> getProvinces(Map<String, String> params);

    Observable<ArticleResponse> getArticlesNearBy(Map<String, String> params);

    Observable<PlacesResponse> searchPlacesPost(String url);

    Observable<TypesResponse> getType();

    Observable<SimpleDataResponse> postArticle(Map<String, String> params);

    Observable<SimpleDataResponse> updateArticle(Map<String, String> params);

    Observable<DResponseResult> searchDirection(String url);

    Observable<ArticleResponse> loadJourneyArticles(Map<String, String> params);

    Observable<SimpleDataResponse> updateIndex(Map<String, String> params);
}
