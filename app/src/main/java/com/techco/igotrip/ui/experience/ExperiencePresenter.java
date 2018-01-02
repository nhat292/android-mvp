
package com.techco.igotrip.ui.experience;

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


public class ExperiencePresenter<V extends ExperienceBaseView> extends BasePresenter<V>
        implements ExperienceMvpPresenter<V> {


    @Inject
    public ExperiencePresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void getUserArticles(double lat, double lng) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        getCompositeDisposable().add(getDataManager()
                .getUserArticles(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onGetUserArticlesSuccess(response.getArticles());
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
    public void addOrRemoveFavorite(int articleId, String action) {
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
                    getMvpView().onAddOrRemoveFavoriteSuccess();
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

    @Override
    public void deleteArticle(int articleId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("article_id", String.valueOf(articleId));
        getCompositeDisposable().add(getDataManager()
                .deleteArticle(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onDeleteArticleSuccess();
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
