/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.myproject.framework.mvp.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Nhat on 12/13/17.
 */


public abstract class BaseSubView extends ViewGroup implements SubBaseView {

    private BaseView mParentBaseView;

    public BaseSubView(Context context) {
        super(context);
    }

    public BaseSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BaseSubView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void attachParentMvpView(BaseView baseView) {
        mParentBaseView = baseView;
    }

    @Override
    public void showLoading() {
        if (mParentBaseView != null) {
            mParentBaseView.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mParentBaseView != null) {
            mParentBaseView.hideLoading();
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        if (mParentBaseView != null) {
            mParentBaseView.onError(resId);
        }
    }

    @Override
    public void onError(String message) {
        if (mParentBaseView != null) {
            mParentBaseView.onError(message);
        }
    }

    @Override
    public void showMessage(String message) {
        if (mParentBaseView != null) {
            mParentBaseView.showMessage(message);
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (mParentBaseView != null) {
            mParentBaseView.showMessage(resId);
        }
    }

    @Override
    public void hideKeyboard() {
        if (mParentBaseView != null) {
            mParentBaseView.hideKeyboard();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mParentBaseView != null) {
            return mParentBaseView.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void openActivityOnTokenExpire() {
        if (mParentBaseView != null) {
            mParentBaseView.openActivityOnTokenExpire();
        }
    }

    protected abstract void bindViewsAndSetOnClickListeners();

    protected abstract void setUp();
}
