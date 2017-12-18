
package com.myproject.framework.mvp.ui.feed.blogs;

import com.myproject.framework.mvp.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


public interface BlogMvpPresenter<V extends BlogBaseView>
        extends MvpPresenter<V> {

    void onViewPrepared();
}


