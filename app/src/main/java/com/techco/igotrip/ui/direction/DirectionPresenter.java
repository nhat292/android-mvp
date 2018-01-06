
package com.techco.igotrip.ui.direction;

import com.androidnetworking.error.ANError;
import com.techco.common.AppConstants;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.data.network.ApiEndPoint;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class DirectionPresenter<V extends DirectionBaseView> extends BasePresenter<V>
        implements DirectionMvpPresenter<V> {


    @Inject
    public DirectionPresenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void searchDirection(String query) {
        getMvpView().showLoading();
        String url = ApiEndPoint.BASE_GOOGLE_API + "directions/json?" + query + "&key=" + AppConstants.GOOGLE_API_KEY;
        getCompositeDisposable().add(getDataManager()
                .searchDirection(url).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onSearchDirectionSuccess(response);
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
