
package com.myproject.framework.mvp.ui.main;


import com.myproject.framework.mvp.dagger.PerActivity;
import com.myproject.framework.mvp.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface MainMvpPresenter<V extends MainBaseView> extends MvpPresenter<V> {

    void onDrawerOptionAboutClick();

    void onDrawerOptionLogoutClick();

    void onDrawerRateUsClick();

    void onDrawerMyFeedClick();

    void onViewInitialized();

    void onCardExhausted();

    void onNavMenuCreated();
}
