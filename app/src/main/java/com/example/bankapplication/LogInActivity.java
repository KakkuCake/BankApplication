package com.example.bankapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;

public class LogInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button button_login;
    private TextView link_regist;
    private TextView changeLanguage;

    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button_login = findViewById(R.id.button_login);
        link_regist = findViewById(R.id.link_regist);
        changeLanguage = findViewById(R.id.changeLang);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPassword.isEmpty()) {
                    database.Login(v, mEmail, mPassword);
                } else {
                    email.setError(getString(R.string.insertEmail));
                    password.setError(getString(R.string.insertPass));
                }
            }
        });

        link_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });

    }


    //NÄISTÄ OMA LUOKKA

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void changeLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(locale));
        resources.updateConfiguration(config, dm);
    }


    public void changeLanguage(){
        final String[] lista = {"In English", "Suomeksi"};
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(LogInActivity.this);
        //aBuilder.setTitle("choose language");
        aBuilder.setSingleChoiceItems(lista, -1, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 1){
                    changeLocale("fi");
                    finish();
                    startActivity(getIntent());
                }else if(which == 0){
                    changeLocale("en");
                    finish();
                    startActivity(getIntent());
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = aBuilder.create();
        dialog.show();

    }


}
