
package com.techco.igotrip.ui.dialog.rating;


import com.techco.igotrip.ui.base.DialogBaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface RatingDialogBaseView extends DialogBaseView {

    void openPlayStoreForRating();

    void showPlayStoreRatingView();

    void showRatingMessageView();

    void hideSubmitButton();

    void disableRatingStars();

    void dismissDialog();
}
