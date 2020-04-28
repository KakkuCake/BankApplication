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

public class CreateNewAccountActivity2 extends AppCompatActivity {

    private Button button_create_new_account;
    private ProgressBar loading;
    String getEmail, account_type;
    private TextInputLayout account_number, balance, credit;
    private static String URL_CREATE_REGULAR_ACCOUNT = "http://192.168.1.162/android_register_login/create_regular_account.php";
    private static String URL_CREATE_CREDIT_ACCOUNT = "http://192.168.1.162/android_register_login/create_credit_account.php";
    private static String URL_CREATE_SAVINGS_ACCOUNT = "http://192.168.1.162/android_register_login/create_savings_account.php";

    SessionManager sessionManager;
    Validation validator = new Validation(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account2);

        Intent intent = getIntent();
        account_type = intent.getStringExtra(CreateNewAccountActivity.ACCOUNT_TYPE);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);

        loading = findViewById(R.id.loading);

        account_number = findViewById(R.id.account_number);
        balance= findViewById(R.id.balance);
        credit = findViewById(R.id.credit);

        button_create_new_account = findViewById(R.id.button_create_new_account);
        button_create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account_type.equals("regularAccount")) {
                    createRegularAccount(v);
                } else if (account_type.equals("creditAccount")) {
                    createCreditAccount(v);
                } else if (account_type.equals("savingsAccount")) {
                    createSavingsAccount(v);
                }
            }
        });

        Toast.makeText(this, "Choose the type of account you want to create and press the confirm button", Toast.LENGTH_LONG).show();


    }


    private void createRegularAccount(View v) {

        String account_numberInput = account_number.getEditText().getText().toString().trim();
        String balanceInput = balance.getEditText().getText().toString().trim();
        String creditInput = credit.getEditText().getText().toString().trim();

        if (!validator.validateAccountNumber(account_numberInput) | !validator.validateBalance(balanceInput) | !validator.validateCredit(creditInput)) {
            return;
        }

        loading.setVisibility(View.VISIBLE);
        button_create_new_account.setVisibility(View.GONE);

        final String email = getEmail;
        final String account_number = this.account_number.getEditText().getText().toString().trim();
        final String balance = this.balance.getEditText().getText().toString().trim(); //Merkitään myös rahasummat String muodossa, mutta tarkistetaan että ne ovat INT ennen.

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_REGULAR_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateNewAccountActivity2.this, HomeActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.registerFail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void createCreditAccount(View v) {

        String account_numberInput = account_number.getEditText().getText().toString().trim();
        String balanceInput = balance.getEditText().getText().toString().trim();
        String creditInput = credit.getEditText().getText().toString().trim();

        if (!validator.validateAccountNumber(account_numberInput) | !validator.validateBalance(balanceInput) | !validator.validateCredit(creditInput)) {
            return;
        }

        loading.setVisibility(View.VISIBLE);
        button_create_new_account.setVisibility(View.GONE);

        final String email = getEmail;
        final String account_number = this.account_number.getEditText().getText().toString().trim();
        final String balance = this.balance.getEditText().getText().toString().trim(); //Merkitään myös rahasummat String muodossa, mutta tarkistetaan että ne ovat INT ennen.
        final String credit = this.credit.getEditText().getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_CREDIT_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateNewAccountActivity2.this, HomeActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.registerFail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("account_number", account_number);
                params.put("balance", balance);
                params.put("credit", credit);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void createSavingsAccount(View v) {

        String account_numberInput = account_number.getEditText().getText().toString().trim();
        String balanceInput = balance.getEditText().getText().toString().trim();
        String creditInput = credit.getEditText().getText().toString().trim();

        if (!validator.validateAccountNumber(account_numberInput) | !validator.validateBalance(balanceInput) | !validator.validateCredit(creditInput)) {
            return;
        }

        loading.setVisibility(View.VISIBLE);
        button_create_new_account.setVisibility(View.GONE);

        final String email = getEmail;
        final String account_number = this.account_number.getEditText().getText().toString().trim();
        final String balance = this.balance.getEditText().getText().toString().trim(); //Merkitään myös rahasummat String muodossa, mutta tarkistetaan että ne ovat INT ennen.

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_SAVINGS_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateNewAccountActivity2.this, HomeActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.registerFail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateNewAccountActivity2.this, getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
