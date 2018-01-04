

package com.techco.igotrip.ui.showmap;

import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.data.network.model.object.Province;
import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.SearchPlaceResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ShowMapBaseView extends BaseView {
    void onGetExploreDataSuccess(ExploreDataResponse response);
    void onSelectTypeSuccess(SelectTypeResponse response);
    void onSearchPlacesSuccess(SearchPlaceResponse response);
    void onGetArticlesSuccess(List<Article> articles);
    void onGetProvincesSuccess(List<Province> provinces);
}
