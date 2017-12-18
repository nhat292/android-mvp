
package com.myproject.framework.mvp.dagger.component;

import android.app.Application;
import android.content.Context;

import com.myproject.framework.mvp.App;
import com.myproject.framework.mvp.data.DataManager;
import com.myproject.framework.mvp.dagger.ApplicationContext;
import com.myproject.framework.mvp.dagger.module.ApplicationModule;
import com.myproject.framework.mvp.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(App app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}