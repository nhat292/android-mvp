
package com.techco.igotrip.dagger.component;

import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.dagger.module.ActivityModule;
import com.techco.igotrip.ui.changepassword.ChangePasswordActivity;
import com.techco.igotrip.ui.comment.CommentActivity;
import com.techco.igotrip.ui.createtrip.CreateTripActivity;
import com.techco.igotrip.ui.detail.DetailActivity;
import com.techco.igotrip.ui.dialog.rating.RateUsDialog;
import com.techco.igotrip.ui.direction.DirectionActivity;
import com.techco.igotrip.ui.experience.ExperienceActivity;
import com.techco.igotrip.ui.favorite.FavoriteActivity;
import com.techco.igotrip.ui.info.InfoActivity;
import com.techco.igotrip.ui.launch.LaunchActivity;
import com.techco.igotrip.ui.login.LoginActivity;
import com.techco.igotrip.ui.main.MainActivity;
import com.techco.igotrip.ui.menu.MenuActivity;
import com.techco.igotrip.ui.mytrip.MyTripActivity;
import com.techco.igotrip.ui.post.PostActivity;
import com.techco.igotrip.ui.provincedetail.ProvinceDetailActivity;
import com.techco.igotrip.ui.showmap.ShowMapActivity;
import com.techco.igotrip.ui.signup.SignUpActivity;
import com.techco.igotrip.ui.viewarticles.ViewArticlesActivity;
import com.techco.igotrip.ui.writecomment.WriteCommentActivity;
import com.techco.igotrip.ui.youarehere.YouAreHereActivity;

import dagger.Component;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(RateUsDialog dialog);

    void inject(LaunchActivity activity);
    void inject(MenuActivity activity);
    void inject(LoginActivity activity);
    void inject(SignUpActivity activity);
    void inject(MainActivity activity);
    void inject(ProvinceDetailActivity activity);
    void inject(InfoActivity activity);
    void inject(ChangePasswordActivity activity);
    void inject(CommentActivity activity);
    void inject(WriteCommentActivity activity);
    void inject(CreateTripActivity activity);
    void inject(DetailActivity activity);
    void inject(MyTripActivity activity);
    void inject(FavoriteActivity activity);
    void inject(ExperienceActivity activity);
    void inject(ShowMapActivity activity);
    void inject(YouAreHereActivity activity);
    void inject(ViewArticlesActivity activity);
    void inject(PostActivity activity);
    void inject(DirectionActivity activity);

}
