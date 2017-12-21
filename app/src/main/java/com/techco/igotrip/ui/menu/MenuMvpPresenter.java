
package com.techco.igotrip.ui.menu;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface MenuMvpPresenter<V extends MenuBaseView> extends MvpPresenter<V> {

    void checkLoginStatus();

    void onLoginClick();

    void onYouAreHereClick();

    void onExploreClick();
}
