package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
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

public class EditProfileActivity extends AppCompatActivity {

    private TextInputLayout first_name, email, password, c_password;
    private Button button_save;
    SessionManager sessionManager;
    String getId;
    //KALLE
    private static String URL_EDIT = "http://192.168.1.162/android_register_login/edit_detail.php";
    //JOONA 192.168.1.4
    //private static String URL_EDIT = "http://192.168.1.4/android_register_login/edit_detail.php";
    Validation validator = new Validation(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        first_name = findViewById(R.id.first_name);
        email= findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails(v);
            }
        });

    }

    // This method saves the user details
    private void saveDetails(View v) {

        String first_nameInput = first_name.getEditText().getText().toString().trim();
        String emailInput = email.getEditText().getText().toString().trim();
        String passwordInput = password.getEditText().getText().toString().trim();
        String c_passwordInput = c_password.getEditText().getText().toString().trim();

        if (!validator.validateFirstName(first_nameInput) | !validator.validateEmail(emailInput) | !validator.checkThatPasswordsMatch(passwordInput, c_passwordInput)
                | !validator.validatePassword(passwordInput) | !validator.validateConfirmedPassword(c_passwordInput)) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        final String first_name = this.first_name.getEditText().getText().toString().trim();
        final String email = this.email.getEditText().getText().toString().trim();
        final String password = this.password.getEditText().getText().toString().trim();
        final String id = getId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText( EditProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(first_name, email, id);
                                Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText( EditProfileActivity.this, "Error at saving the details: " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText( EditProfileActivity.this, "Error at saving the details: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("email", email);
                params.put("password", password);
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
