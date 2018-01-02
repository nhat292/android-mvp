

package com.techco.igotrip.ui.showmap;

import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ShowMapBaseView extends BaseView {
    void onGetExploreDataSuccess(ExploreDataResponse response);
}
