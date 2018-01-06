

package com.techco.igotrip.ui.direction;

import com.techco.igotrip.data.network.model.response.DResponseResult;
import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface DirectionBaseView extends BaseView {

    void onSearchDirectionSuccess(DResponseResult responseResult);
}
