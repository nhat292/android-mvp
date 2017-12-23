
package com.techco.igotrip.ui.info;

import com.androidnetworking.error.ANError;
import com.techco.igotrip.App;
import com.techco.igotrip.R;
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


public class InfoPresenter<V extends InfoBaseView> extends BasePresenter<V>
        implements InfoMvpPresenter<V> {


    @Inject
    public InfoPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void getUserInfo() {
        getMvpView().onGetUserInfoSuccess(getDataManager().getUserInfo());
    }


    @Override
    public void updateInfo(int id, String phone, String email, String fullname, String birthday, String address, int gender, String image64) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("phone", phone);
        params.put("email", email);
        params.put("full_name", fullname);
        params.put("birthday", birthday);
        params.put("address", address);
        params.put("gender", String.valueOf(gender));
        if (image64 != null) {
            params.put("image", image64);
        }
        getCompositeDisposable().add(getDataManager()
                .updateInfo(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    if (response.getStatus() == 0) {
                        getDataManager().setUserInfo(response.getUser());
                        getMvpView().onUpdateInfoSuccess();
                    } else if (response.getStatus() == 1) {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_account_does_not_exist));
                    }
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
