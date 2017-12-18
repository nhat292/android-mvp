
package com.myproject.framework.mvp.ui.splash;


import com.myproject.framework.mvp.dagger.PerActivity;
import com.myproject.framework.mvp.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface SplashMvpPresenter<V extends SplashBaseView> extends MvpPresenter<V> {

}
