
package com.techco.igotrip.ui.dialog.rating;


import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


public interface RatingDialogMvpPresenter<V extends RatingDialogBaseView> extends MvpPresenter<V> {

    void onRatingSubmitted(float rating, String message);

    void onCancelClicked();

    void onLaterClicked();

    void onPlayStoreRatingClicked();
}
