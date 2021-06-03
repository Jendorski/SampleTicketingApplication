package com.testapplication.preferenceHelper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static final String PREF_NAME = "pref_name";

    private static final String THE_JWT_TOKEN = "theJWToken";

    private static final String THE_LOGIN_EMAIL = "theLoginEmail";

    private static final String THE_LOGIN_PASSWORD = "theLoginPassword";

    private static final String IS_LOGGED_IN = "is_logged_in";

    private static final String IS_MAIN_CURRENCY = "is_main_currency";

    private static SharedPreferences app_prefs;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        //this.context = context;
    }

    public void putLoginPassword(String password) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(THE_LOGIN_PASSWORD, password);
        edit.apply();
    }

    public String getLoginPassword(){
        return app_prefs.getString(THE_LOGIN_PASSWORD, "");
    }

    public void putLoginEmail(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(THE_LOGIN_EMAIL, email);
        edit.apply();
    }

    public String getLoginEmail(){
        return app_prefs.getString(THE_LOGIN_EMAIL, "");
    }

    public void putJWTToken(String theJWToken) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(THE_JWT_TOKEN, theJWToken);
        edit.apply();
    }

    public static String getTheJwtToken(){
        return app_prefs.getString(THE_JWT_TOKEN, "");
    }

    public void putIsLoggedIn(boolean b) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(IS_LOGGED_IN, b);
        edit.apply();
    }

    public boolean getIsLoggedIn(){
        return app_prefs.getBoolean(IS_LOGGED_IN, false);
    }

    public void clearData() {
        SharedPreferences.Editor editor = app_prefs.edit();
        editor.clear().commit();
    }


}
