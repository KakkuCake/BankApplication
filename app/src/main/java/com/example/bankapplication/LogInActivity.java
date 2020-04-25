package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private EditText email, password;
    private Button button_login;
    private TextView link_regist;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://192.168.1.162/android_register_login/login.php";
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

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPassword.isEmpty()) {
                    Login(mEmail, mPassword);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
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
                        Toast.makeText(LogInActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    button_login.setVisibility(View.VISIBLE);
                    Toast.makeText(LogInActivity.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        button_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LogInActivity.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
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

}
