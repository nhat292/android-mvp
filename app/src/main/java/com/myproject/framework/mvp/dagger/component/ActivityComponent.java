
package com.myproject.framework.mvp.dagger.component;

import com.myproject.framework.mvp.dagger.PerActivity;
import com.myproject.framework.mvp.dagger.module.ActivityModule;
import com.myproject.framework.mvp.ui.about.AboutFragment;
import com.myproject.framework.mvp.ui.feed.FeedActivity;
import com.myproject.framework.mvp.ui.feed.blogs.BlogFragment;
import com.myproject.framework.mvp.ui.feed.opensource.OpenSourceFragment;
import com.myproject.framework.mvp.ui.login.LoginActivity;
import com.myproject.framework.mvp.ui.main.MainActivity;
import com.myproject.framework.mvp.ui.main.rating.RateUsDialog;
import com.myproject.framework.mvp.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SplashActivity activity);

    void inject(FeedActivity activity);

    void inject(AboutFragment fragment);

    void inject(OpenSourceFragment fragment);

    void inject(BlogFragment fragment);

    void inject(RateUsDialog dialog);

}
