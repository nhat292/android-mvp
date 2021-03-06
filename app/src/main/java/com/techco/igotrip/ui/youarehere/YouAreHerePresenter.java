
package com.techco.igotrip.ui.youarehere;

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
                   // getMvpView().hideLoading();
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
    public void exploreArticle(double lat, double lng, int typeId, int houseTypeId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        params.put("type_id", String.valueOf(typeId));
        params.put("house_type_id", String.valueOf(houseTypeId));
        if (getDataManager().getUserInfo() != null) {
            params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        }
        getCompositeDisposable().add(getDataManager()
                .getArticlesNearBy(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onExploreArticleSuccess(response.getArticles());
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
    public void onPostClick() {
        if(getDataManager().getUserInfo() != null) {
            getMvpView().openPost();
        } else {
            getMvpView().openLogin();
        }
    }
}
