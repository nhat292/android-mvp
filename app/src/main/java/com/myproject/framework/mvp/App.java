
package com.myproject.framework.mvp;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor.Level;
import com.myproject.framework.mvp.data.DataManager;
import com.myproject.framework.mvp.dagger.component.ApplicationComponent;
import com.myproject.framework.mvp.dagger.component.DaggerApplicationComponent;
import com.myproject.framework.mvp.dagger.module.ApplicationModule;
import com.myproject.framework.mvp.utils.AppLogger;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Nhat on 12/13/17.
 */


public class App extends Application {

    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY);
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
