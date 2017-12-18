
package com.myproject.framework.mvp.ui.feed;

import com.myproject.framework.mvp.data.DataManager;
import com.myproject.framework.mvp.ui.base.BasePresenter;
import com.myproject.framework.mvp.ui.base.BaseView;
import com.myproject.framework.mvp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class FeedPresenter<V extends BaseView> extends BasePresenter<V> implements
        FeedMvpPresenter<V> {

    private static final String TAG = "FeedPresenter";

    @Inject
    public FeedPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
