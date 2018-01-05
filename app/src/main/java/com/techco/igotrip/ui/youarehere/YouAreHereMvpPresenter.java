
package com.techco.igotrip.ui.youarehere;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface YouAreHereMvpPresenter<V extends YouAreHereBaseView> extends MvpPresenter<V> {
    void getExploreData();
    void checkUserStatus();
    void logout();
    void selectType(int typeId);
    void exploreArticle(double lat, double lng, int typeId, int houseTypeId);
}
