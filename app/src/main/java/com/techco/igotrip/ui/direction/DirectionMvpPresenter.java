
package com.techco.igotrip.ui.direction;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface DirectionMvpPresenter<V extends DirectionBaseView> extends MvpPresenter<V> {
    void searchDirection(String query);
}
