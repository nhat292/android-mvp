

package com.techco.igotrip.ui.provincedetail;

import com.techco.igotrip.data.network.model.response.ArticleResponse;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ProvinceDetailBaseView extends BaseView {

    void onGetExploreDataSuccess(ExploreDataResponse response);

    void onSelectTypeSuccess(SelectTypeResponse response);

    void onExploreArticleSuccess(ArticleResponse response);

    void openLogin();

    void onActionFavoriteSuccess();
}
