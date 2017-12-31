

package com.techco.igotrip.ui.provincedetail;

import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ProvinceDetailBaseView extends BaseView {

    void onGetExploreDataSuccess(ExploreDataResponse response);

    void onSelectTypeSuccess(SelectTypeResponse response);

    void onExploreArticleSuccess(ArticleResponse response);

    void openLogin();

    void onActionFavoriteSuccess();

    void onCreateShareLinkSuccess(String link);

    void onGetJourneysSuccess(List<Journey> journeys);

    void onAddOrRemoveJourneySuccess();
}
