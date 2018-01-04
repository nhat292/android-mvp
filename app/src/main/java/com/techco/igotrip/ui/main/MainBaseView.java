

package com.techco.igotrip.ui.main;

import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.ProvinceResponse;
import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface MainBaseView extends BaseView {

    void onGetFirstDataSuccess(FirstDataResponse response);

    void onSelectNationSuccess(ProvinceResponse response);

    void onCheckUserStatusSuccess(User user);
}
