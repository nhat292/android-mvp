

package com.techco.igotrip.ui.menu;

import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface MenuBaseView extends BaseView {

    void isLogged(boolean isLogged);

    void openMainActivity();

    void openYouAreHereActivity();

    void openLoginActivity();
}
