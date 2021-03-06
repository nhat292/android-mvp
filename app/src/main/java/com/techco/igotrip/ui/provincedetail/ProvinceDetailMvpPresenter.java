
package com.techco.igotrip.ui.provincedetail;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface ProvinceDetailMvpPresenter<V extends ProvinceDetailBaseView> extends MvpPresenter<V> {
    void getExploreData();
    void selectType(int typeId);
    void exploreArticle(int provinceId, double lat, double lng, double minDistance, int typeId, int houseTypeId);
    void actionFavorite(String action, int articleId);
    void createShareLink(int articleId);
    void actionTrip(int articleId, int journeyId, boolean action);
}
