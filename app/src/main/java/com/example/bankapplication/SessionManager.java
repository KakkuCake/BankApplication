package com.example.bankapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String ID = "ID";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String first_name, String email, String id) {
        editor.putBoolean(LOGIN, true);
        editor.putString(FIRST_NAME, first_name);
        editor.putString(EMAIL, email);
        editor.putString(ID, id);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent intent = new Intent(context, LogInActivity.class);
            context.startActivity(intent);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(FIRST_NAME, sharedPreferences.getString(FIRST_NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LogInActivity.class);
        context.startActivity(intent);
        ((HomeActivity)context).finish();
    }

}
