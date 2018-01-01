

package com.techco.igotrip.ui.favorite;

import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface FavoriteBaseView extends BaseView {
    void onGetFavoritesSuccess(List<Article> articles);
    void onRemoveFavoriteSuccess();
    void onCreateShareLinkSuccess(String link);
    void onGetJourneysSuccess(List<Journey> journeys);
    void onAddOrRemoveJourneySuccess();
}
