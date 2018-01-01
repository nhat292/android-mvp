
package com.techco.igotrip.ui.favorite;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface FavoriteMvpPresenter<V extends FavoriteBaseView> extends MvpPresenter<V> {
    void getFavorites(double lat, double lng);
    void removeFavorite(int articleId);
    void createShareLink(int articleId);
    void actionTrip(int articleId, int journeyId, boolean action);
}
