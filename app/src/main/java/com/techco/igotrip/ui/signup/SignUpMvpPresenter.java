
package com.techco.igotrip.ui.signup;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface SignUpMvpPresenter<V extends SignUpBaseView> extends MvpPresenter<V> {

    void onSubmitClick(String username, String email, String fullname, String password, String repassword, int gender);
}
