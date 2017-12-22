
package com.techco.igotrip.data;


import android.content.Context;

import com.techco.igotrip.dagger.ApplicationContext;
import com.techco.igotrip.data.network.ApiHeader;
import com.techco.igotrip.data.network.ApiHelper;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.LoginResponse;
import com.techco.igotrip.data.network.model.response.SelectNationResponse;
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
    public Observable<SelectNationResponse> selectNation(Map<String, String> params) {
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
    public Observable<LoginResponse> login(Map<String, String> params) {
        return mApiHelper.login(params);
    }

    @Override
    public Observable<LoginResponse> signUp(Map<String, String> params) {
        return mApiHelper.signUp(params);
    }

    @Override
    public Observable<LoginResponse> loginSocial(Map<String, String> params) {
        return mApiHelper.loginSocial(params);
    }

    @Override
    public Observable<LoginResponse> changePassword(Map<String, String> params) {
        return mApiHelper.changePassword(params);
    }


    @Override
    public void setUserInfo(User user) {
        mPreferencesHelper.setUserInfo(user);
    }

    @Override
    public User getUserInfo() {
        return mPreferencesHelper.getUserInfo();
    }
}
