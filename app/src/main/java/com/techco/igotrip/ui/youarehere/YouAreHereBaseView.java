

package com.techco.igotrip.ui.youarehere;

import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface YouAreHereBaseView extends BaseView {
    void onCheckUserStatusSuccess(User user);
    void onGetExploreDataSuccess(ExploreDataResponse response);
    void onSelectTypeSuccess(SelectTypeResponse response);
    void onExploreArticleSuccess(List<Article> articles);
}
