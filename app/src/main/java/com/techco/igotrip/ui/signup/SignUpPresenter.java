
package com.techco.igotrip.ui.signup;

import com.androidnetworking.error.ANError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.techco.common.RegularUtil;
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


public class SignUpPresenter<V extends SignUpBaseView> extends BasePresenter<V>
        implements SignUpMvpPresenter<V> {


    @Inject
    public SignUpPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void onSubmitClick(String username, String email, String fullname, String password, String repassword, int gender) {
        if (username.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_empty_username));
            return;
        }
        if (email.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_empty_email));
            return;
        }
        if (!RegularUtil.isValidEmail(email)) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_email_invalid));
            return;
        }
        if (password.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_empty_password));
            return;
        }
        if (!repassword.equals(repassword)) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_password_does_not_match));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("user_name", username);
        params.put("full_name", fullname);
        params.put("password", password);
        params.put("gender", String.valueOf(gender));
        params.put("device_type", "android");
        params.put("device_id", FirebaseInstanceId.getInstance().getToken());
        getCompositeDisposable().add(getDataManager()
                .signUp(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    if (response.getStatus() == 0) {
                        getDataManager().setUserInfo(response.getUser());
                        getDataManager().setUsername(response.getUser().getUsername());
                        getMvpView().onRegisterSuccess();
                    } else if (response.getStatus() == 1) {
                        getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                App.getInstance().getString(R.string.message_username_password_exists));
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
