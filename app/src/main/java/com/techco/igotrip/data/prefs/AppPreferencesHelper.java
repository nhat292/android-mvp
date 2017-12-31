
package com.techco.igotrip.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.techco.igotrip.dagger.ApplicationContext;
import com.techco.igotrip.dagger.PreferenceInfo;
import com.techco.igotrip.data.network.model.object.User;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Nhat on 12/13/17.
 */


@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_INFO = "PREF_KEY_USER_INFO";
    private static final String PREF_KEY_USERNAME = "PREF_KEY_USERNAME";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public void setUserInfo(User user) {
        if (user != null) {
            mPrefs.edit().putString(PREF_KEY_USER_INFO, new Gson().toJson(user)).apply();
        } else {
            mPrefs.edit().remove(PREF_KEY_USER_INFO).apply();
        }
    }

    @Override
    public User getUserInfo() {
        String userJson = mPrefs.getString(PREF_KEY_USER_INFO, null);
        if (userJson != null) {
            return new Gson().fromJson(userJson, User.class);
        }
        return null;
    }

    @Override
    public void setUsername(String username) {
        mPrefs.edit().putString(PREF_KEY_USERNAME, username).apply();
    }

    @Override
    public String getUsername() {
        return mPrefs.getString(PREF_KEY_USERNAME, "");
    }
}
