
package com.myproject.framework.mvp.ui.about;

import com.myproject.framework.mvp.data.DataManager;
import com.myproject.framework.mvp.ui.base.BasePresenter;
import com.myproject.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class AboutPresenter<V extends AboutBaseView> extends BasePresenter<V>
        implements AboutMvpPresenter<V> {

    @Inject
    public AboutPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
