
package com.techco.igotrip.ui.changepassword;

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


public class ChangePasswordPresenter<V extends ChangePasswordBaseView> extends BasePresenter<V>
        implements ChangePasswordMvpPresenter<V> {


    @Inject
    public ChangePasswordPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String reNewPassword) {
        if (oldPassword.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_enter_current_password));
            return;
        }
        if (newPassword.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_enter_new_password));
            return;
        }
        if (!newPassword.equals(reNewPassword)) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_password_does_not_match));
            return;
        }
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(getDataManager().getUserInfo().getId()));
        params.put("old_pass", oldPassword);
        params.put("new_pass", newPassword);
        getCompositeDisposable().add(getDataManager()
                .changePassword(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    if (response.getStatus() == 0) {
                        getDataManager().setUserInfo(response.getUser());
                        getMvpView().onChangePasswordSuccess();
                    } else if (response.getStatus() == 1) {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_account_does_not_exist));
                    } else {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_password_incorrect));
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
