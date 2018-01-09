

package com.techco.igotrip.ui.journey;

import com.techco.igotrip.data.network.model.object.Article;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface JourneyBaseView extends BaseView {

    void hideRefreshLoading();

    void onLoadArticlesSuccess(List<Article> articles);
}
