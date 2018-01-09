
package com.techco.igotrip.ui.journey;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface JourneyMvpPresenter<V extends JourneyBaseView> extends MvpPresenter<V> {
    void loadArticles(double lat, double lng, int journeyId, boolean showLoading);
    void updateIndex(String data, int journeyId);
}
