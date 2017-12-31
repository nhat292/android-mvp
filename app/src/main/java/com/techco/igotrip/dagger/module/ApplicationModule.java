
package com.techco.igotrip.dagger.module;

import android.app.Application;
import android.content.Context;

import com.techco.common.AppConstants;
import com.techco.igotrip.BuildConfig;
import com.techco.igotrip.dagger.ApiInfo;
import com.techco.igotrip.dagger.ApplicationContext;
import com.techco.igotrip.dagger.DatabaseInfo;
import com.techco.igotrip.dagger.PreferenceInfo;
import com.techco.igotrip.data.AppDataManager;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.data.network.ApiHeader;
import com.techco.igotrip.data.network.ApiHelper;
import com.techco.igotrip.data.network.AppApiHelper;
import com.techco.igotrip.data.prefs.AppPreferencesHelper;
import com.techco.igotrip.data.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nhat on 12/13/17.
 */


@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getUserInfo() != null ? (long) preferencesHelper.getUserInfo().getId() : 0,
                preferencesHelper.getUserInfo() != null ? preferencesHelper.getUserInfo().getToken() : "");
    }

    /*@Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }*/
}
