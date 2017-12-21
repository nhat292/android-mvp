
package com.techco.igotrip.ui.launch;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface LaunchMvpPresenter<V extends LaunchBaseView> extends MvpPresenter<V> {

}
