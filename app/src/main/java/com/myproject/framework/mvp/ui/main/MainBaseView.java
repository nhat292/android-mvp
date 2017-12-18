
package com.myproject.framework.mvp.ui.main;

import com.myproject.framework.mvp.data.db.model.Question;
import com.myproject.framework.mvp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface MainBaseView extends BaseView {

    void openLoginActivity();

    void showAboutFragment();

    void refreshQuestionnaire(List<Question> questionList);

    void reloadQuestionnaire(List<Question> questionList);

    void updateUserName(String currentUserName);

    void updateUserEmail(String currentUserEmail);

    void updateUserProfilePic(String currentUserProfilePicUrl);

    void updateAppVersion();

    void showRateUsDialog();

    void openMyFeedActivity();

    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();
}
