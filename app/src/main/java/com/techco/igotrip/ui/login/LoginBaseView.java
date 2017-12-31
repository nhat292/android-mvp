

package com.techco.igotrip.ui.login;

import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface LoginBaseView extends BaseView {

    void onCheckUsernameSuccess(String username);

    void openSignUpActivity();

    void onForgotPasswordSuccess();

    void onLoginSuccess();

    void onUsernameAvailable(String username);

}
