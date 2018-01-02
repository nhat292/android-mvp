
package com.techco.igotrip.ui.showmap;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface ShowMapMvpPresenter<V extends ShowMapBaseView> extends MvpPresenter<V> {

    void getExploreData();
}
