
package com.techco.igotrip;

import android.app.Application;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level;
import com.crashlytics.android.Crashlytics;
import com.techco.common.AppLogger;
import com.techco.igotrip.dagger.component.ApplicationComponent;
import com.techco.igotrip.dagger.component.DaggerApplicationComponent;
import com.techco.igotrip.dagger.module.ApplicationModule;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.ui.launch.LaunchActivity;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import javax.inject.Inject;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import io.fabric.sdk.android.Fabric;


/**
 * Created by Nhat on 12/13/17.
 */


public class App extends Application {

    @Inject
    DataManager mDataManager;

    /*@Inject
    CalligraphyConfig mCalligraphyConfig;*/

    private ApplicationComponent mApplicationComponent;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                        getString(R.string.twitter_consumer_secret_key)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
        mApplicationComponent.inject(this);
        AppLogger.init();
        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY);
        }
        //CalligraphyConfig.initDefault(mCalligraphyConfig);
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .errorDrawable(R.mipmap.ic_launcher) //default: bug image
                .restartActivity(LaunchActivity.class) //default: null (your app's launch activity)
                .apply();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public static App getInstance() {
        return instance;
    }
}
