
package com.techco.igotrip.ui.detail;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface DetailMvpPresenter<V extends DetailBaseView> extends MvpPresenter<V> {
    void checkUserInfo(int type);
    void getImages(int articleId);
    void getCommentCount(int articleId);
    void getUserInfo(int userId);
    void actionFavorite(String action, int articleId);
    void createShareLink(int articleId);
    void actionTrip(int articleId, int journeyId, boolean action);
}
