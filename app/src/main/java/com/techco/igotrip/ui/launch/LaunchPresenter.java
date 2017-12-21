
package com.techco.igotrip.ui.launch;

import android.os.Handler;

import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class LaunchPresenter<V extends LaunchBaseView> extends BasePresenter<V>
        implements LaunchMvpPresenter<V> {

    public static final int DEFAULT_SPLASH_TIMEOUT = 2000;

    @Inject
    public LaunchPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        new Handler().postDelayed(() -> {
            if (!isViewAttached()) {
                return;
            }
            getMvpView().openMenuActivity();
        }, DEFAULT_SPLASH_TIMEOUT);
    }
}
