
package com.techco.igotrip.ui.experience;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface ExperienceMvpPresenter<V extends ExperienceBaseView> extends MvpPresenter<V> {
    void getUserArticles(double lat, double lng);
    void addOrRemoveFavorite(int articleId, String action);
    void createShareLink(int articleId);
    void actionTrip(int articleId, int journeyId, boolean action);
    void deleteArticle(int articleId);
}
