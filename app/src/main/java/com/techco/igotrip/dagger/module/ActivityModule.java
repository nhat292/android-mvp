
package com.techco.igotrip.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.techco.igotrip.dagger.ActivityContext;
import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.changepassword.ChangePasswordBaseView;
import com.techco.igotrip.ui.changepassword.ChangePasswordMvpPresenter;
import com.techco.igotrip.ui.changepassword.ChangePasswordPresenter;
import com.techco.igotrip.ui.comment.CommentBaseView;
import com.techco.igotrip.ui.comment.CommentMvpPresenter;
import com.techco.igotrip.ui.comment.CommentPresenter;
import com.techco.igotrip.ui.createtrip.CreateTripBaseView;
import com.techco.igotrip.ui.createtrip.CreateTripMvpPresenter;
import com.techco.igotrip.ui.createtrip.CreateTripPresenter;
import com.techco.igotrip.ui.detail.DetailBaseView;
import com.techco.igotrip.ui.detail.DetailMvpPresenter;
import com.techco.igotrip.ui.detail.DetailPresenter;
import com.techco.igotrip.ui.dialog.rating.RatingDialogBaseView;
import com.techco.igotrip.ui.dialog.rating.RatingDialogMvpPresenter;
import com.techco.igotrip.ui.dialog.rating.RatingDialogPresenter;
import com.techco.igotrip.ui.direction.DirectionBaseView;
import com.techco.igotrip.ui.direction.DirectionMvpPresenter;
import com.techco.igotrip.ui.direction.DirectionPresenter;
import com.techco.igotrip.ui.experience.ExperienceBaseView;
import com.techco.igotrip.ui.experience.ExperienceMvpPresenter;
import com.techco.igotrip.ui.experience.ExperiencePresenter;
import com.techco.igotrip.ui.favorite.FavoriteBaseView;
import com.techco.igotrip.ui.favorite.FavoriteMvpPresenter;
import com.techco.igotrip.ui.favorite.FavoritePresenter;
import com.techco.igotrip.ui.info.InfoBaseView;
import com.techco.igotrip.ui.info.InfoMvpPresenter;
import com.techco.igotrip.ui.info.InfoPresenter;
import com.techco.igotrip.ui.journey.JourneyBaseView;
import com.techco.igotrip.ui.journey.JourneyMvpPresenter;
import com.techco.igotrip.ui.journey.JourneyPresenter;
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
import com.techco.igotrip.ui.mytrip.MyTripBaseView;
import com.techco.igotrip.ui.mytrip.MyTripMvpPresenter;
import com.techco.igotrip.ui.mytrip.MyTripPresenter;
import com.techco.igotrip.ui.post.PostBaseView;
import com.techco.igotrip.ui.post.PostMvpPresenter;
import com.techco.igotrip.ui.post.PostPresenter;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailBaseView;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailMvpPresenter;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailPresenter;
import com.techco.igotrip.ui.showmap.ShowMapBaseView;
import com.techco.igotrip.ui.showmap.ShowMapMvpPresenter;
import com.techco.igotrip.ui.showmap.ShowMapPresenter;
import com.techco.igotrip.ui.signup.SignUpBaseView;
import com.techco.igotrip.ui.signup.SignUpMvpPresenter;
import com.techco.igotrip.ui.signup.SignUpPresenter;
import com.techco.igotrip.ui.viewarticles.ViewArticlesBaseView;
import com.techco.igotrip.ui.viewarticles.ViewArticlesMvpPresenter;
import com.techco.igotrip.ui.viewarticles.ViewArticlesPresenter;
import com.techco.igotrip.ui.writecomment.WriteCommentBaseView;
import com.techco.igotrip.ui.writecomment.WriteCommentMvpPresenter;
import com.techco.igotrip.ui.writecomment.WriteCommentPresenter;
import com.techco.igotrip.ui.youarehere.YouAreHereBaseView;
import com.techco.igotrip.ui.youarehere.YouAreHereMvpPresenter;
import com.techco.igotrip.ui.youarehere.YouAreHerePresenter;
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
    @PerActivity
    InfoMvpPresenter<InfoBaseView> provideInfoPresenter(
            InfoPresenter<InfoBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ChangePasswordMvpPresenter<ChangePasswordBaseView> provideChangePasswordPresenter(
            ChangePasswordPresenter<ChangePasswordBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CommentMvpPresenter<CommentBaseView> provideCommentPresenter(
            CommentPresenter<CommentBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    WriteCommentMvpPresenter<WriteCommentBaseView> provideWriteCommentPresenter(
            WriteCommentPresenter<WriteCommentBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CreateTripMvpPresenter<CreateTripBaseView> provideCreateTripPresenter(
            CreateTripPresenter<CreateTripBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DetailMvpPresenter<DetailBaseView> provideDetailPresenter(
            DetailPresenter<DetailBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MyTripMvpPresenter<MyTripBaseView> provideMyTripPresenter(
            MyTripPresenter<MyTripBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FavoriteMvpPresenter<FavoriteBaseView> provideFavoritePresenter(
            FavoritePresenter<FavoriteBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ExperienceMvpPresenter<ExperienceBaseView> provideExperiencePresenter(
            ExperiencePresenter<ExperienceBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShowMapMvpPresenter<ShowMapBaseView> provideShowMapPresenter(
            ShowMapPresenter<ShowMapBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    YouAreHereMvpPresenter<YouAreHereBaseView> provideYouAreHerePresenter(
            YouAreHerePresenter<YouAreHereBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ViewArticlesMvpPresenter<ViewArticlesBaseView> provideViewArticlesPresenter(
            ViewArticlesPresenter<ViewArticlesBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PostMvpPresenter<PostBaseView> providePostPresenter(
            PostPresenter<PostBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DirectionMvpPresenter<DirectionBaseView> provideDirectionPresenter(
            DirectionPresenter<DirectionBaseView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    JourneyMvpPresenter<JourneyBaseView> provideJourneyPresenter(
            JourneyPresenter<JourneyBaseView> presenter) {
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
