package com.example.bankapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {  //This class has information about the current session and knows who is using the app. This class uses the sharedPrefrences interface.

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

    public void createSession(String first_name, String email, String id) {  /* This method creates the session. When session is created SessionManager class knows first name, email and id of the account
    that is using the app */
        editor.putBoolean(LOGIN, true);
        editor.putString(FIRST_NAME, first_name);
        editor.putString(EMAIL, email);
        editor.putString(ID, id);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {  //T his method is here only for safety. If account somehow gets pass the LogInActivity, will he be send back to LogInActivity if he does not have account data written in database.
        if (!this.isLogin()) {
            Intent intent = new Intent(context, LogInActivity.class);
            context.startActivity(intent);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){  // With this method we can get the first name email and id of the account using the app.
        HashMap<String, String> user = new HashMap<>();
        user.put(FIRST_NAME, sharedPreferences.getString(FIRST_NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    public void logout() { //This method handles the logout operation.
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LogInActivity.class);
        context.startActivity(intent);
        ((HomeActivity)context).finish();
    }

}
