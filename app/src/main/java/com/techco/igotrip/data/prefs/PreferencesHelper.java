
package com.techco.igotrip.data.prefs;

import com.techco.igotrip.data.network.model.object.User;

/**
 * Created by Nhat on 12/13/17.
 */


public interface PreferencesHelper {

    void setUserInfo(User User);

    User getUserInfo();

    void setUsername(String username);

    String getUsername();

}
