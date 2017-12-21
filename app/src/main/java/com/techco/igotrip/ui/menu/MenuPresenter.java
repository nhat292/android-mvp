
package com.techco.igotrip.ui.menu;

import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class MenuPresenter<V extends MenuBaseView> extends BasePresenter<V>
        implements MenuMvpPresenter<V> {


    @Inject
    public MenuPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void onLoginClick() {
        getMvpView().openLoginActivity();
    }

    @Override
    public void onYouAreHereClick() {
        getMvpView().openYouAreHereActivity();
    }

    @Override
    public void onExploreClick() {
        getMvpView().openMainActivity();
    }

    @Override
    public void checkLoginStatus() {
        if(getDataManager().getUserInfo() != null) {
            getMvpView().isLogged(true);
        } else {
            getMvpView().isLogged(false);
        }
    }
}
