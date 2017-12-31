
package com.techco.igotrip.ui.createtrip;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface CreateTripMvpPresenter<V extends CreateTripBaseView> extends MvpPresenter<V> {
    void createAndAddTrip(String name, String description, int articleId);
}
