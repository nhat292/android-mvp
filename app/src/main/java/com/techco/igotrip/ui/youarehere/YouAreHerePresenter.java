
package com.techco.igotrip.ui.youarehere;

import com.androidnetworking.error.ANError;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class YouAreHerePresenter<V extends YouAreHereBaseView> extends BasePresenter<V>
        implements YouAreHereMvpPresenter<V> {


    @Inject
    public YouAreHerePresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void logout() {
        getDataManager().setUserInfo(null);
        getMvpView().onCheckUserStatusSuccess(null);
    }

    @Override
    public void getExploreData() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getExploreData().subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onGetExploreDataSuccess(response);
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));

    }

    @Override
    public void checkUserStatus() {
        User user = getDataManager().getUserInfo();
        getMvpView().onCheckUserStatusSuccess(user);
    }
}
