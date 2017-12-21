
package com.techco.igotrip.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.techco.igotrip.dagger.ActivityContext;
import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.dialog.rating.RatingDialogBaseView;
import com.techco.igotrip.ui.dialog.rating.RatingDialogMvpPresenter;
import com.techco.igotrip.ui.dialog.rating.RatingDialogPresenter;
import com.techco.igotrip.ui.launch.LaunchBaseView;
import com.techco.igotrip.ui.launch.LaunchMvpPresenter;
import com.techco.igotrip.ui.launch.LaunchPresenter;
import com.techco.igotrip.ui.login.LoginBaseView;
import com.techco.igotrip.ui.login.LoginMvpPresenter;
import com.techco.igotrip.ui.login.LoginPresenter;
import com.techco.igotrip.ui.main.MainBaseView;
import com.techco.igotrip.ui.main.MainMvpPresenter;
import com.techco.igotrip.ui.main.MainPresenter;
import com.techco.igotrip.ui.menu.MenuBaseView;
import com.techco.igotrip.ui.menu.MenuMvpPresenter;
import com.techco.igotrip.ui.menu.MenuPresenter;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailBaseView;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailMvpPresenter;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailPresenter;
import com.techco.igotrip.ui.signup.SignUpBaseView;
import com.techco.igotrip.ui.signup.SignUpMvpPresenter;
import com.techco.igotrip.ui.signup.SignUpPresenter;
import com.techco.igotrip.utils.rx.AppSchedulerProvider;
import com.techco.igotrip.utils.rx.SchedulerProvider;

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
    LaunchMvpPresenter<LaunchBaseView> provideSplashPresenter(
            LaunchPresenter<LaunchBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MenuMvpPresenter<MenuBaseView> provideMenuPresenter(
            MenuPresenter<MenuBaseView> presenter) {
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
    SignUpMvpPresenter<SignUpBaseView> provideSignUpPresenter(
            SignUpPresenter<SignUpBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainBaseView> provideMainPresenter(
            MainPresenter<MainBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProvinceDetailMvpPresenter<ProvinceDetailBaseView> provideProvinceDetailPresenter(
            ProvinceDetailPresenter<ProvinceDetailBaseView> presenter) {
        return presenter;
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    RatingDialogMvpPresenter<RatingDialogBaseView> provideRateUsPresenter(
            RatingDialogPresenter<RatingDialogBaseView> presenter) {
        return presenter;
    }
}
