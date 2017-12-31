
package com.techco.igotrip.ui.createtrip;

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


public class CreateTripPresenter<V extends CreateTripBaseView> extends BasePresenter<V>
        implements CreateTripMvpPresenter<V> {

    @Inject
    public CreateTripPresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }


    @Override
    public void createAndAddTrip(String name, String description, int articleId) {
        if (name.isEmpty()) {
            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                    App.getInstance().getString(R.string.message_trip_name_empty));
            return;
        }
        if (articleId != -1) {
            getMvpView().showLoading();
            Map<String, String> params = new HashMap<>();
            params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
            params.put("journey_name", name);
            params.put("journey_description", description);
            params.put("article_id", String.valueOf(articleId));
            getCompositeDisposable().add(getDataManager()
                    .createJourneyAndAddTrip(params)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        getMvpView().hideLoading();
                        if (response.getStatus() == 0) {
                            getMvpView().onCreateJourneySuccess();
                        } else {
                            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                    String.format(App.getInstance().getString(R.string.message_trip_name_existed_format), name));
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
        } else {
            getMvpView().showLoading();
            Map<String, String> params = new HashMap<>();
            params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
            params.put("journey_name", name);
            params.put("journey_description", description);
            getCompositeDisposable().add(getDataManager()
                    .createJourney(params)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        getMvpView().hideLoading();
                        if (response.getStatus() == 0) {
                            getMvpView().onCreateJourneySuccess();
                        } else {
                            getMvpView().showSimpleDialog(App.getInstance().getString(R.string.error_title),
                                    String.format(App.getInstance().getString(R.string.message_trip_name_existed_format), name));
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
}
