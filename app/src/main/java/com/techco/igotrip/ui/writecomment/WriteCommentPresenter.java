
package com.techco.igotrip.ui.writecomment;

import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class WriteCommentPresenter<V extends WriteCommentBaseView> extends BasePresenter<V>
        implements WriteCommentMvpPresenter<V> {

    @Inject
    public WriteCommentPresenter(DataManager dataManager,
                                 SchedulerProvider schedulerProvider,
                                 CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
