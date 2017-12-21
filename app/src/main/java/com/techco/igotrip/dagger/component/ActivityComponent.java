
package com.techco.igotrip.dagger.component;

import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.dagger.module.ActivityModule;
import com.techco.igotrip.ui.dialog.rating.RateUsDialog;
import com.techco.igotrip.ui.launch.LaunchActivity;
import com.techco.igotrip.ui.login.LoginActivity;
import com.techco.igotrip.ui.main.MainActivity;
import com.techco.igotrip.ui.menu.MenuActivity;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailActivity;
import com.techco.igotrip.ui.signup.SignUpActivity;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(RateUsDialog dialog);


    void inject(LaunchActivity activity);
    void inject(MenuActivity activity);
    void inject(LoginActivity activity);
    void inject(SignUpActivity activity);
    void inject(MainActivity activity);
    void inject(ProvinceDetailActivity activity);

}
