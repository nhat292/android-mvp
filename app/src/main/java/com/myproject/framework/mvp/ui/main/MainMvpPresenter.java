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

package com.myproject.framework.mvp.ui.main;


import com.myproject.framework.mvp.dagger.PerActivity;
import com.myproject.framework.mvp.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface MainMvpPresenter<V extends MainBaseView> extends MvpPresenter<V> {

    void onDrawerOptionAboutClick();

    void onDrawerOptionLogoutClick();

    void onDrawerRateUsClick();

    void onDrawerMyFeedClick();

    void onViewInitialized();

    void onCardExhausted();

    void onNavMenuCreated();
}
