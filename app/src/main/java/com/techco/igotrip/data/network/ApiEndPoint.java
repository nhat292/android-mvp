
package com.techco.igotrip.data.network;


import com.techco.igotrip.BuildConfig;

/**
 * Created by Nhat on 12/13/17.
 */


public final class ApiEndPoint {

    public static String BASE_URL = BuildConfig.BASE_URL;
    public static String BASE_GOOGLE_API = "https://maps.googleapis.com/maps/api/";

    public static String ENDPOINT_FIRST_DATA = BASE_URL
            + "api/v1/first-data";

    public static String ENDPOINT_SELECT_NATION = BASE_URL
            + "api/v1/select-nation";

    public static String ENDPOINT_EXPLORE_DATA = BASE_URL
            + "api/v1/explore-data";

    public static String ENDPOINT_SELECT_TYPE = BASE_URL
            + "api/v1/explore-select-type";

    public static String ENDPOINT_LOGIN = BASE_URL
            + "api/v1/login";

    public static String ENDPOINT_SIGNUP = BASE_URL
            + "api/v1/register";

    public static String ENDPOINT_LOGIN_SOCIAL = BASE_URL
            + "api/v1/login-social";

    public static String ENDPOINT_CHANGE_PASSWORD = BASE_URL
            + "api/v1/change-password";

    public static String ENDPOINT_UPDATE_INFO = BASE_URL
            + "api/v1/update-info";

    public static String ENDPOINT_EXPLORE_ARTICLE = BASE_URL
            + "api/v1/explore-article";

    public static String ENDPOINT_ACTION_FAVORITE = BASE_URL
            + "api/v1/action-favorite";

    public static String ENDPOINT_GET_COMMENTS = BASE_URL
            + "api/v1/get-comments";

    public static String ENDPOINT_DELETE_COMMENT = BASE_URL
            + "api/v1/delete-comment";

    public static String ENDPOINT_UPDATE_COMMENT = BASE_URL
            + "api/v1/update-comment";

    public static String ENDPOINT_CREATE_COMMENT = BASE_URL
            + "api/v1/create-comment";

    public static String ENDPOINT_CREATE_SHARE_LINK = BASE_URL
            + "api/v1/create-share-link";

    public static String ENDPOINT_GET_JOURNEYS = BASE_URL
            + "api/v1/get-journeys";

    public static String ENDPOINT_ACTION_TRIP = BASE_URL
            + "api/v1/action-trip";

    public static String ENDPOINT_CREATE_JOURNEY_ADD_TRIP = BASE_URL
            + "api/v1/create-journey-and-add-user-trip";

    public static String ENDPOINT_CREATE_JOURNEY = BASE_URL
            + "api/v1/create-journey";

    public static String ENDPOINT_FORGOT_PASSWORD = BASE_URL
            + "api/v1/forgot-password";

    public static String ENDPOINT_GET_IMAGES = BASE_URL
            + "api/v1/article-images";

    public static String ENDPOINT_GET_COMMENT_COUNT = BASE_URL
            + "api/v1/get-comment-count";

    public static String ENDPOINT_GET_USER_INFO = BASE_URL
            + "api/v1/user-info";

    public static String ENDPOINT_DELETE_JOURNEY = BASE_URL
            + "api/v1/delete_journey";

    public static String ENDPOINT_GET_FAVORITES = BASE_URL
            + "api/v1/get-favorites";

    public static String ENDPOINT_GET_USER_ARTICLES = BASE_URL
            + "api/v1/user-articles";

    public static String ENDPOINT_DELETE_ARTICLE = BASE_URL
            + "api/v1/delete-article";

    public static String ENDPOINT_ARTICLE_PROVINCE = BASE_URL
            + "api/v1/articles-province";

    public static String ENDPOINT_GET_PROVINCES = BASE_URL
            + "api/v1/get-provinces-by-nation";

    public static String ENDPOINT_GET_ARTICLES_NEARBY = BASE_URL
            + "api/v1/articles-nearby";

    public static String ENDPOINT_GET_TYPES = BASE_URL
            + "api/v1/get-type";

