
package com.techco.igotrip.data.network;


import com.techco.igotrip.BuildConfig;

/**
 * Created by Nhat on 12/13/17.
 */


public final class ApiEndPoint {

    public static String BASE_URL = BuildConfig.BASE_URL;

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
    }

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }

}
