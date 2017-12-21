
package com.techco.igotrip.dagger.component;

import com.techco.igotrip.dagger.PerService;
import com.techco.igotrip.dagger.module.ServiceModule;
import com.techco.igotrip.service.SyncService;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
