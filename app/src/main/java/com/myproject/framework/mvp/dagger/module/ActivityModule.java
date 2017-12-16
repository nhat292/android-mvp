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

package com.myproject.framework.mvp.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.myproject.framework.mvp.data.network.model.BlogResponse;
import com.myproject.framework.mvp.data.network.model.OpenSourceResponse;
import com.myproject.framework.mvp.dagger.ActivityContext;
import com.myproject.framework.mvp.dagger.PerActivity;
import com.myproject.framework.mvp.ui.about.AboutMvpPresenter;
import com.myproject.framework.mvp.ui.about.AboutBaseView;
import com.myproject.framework.mvp.ui.about.AboutPresenter;
import com.myproject.framework.mvp.ui.feed.FeedMvpPresenter;
import com.myproject.framework.mvp.ui.feed.FeedBaseView;
import com.myproject.framework.mvp.ui.feed.FeedPagerAdapter;
import com.myproject.framework.mvp.ui.feed.FeedPresenter;
import com.myproject.framework.mvp.ui.feed.blogs.BlogAdapter;
import com.myproject.framework.mvp.ui.feed.blogs.BlogBaseView;
import com.myproject.framework.mvp.ui.feed.blogs.BlogMvpPresenter;
import com.myproject.framework.mvp.ui.feed.blogs.BlogPresenter;
import com.myproject.framework.mvp.ui.feed.opensource.OpenSourceAdapter;
import com.myproject.framework.mvp.ui.feed.opensource.OpenSourceBaseView;
import com.myproject.framework.mvp.ui.feed.opensource.OpenSourceMvpPresenter;
import com.myproject.framework.mvp.ui.feed.opensource.OpenSourcePresenter;
import com.myproject.framework.mvp.ui.login.LoginBaseView;
import com.myproject.framework.mvp.ui.login.LoginMvpPresenter;
import com.myproject.framework.mvp.ui.login.LoginPresenter;
import com.myproject.framework.mvp.ui.main.MainMvpPresenter;
import com.myproject.framework.mvp.ui.main.MainBaseView;
import com.myproject.framework.mvp.ui.main.MainPresenter;
import com.myproject.framework.mvp.ui.main.rating.RatingDialogMvpPresenter;
import com.myproject.framework.mvp.ui.main.rating.RatingDialogBaseView;
import com.myproject.framework.mvp.ui.main.rating.RatingDialogPresenter;
import com.myproject.framework.mvp.ui.splash.SplashBaseView;
import com.myproject.framework.mvp.ui.splash.SplashMvpPresenter;
import com.myproject.framework.mvp.ui.splash.SplashPresenter;
import com.myproject.framework.mvp.utils.rx.AppSchedulerProvider;
import com.myproject.framework.mvp.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashBaseView> provideSplashPresenter(
            SplashPresenter<SplashBaseView> presenter) {
        return presenter;
    }

    @Provides
    AboutMvpPresenter<AboutBaseView> provideAboutPresenter(
            AboutPresenter<AboutBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginBaseView> provideLoginPresenter(
            LoginPresenter<LoginBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainBaseView> provideMainPresenter(
            MainPresenter<MainBaseView> presenter) {
        return presenter;
    }

    @Provides
    RatingDialogMvpPresenter<RatingDialogBaseView> provideRateUsPresenter(
            RatingDialogPresenter<RatingDialogBaseView> presenter) {
        return presenter;
    }

    @Provides
    FeedMvpPresenter<FeedBaseView> provideFeedPresenter(
            FeedPresenter<FeedBaseView> presenter) {
        return presenter;
    }

    @Provides
    OpenSourceMvpPresenter<OpenSourceBaseView> provideOpenSourcePresenter(
            OpenSourcePresenter<OpenSourceBaseView> presenter) {
        return presenter;
    }

    @Provides
    BlogMvpPresenter<BlogBaseView> provideBlogMvpPresenter(
            BlogPresenter<BlogBaseView> presenter) {
        return presenter;
    }

    @Provides
    FeedPagerAdapter provideFeedPagerAdapter(AppCompatActivity activity) {
        return new FeedPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    OpenSourceAdapter provideOpenSourceAdapter() {
        return new OpenSourceAdapter(new ArrayList<OpenSourceResponse.Repo>());
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<BlogResponse.Blog>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
