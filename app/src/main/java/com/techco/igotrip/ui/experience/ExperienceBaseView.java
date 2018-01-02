

package com.techco.igotrip.ui.experience;

import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ExperienceBaseView extends BaseView {
    void onGetUserArticlesSuccess(List<Article> articles);
    void onAddOrRemoveFavoriteSuccess();
    void onCreateShareLinkSuccess(String link);
    void onGetJourneysSuccess(List<Journey> journeys);
    void onAddOrRemoveJourneySuccess();
    void onDeleteArticleSuccess();
}
