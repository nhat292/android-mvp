

package com.techco.igotrip.ui.detail;

import com.techco.igotrip.data.network.model.object.ArticleImage;
import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.data.network.model.object.User;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface DetailBaseView extends BaseView {
    void onCheckUserSuccess(User user, int type);
    void onGetImagesSuccess(List<ArticleImage> images);
    void onGetCommentCountSuccess(int count);
    void onGetUserInfoSuccess(User user);
    void openLogin();
    void onActionFavoriteSuccess();
    void onCreateShareLinkSuccess(String link);
    void onGetJourneysSuccess(List<Journey> journeys);
    void onAddOrRemoveJourneySuccess();
}
