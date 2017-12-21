
package com.techco.igotrip.ui.base;

/**
 * Created by Nhat on 12/13/17.
 */


import android.support.annotation.StringRes;

import com.techco.igotrip.ui.dialog.DialogCallback;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface BaseView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showSimpleDialog(String title, String message);

    void showConfirmDialog(String title, String message, DialogCallback callback);

    boolean isNetworkConnected();

    void hideKeyboard();

}
