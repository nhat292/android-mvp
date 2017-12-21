
package com.techco.igotrip.dagger.component;

import com.techco.igotrip.dagger.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */
@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}
