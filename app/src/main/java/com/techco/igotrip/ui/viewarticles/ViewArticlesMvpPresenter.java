
package com.techco.igotrip.ui.viewarticles;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface ViewArticlesMvpPresenter<V extends ViewArticlesBaseView> extends MvpPresenter<V> {
    void actionFavorite(String action, int articleId);
    void createShareLink(int articleId);
    void actionTrip(int articleId, int journeyId, boolean action);
}
