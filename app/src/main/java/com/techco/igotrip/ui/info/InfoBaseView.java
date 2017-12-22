

package com.techco.igotrip.ui.info;

import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface InfoBaseView extends BaseView {

    void onGetUserInfoSuccess(User user);
}
