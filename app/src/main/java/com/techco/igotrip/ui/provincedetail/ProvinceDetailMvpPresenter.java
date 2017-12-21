
package com.techco.igotrip.ui.provincedetail;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface ProvinceDetailMvpPresenter<V extends ProvinceDetailBaseView> extends MvpPresenter<V> {
    void getExploreData();
    void selectType(int typeId);
}
