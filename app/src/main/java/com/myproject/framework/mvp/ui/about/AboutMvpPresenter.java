
package com.myproject.framework.mvp.ui.about;


import com.myproject.framework.mvp.dagger.PerActivity;
import com.myproject.framework.mvp.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface AboutMvpPresenter<V extends AboutBaseView> extends MvpPresenter<V> {

}
