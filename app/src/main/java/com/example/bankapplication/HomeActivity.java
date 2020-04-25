
package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {


    String mName, mEmail;
    private TextView name, email;
    private Button button_logout;
    private Button button_edit_profile;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        button_logout = (Button) findViewById(R.id.button_logout);
        button_edit_profile = (Button) findViewById(R.id.button_edit_profile);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.FIRST_NAME);
        String mEmail = user.get(sessionManager.EMAIL);


        name.setText(mName);
        email.setText(mEmail);

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });

        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityEditProfile();
            }
        });
    }

    // This method gets the user details
    private void startActivityEditProfile() {
        Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }

}