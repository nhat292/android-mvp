
package com.myproject.framework.mvp.ui.feed.opensource;

import com.androidnetworking.error.ANError;
import com.myproject.framework.mvp.data.DataManager;
import com.myproject.framework.mvp.data.network.model.OpenSourceResponse;
import com.myproject.framework.mvp.ui.base.BasePresenter;
import com.myproject.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Nhat on 12/13/17.
 */


public class OpenSourcePresenter<V extends OpenSourceBaseView> extends BasePresenter<V>
        implements OpenSourceMvpPresenter<V> {

    @Inject
    public OpenSourcePresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getOpenSourceApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<OpenSourceResponse>() {
                    @Override
                    public void accept(@NonNull OpenSourceResponse openSourceResponse)
                            throws Exception {
                        if (openSourceResponse != null && openSourceResponse.getData() != null) {
                            getMvpView().updateRepo(openSourceResponse.getData());
                        }
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
