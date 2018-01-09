
package com.techco.igotrip.ui.journey;

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


public class JourneyPresenter<V extends JourneyBaseView> extends BasePresenter<V>
        implements JourneyMvpPresenter<V> {


    @Inject
    public JourneyPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void loadArticles(double lat, double lng, int journeyId, boolean showLoading) {
        if (showLoading)
            getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        params.put("journey_id", String.valueOf(journeyId));
        params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        getCompositeDisposable().add(getDataManager()
                .loadJourneyArticles(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().hideRefreshLoading();
                    getMvpView().onLoadArticlesSuccess(response.getArticles());
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    getMvpView().hideRefreshLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void updateIndex(String data, int journeyId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("data", data);
        params.put("journey_id", String.valueOf(journeyId));
        params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        getCompositeDisposable().add(getDataManager()
                .updateIndex(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
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
