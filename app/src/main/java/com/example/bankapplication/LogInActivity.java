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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button button_login;
    private TextView link_regist;
    private TextView changeLang;
    private ProgressBar loading;

    //KALLE
    //private static String URL_LOGIN = "http://192.168.1.162/android_register_login/login.php";
    //JOONA
    private static String URL_LOGIN = "http://192.168.1.4/android_register_login/login.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sessionManager = new SessionManager(this);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button_login = findViewById(R.id.button_login);
        link_regist = findViewById(R.id.link_regist);
        changeLang = findViewById(R.id.changeLang);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPassword.isEmpty()) {
                    Login(mEmail, mPassword);
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

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });

    }

    private void Login(final String email, final String password) {

        loading.setVisibility(View.VISIBLE);
        button_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")) {

                        for  (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String first_name = object.getString("first_name").trim();
                            String email = object.getString("email").trim();
                            String id = object.getString("id").trim();

                            sessionManager.createSession(first_name, email, id);

                            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                            intent.putExtra("first_name", first_name);
                            intent.putExtra("email", email);
                            startActivity(intent);


                            loading.setVisibility(View.GONE);
                        }

                    } else {
                        loading.setVisibility(View.GONE);
                        button_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LogInActivity.this, getString(R.string.logInFail), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    button_login.setVisibility(View.VISIBLE);
                    Toast.makeText(LogInActivity.this, getString(R.string.logInFail), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        button_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LogInActivity.this, getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
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
