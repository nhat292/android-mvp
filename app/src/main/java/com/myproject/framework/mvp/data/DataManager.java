
package com.myproject.framework.mvp.data;


import com.myproject.framework.mvp.data.db.DbHelper;
import com.myproject.framework.mvp.data.network.ApiHelper;
import com.myproject.framework.mvp.data.prefs.PreferencesHelper;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    void updateApiHeader(Long userId, String accessToken);

    void setUserAsLoggedOut();

    Observable<Boolean> seedDatabaseQuestions();

    Observable<Boolean> seedDatabaseOptions();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
