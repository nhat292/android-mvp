
package com.techco.igotrip.ui.provincedetail;

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


public class ProvinceDetailPresenter<V extends ProvinceDetailBaseView> extends BasePresenter<V>
        implements ProvinceDetailMvpPresenter<V> {


    @Inject
    public ProvinceDetailPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void getExploreData() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getExploreData().subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    //getMvpView().hideLoading();
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
    public void selectType(int typeId) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .selectType(typeId).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    //getMvpView().hideLoading();
                    getMvpView().onSelectTypeSuccess(response);
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
    public void exploreArticle(int provinceId, double lat, double lng, double minDistance, int typeId, int houseTypeId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("provinceId", String.valueOf(provinceId));
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        params.put("minDistance", String.valueOf(minDistance));
        params.put("typeId", String.valueOf(typeId));
        params.put("houseTypeId", String.valueOf(houseTypeId));
        if (getDataManager().getUserInfo() != null) {
            params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        }
        getCompositeDisposable().add(getDataManager()
                .exploreArticle(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onExploreArticleSuccess(response);
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
    public void actionFavorite(String action, int articleId) {
        if (getDataManager().getUserInfo() == null) {
            getMvpView().openLogin();
            return;
        }
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(String.valueOf(getDataManager().getUserInfo().getId())));
        params.put("action", action);
        params.put("article_id", String.valueOf(articleId));
        getCompositeDisposable().add(getDataManager()
                .actionFavorite(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onActionFavoriteSuccess();
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
    public void createShareLink(int articleId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("article_id", String.valueOf(articleId));
        getCompositeDisposable().add(getDataManager()
                .createShareLink(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onCreateShareLinkSuccess(response.getData());
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
    public void actionTrip(int articleId, int journeyId, boolean action) {
        if (getDataManager().getUserInfo() == null) {
            getMvpView().openLogin();
            return;
        }
        if (action) {
            getMvpView().showLoading();
            Map<String, String> params = new HashMap<>();
            params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
            params.put("action", journeyId == -1 ? "remove" : "add");
            params.put("article_id", String.valueOf(articleId));
            params.put("journey_id", String.valueOf(journeyId));
            getCompositeDisposable().add(getDataManager()
                    .actionTrip(params)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        getMvpView().hideLoading();
                        getMvpView().onAddOrRemoveJourneySuccess();
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
        } else {
            getMvpView().showLoading();
            Map<String, String> params = new HashMap<>();
            params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
            getCompositeDisposable().add(getDataManager()
                    .getJourneys(params)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        getMvpView().hideLoading();
                        getMvpView().onGetJourneysSuccess(response.getJourneys());
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
}
