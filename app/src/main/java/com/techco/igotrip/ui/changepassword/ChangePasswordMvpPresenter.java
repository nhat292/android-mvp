
package com.techco.igotrip.ui.changepassword;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface ChangePasswordMvpPresenter<V extends ChangePasswordBaseView> extends MvpPresenter<V> {

    void changePassword(String oldPassword, String newPassword, String reNewPassword);
}
