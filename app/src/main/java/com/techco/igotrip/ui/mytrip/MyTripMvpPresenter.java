
package com.techco.igotrip.ui.mytrip;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface MyTripMvpPresenter<V extends MyTripBaseView> extends MvpPresenter<V> {
    void getJourneys(boolean first);
    void deleteJourney(int journeyId);
}
