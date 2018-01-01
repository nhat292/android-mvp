
package com.techco.igotrip.ui.mytrip;

import com.androidnetworking.error.ANError;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class MyTripPresenter<V extends MyTripBaseView> extends BasePresenter<V>
        implements MyTripMvpPresenter<V> {


    @Inject
    public MyTripPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void getJourneys(boolean first) {
        if (first) {
            getMvpView().showLoading();
        } else {
            getMvpView().onShowRefreshLoading();
        }
        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        getCompositeDisposable().add(getDataManager()
                .getJourneys(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onHideRefreshLoading();
                    getMvpView().onGetJourneysSuccess(response.getJourneys());
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().onHideRefreshLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void deleteJourney(int journeyId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("journey_id", String.valueOf(journeyId));
        getCompositeDisposable().add(getDataManager()
                .deleteJourney(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onDeleteJourneySuccess();
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
}
