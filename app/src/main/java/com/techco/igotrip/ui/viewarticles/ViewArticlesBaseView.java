

package com.techco.igotrip.ui.viewarticles;

import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ViewArticlesBaseView extends BaseView {

    void openLogin();

    void onActionFavoriteSuccess();

    void onCreateShareLinkSuccess(String link);

    void onGetJourneysSuccess(List<Journey> journeys);

    void onAddOrRemoveJourneySuccess();
}
