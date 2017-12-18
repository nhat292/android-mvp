
package com.myproject.framework.mvp.dagger.component;

import com.myproject.framework.mvp.dagger.PerService;
import com.myproject.framework.mvp.dagger.module.ServiceModule;
import com.myproject.framework.mvp.service.SyncService;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
