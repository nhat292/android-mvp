
package com.techco.igotrip.ui.showmap;

import com.androidnetworking.error.ANError;
import com.techco.common.AppConstants;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.data.network.ApiEndPoint;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class ShowMapPresenter<V extends ShowMapBaseView> extends BasePresenter<V>
        implements ShowMapMvpPresenter<V> {


    @Inject
    public ShowMapPresenter(DataManager dataManager,
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
    public void selectType(int typeId) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .selectType(typeId).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
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
    public void searchPlaces(String query, String nextPageToken) {
        getMvpView().showLoading();
        String url = ApiEndPoint.BASE_GOOGLE_API + "place/textsearch/json";
        if (nextPageToken == null) {
            try {
                url += "?query=" + URLEncoder.encode(query, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                url += "?query=" + query.replace(" ", "%20");
                e.printStackTrace();
            }
        } else {
            url += "?pagetoken=" + nextPageToken;
        }
        url += "&key=" + AppConstants.GOOGLE_API_KEY;
        getCompositeDisposable().add(getDataManager()
                .searchPlaces(url).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onSearchPlacesSuccess(response);
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
    public void exploreArticle(int provinceId, double lat, double lng, int typeId, int subTypeId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("province_id", String.valueOf(provinceId));
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        params.put("type_id", String.valueOf(typeId));
        params.put("house_type_id", String.valueOf(subTypeId));
        if (getDataManager().getUserInfo() != null) {
            params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        }
        getCompositeDisposable().add(getDataManager()
                .getArticleProvince(params).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onGetArticlesSuccess(response.getArticles());
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
    public void getProvinces(int nationId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("nation_id", String.valueOf(nationId));
        getCompositeDisposable().add(getDataManager()
                .getProvinces(params).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onGetProvincesSuccess(response.getProvinces());
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
