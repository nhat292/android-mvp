
package com.techco.igotrip.ui.info;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface InfoMvpPresenter<V extends InfoBaseView> extends MvpPresenter<V> {

    void getUserInfo();

    void updateInfo(int id, String phone, String email, String fullname, String birthday, String address, int gender, String image64);
}
