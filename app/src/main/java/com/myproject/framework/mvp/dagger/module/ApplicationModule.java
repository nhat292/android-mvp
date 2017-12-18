
package com.myproject.framework.mvp.dagger.module;

import android.app.Application;
import android.content.Context;

import com.myproject.framework.mvp.BuildConfig;
import com.myproject.framework.mvp.R;
import com.myproject.framework.mvp.dagger.ApiInfo;
import com.myproject.framework.mvp.dagger.ApplicationContext;
import com.myproject.framework.mvp.dagger.DatabaseInfo;
import com.myproject.framework.mvp.dagger.PreferenceInfo;
import com.myproject.framework.mvp.data.AppDataManager;
import com.myproject.framework.mvp.data.DataManager;
import com.myproject.framework.mvp.data.db.AppDbHelper;
import com.myproject.framework.mvp.data.db.DbHelper;
import com.myproject.framework.mvp.data.network.ApiHeader;
import com.myproject.framework.mvp.data.network.ApiHelper;
import com.myproject.framework.mvp.data.network.AppApiHelper;
import com.myproject.framework.mvp.data.prefs.AppPreferencesHelper;
import com.myproject.framework.mvp.data.prefs.PreferencesHelper;
import com.myproject.framework.mvp.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
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
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
