
package com.techco.igotrip.ui.main;

import com.androidnetworking.error.ANError;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class MainPresenter<V extends MainBaseView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {


    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void getFirstData() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getFirstData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> getMvpView().onGetFirstDataSuccess(response), throwable -> {
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
    public void selectNation(int nationId, boolean showLoading, boolean isFirstTime) {
        if (showLoading && !isFirstTime)
            getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("nation_id", String.valueOf(nationId));
        getCompositeDisposable().add(getDataManager()
                .selectNation(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onSelectNationSuccess(response);
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

    @Override
    public void logout() {
        getDataManager().setUserInfo(null);
        getMvpView().onCheckUserStatusSuccess(null);
    }
}
