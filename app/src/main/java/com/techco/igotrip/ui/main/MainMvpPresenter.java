
package com.techco.igotrip.ui.main;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface MainMvpPresenter<V extends MainBaseView> extends MvpPresenter<V> {

    void getFirstData();

    void selectNation(int nationId, boolean showLoading, boolean isFirstTime);

    void checkUserStatus();

    void logout();
}
