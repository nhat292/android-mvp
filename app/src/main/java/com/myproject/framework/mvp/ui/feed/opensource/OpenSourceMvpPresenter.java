
package com.myproject.framework.mvp.ui.feed.opensource;

import com.myproject.framework.mvp.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


public interface OpenSourceMvpPresenter<V extends OpenSourceBaseView>
        extends MvpPresenter<V> {

    void onViewPrepared();
}