    public static String ENDPOINT_POST_ARTICLE = BASE_URL
            + "api/v1/post-article";

    public static String ENDPOINT_UPDATE_ARTICLE = BASE_URL
            + "api/v1/update-article";

    public static String ENDPOINT_JOURNEY_ARTICLES = BASE_URL
            + "api/v1/get-journey-articles";

    public static String ENDPOINT_UPDATE_INDEX = BASE_URL
            + "api/v1/update-index";

    public static void changeBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
        ENDPOINT_FIRST_DATA = BASE_URL + "api/v1/first-data";
        ENDPOINT_SELECT_NATION = BASE_URL + "api/v1/select-nation";
        ENDPOINT_EXPLORE_DATA = BASE_URL + "api/v1/explore-data";
        ENDPOINT_SELECT_TYPE = BASE_URL + "api/v1/explore-select-type";
        ENDPOINT_LOGIN = BASE_URL + "api/v1/login";
        ENDPOINT_SIGNUP = BASE_URL + "api/v1/register";
        ENDPOINT_LOGIN_SOCIAL = BASE_URL + "api/v1/login-social";
        ENDPOINT_CHANGE_PASSWORD = BASE_URL + "api/v1/change-password";
        ENDPOINT_CHANGE_PASSWORD = BASE_URL + "api/v1/update-info";
        ENDPOINT_EXPLORE_ARTICLE = BASE_URL + "api/v1/explore-article";
        ENDPOINT_ACTION_FAVORITE = BASE_URL + "api/v1/action-favorite";
        ENDPOINT_GET_COMMENTS = BASE_URL + "api/v1/get-comments";
        ENDPOINT_DELETE_COMMENT = BASE_URL + "api/v1/delete-comment";
        ENDPOINT_UPDATE_COMMENT = BASE_URL + "api/v1/update-comment";
        ENDPOINT_CREATE_COMMENT = BASE_URL + "api/v1/create-comment";
        ENDPOINT_CREATE_SHARE_LINK = BASE_URL + "api/v1/create-share-link";
        ENDPOINT_GET_JOURNEYS = BASE_URL + "api/v1/get-journeys";
        ENDPOINT_ACTION_TRIP = BASE_URL + "api/v1/action-trip";
        ENDPOINT_CREATE_JOURNEY_ADD_TRIP = BASE_URL + "api/v1/create-journey-and-add-user-trip";
        ENDPOINT_CREATE_JOURNEY = BASE_URL + "api/v1/create-journey";
        ENDPOINT_FORGOT_PASSWORD = BASE_URL + "api/v1/forgot-password";
        ENDPOINT_GET_IMAGES = BASE_URL + "api/v1/article-images";
        ENDPOINT_GET_COMMENT_COUNT = BASE_URL + "api/v1/get-comment-count";
        ENDPOINT_GET_USER_INFO = BASE_URL + "api/v1/user-info";
        ENDPOINT_DELETE_JOURNEY = BASE_URL + "api/v1/delete_journey";
        ENDPOINT_GET_FAVORITES = BASE_URL + "api/v1/get-favorites";
        ENDPOINT_GET_USER_ARTICLES = BASE_URL + "api/v1/user-articles";
        ENDPOINT_DELETE_ARTICLE = BASE_URL + "api/v1/delete-article";
        ENDPOINT_ARTICLE_PROVINCE = BASE_URL + "api/v1/articles-province";
        ENDPOINT_GET_PROVINCES = BASE_URL + "api/v1/get-provinces-by-nation";
        ENDPOINT_GET_ARTICLES_NEARBY = BASE_URL + "api/v1/articles-nearby";
        ENDPOINT_GET_TYPES = BASE_URL + "api/v1/get-type";
        ENDPOINT_POST_ARTICLE = BASE_URL + "api/v1/post-article";
        ENDPOINT_UPDATE_ARTICLE = BASE_URL + "api/v1/update-article";
        ENDPOINT_JOURNEY_ARTICLES = BASE_URL + "api/v1/get-journey-articles";
        ENDPOINT_UPDATE_INDEX = BASE_URL + "api/v1/update-index";
    }

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
