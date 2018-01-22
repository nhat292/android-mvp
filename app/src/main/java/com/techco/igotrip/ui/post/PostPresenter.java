
package com.techco.igotrip.ui.post;

import android.graphics.Bitmap;

import com.androidnetworking.error.ANError;
import com.google.android.gms.maps.model.LatLng;
import com.techco.common.AppConstants;
import com.techco.common.Utils;
import com.techco.igotrip.App;
import com.techco.igotrip.R;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.data.network.ApiEndPoint;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class PostPresenter<V extends PostBaseView> extends BasePresenter<V>
        implements PostMvpPresenter<V> {


    @Inject
    public PostPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void searchPlaces(double lat, double lng) {
        getMvpView().showLoading();
        String url = ApiEndPoint.BASE_GOOGLE_API + "geocode/json";
        url += "?latlng=" + lat + "," + lng;
        url += "&key=" + AppConstants.GOOGLE_API_KEY;
        getCompositeDisposable().add(getDataManager()
                .searchPlacesPost(url).subscribeOn(getSchedulerProvider().io())
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
    public void getTypes() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getType().subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onGetTypesSuccess(response.getTypes());
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
                    getMvpView().onSelectTypeSuccess(response.getSubTypes());
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
    public void postArticle(LatLng latLng, String address, String title, String description, Type type, SubType subType, List<Bitmap> bitmaps) {
        if (title.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title), App.getInstance().getString(R.string.message_enter_title));
            return;
        }
        if (type == null && subType == null) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title), App.getInstance().getString(R.string.message_select_article_type));
            return;
        }
        if (bitmaps.size() == 0) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title), App.getInstance().getString(R.string.message_add_pictures));
            return;
        }
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        params.put("lat", String.valueOf(latLng.latitude));
        params.put("lng", String.valueOf(latLng.longitude));
        params.put("type_id", String.valueOf(type.getId()));
        params.put("house_type_id", String.valueOf(subType.getId()));
        params.put("nation_id", "0");
        params.put("province_id", "0");
        params.put("address_detail", address);
        params.put("title", title);
        params.put("description", description);
        String imgStr = "";
        for (Bitmap b : bitmaps) {
            String img64 = Utils.getEncoded64ImageStringFromBitmap(b);
            imgStr += img64 + "imagepost";
        }
        params.put("images", imgStr);
        getCompositeDisposable().add(getDataManager()
                .postArticle(params).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onPostArticleSuccess();
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
    public void updateArticle(int articleId, LatLng latLng, String address, String title, String description, Type type, SubType subType, List<Bitmap> bitmaps) {
        if (title.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title), App.getInstance().getString(R.string.message_enter_title));
            return;
        }
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("action", "");
        params.put("id", String.valueOf(articleId));
        params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        params.put("lat", String.valueOf(latLng.latitude));
        params.put("lng", String.valueOf(latLng.longitude));
        if(type != null) {
            params.put("type_id", String.valueOf(type.getId()));
            params.put("house_type_id", String.valueOf(subType.getId()));
        }
        params.put("nation_id", "0");
        params.put("province_id", "0");
        params.put("address_detail", address);
        params.put("title", title);
        params.put("description", description);
        String imgStr = "";
        for (Bitmap b : bitmaps) {
            String img64 = Utils.getEncoded64ImageStringFromBitmap(b);
            imgStr += img64 + "imagepost";
        }
        params.put("images", imgStr);
        getCompositeDisposable().add(getDataManager()
                .updateArticle(params).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onUpdateArticleSuccess();
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
