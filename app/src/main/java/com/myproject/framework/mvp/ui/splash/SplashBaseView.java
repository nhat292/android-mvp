

package com.myproject.framework.mvp.ui.splash;

import com.myproject.framework.mvp.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface SplashBaseView extends BaseView {

    void openLoginActivity();

    void openMainActivity();

    void startSyncService();
}
