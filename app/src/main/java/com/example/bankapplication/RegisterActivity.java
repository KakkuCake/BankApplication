package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button button_regist;
    private ProgressBar loading;
    private TextInputLayout first_name, last_name, email, password, c_password;
    //KALLE
    String URL_REGIST = "http://192.168.1.162/android_register_login/register.php";
    //JOONA
    //String URL_REGIST = "http://192.168.1.4/android_register_login/register.php";

    Validation validator = new Validation(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loading = findViewById(R.id.loading);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        button_regist = findViewById(R.id.button_regist);

        button_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist(v);
            }
        });
    }



    private void Regist(View v) {

        String emailInput = email.getEditText().getText().toString().trim();
        String first_nameInput = first_name.getEditText().getText().toString().trim();
        String last_nameInput = last_name.getEditText().getText().toString().trim();
        String passwordInput = password.getEditText().getText().toString().trim();
        String c_passwordInput = c_password.getEditText().getText().toString().trim();

        if (!validator.validateEmail(emailInput) | !validator.validateFirstName(first_nameInput) | !validator.validateLastName(last_nameInput)
                | !validator.checkThatPasswordsMatch(passwordInput, c_passwordInput) |  !validator.validatePassword(passwordInput) |  !validator.validateConfirmedPassword(c_passwordInput)) {
            return;
        }

        loading.setVisibility(View.VISIBLE);
        button_regist.setVisibility(View.GONE);

        final String first_name = this.first_name.getEditText().getText().toString().trim();
        final String last_name = this.last_name.getEditText().getText().toString().trim();
        final String email = this.email.getEditText().getText().toString().trim();
        final String password = this.password.getEditText().getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, getString(R.string.registerFail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_regist.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
