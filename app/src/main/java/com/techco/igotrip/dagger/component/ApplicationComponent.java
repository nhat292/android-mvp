
package com.techco.igotrip.dagger.component;

import android.app.Application;
import android.content.Context;

import com.techco.igotrip.App;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.dagger.ApplicationContext;
import com.techco.igotrip.dagger.module.ApplicationModule;
import com.techco.igotrip.service.SyncService;

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