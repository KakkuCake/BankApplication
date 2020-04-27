package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccountsActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button button_create_new_account;
    private ProgressBar loading;
    String account_type, getEmail;
    private TextInputLayout account_number, balance, credit;
    private static String URL_CREATE_ACCOUNT = "http://192.168.1.162/android_register_login/create_account.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_accounts);

        sessionManager = new SessionManager(this);


        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);

        loading = findViewById(R.id.loading);
        spinner = findViewById(R.id.spinner);

        account_number = findViewById(R.id.account_number);
        balance= findViewById(R.id.balance);
        credit = findViewById(R.id.credit);


        List<String> nameList = new ArrayList<>();
        nameList.add("regularAccount");
        nameList.add("creditAccount");
        nameList.add("savingsAccount");


        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                account_type = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button_create_new_account = findViewById(R.id.button_create_new_account);
        button_create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createANewAccount(v);
            }
        });

        Toast.makeText(this, "Choose the type of account you want to create and press the confirm button", Toast.LENGTH_LONG).show();


    }

    public void getSelectedAccount(View v){
        String name = (String) spinner.getSelectedItem();
    }

    private void createANewAccount(View v) {

        loading.setVisibility(View.VISIBLE);
        button_create_new_account.setVisibility(View.GONE);

        String account_numberInput = account_number.getEditText().getText().toString().trim();
        String balanceInput = balance.getEditText().getText().toString().trim();
        String creditInput = credit.getEditText().getText().toString().trim();

        if (!account_numberInput.matches("[0-9]+")) {  //LISÄÄ NÄMÄ VALIDATION LUOKKAAN + PHP EI TOIMI BANK1 TAULUSSA.
            System.out.println("Invalid number");
        }

        if (!balanceInput.matches("[0-9]+")) {
            System.out.println("Invalid number");
        }

        if (!creditInput.matches("[0-9]+")) {
            System.out.println("Invalid number");
        }


        final String email = getEmail;
        final String account_number = this.account_number.getEditText().getText().toString().trim();
        final String account_type = (String) spinner.getSelectedItem();
        final String balance = this.balance.getEditText().getText().toString().trim(); //Merkitään myös rahasummat String muodossa, mutta tarkistetaan että ne ovat INT ennen.
        final String credit = this.credit.getEditText().getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(CreateAccountsActivity.this, getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateAccountsActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateAccountsActivity.this, getString(R.string.registerFail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAccountsActivity.this, getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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
                params.put("account_type", account_type);
                params.put("balance", balance);
                params.put("credit", credit);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
