
package com.techco.igotrip.ui.post;


import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface PostMvpPresenter<V extends PostBaseView> extends MvpPresenter<V> {
    void searchPlaces(double lat, double lng);
    void getTypes();
    void selectType(int typeId);
    void postArticle(LatLng latLng, String address, String title, String description, Type type, SubType subType, List<Bitmap> bitmaps);
    void updateArticle(int articleId, LatLng latLng, String address, String title, String description, Type type, SubType subType, List<Bitmap> bitmaps);
}
