package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewAccountActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button button_select_account;
    String account_type, getEmail;
    public static final String ACCOUNT_TYPE = "com.example.bankapplication.ACCOUNT_TYPE";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        List<String> nameList = new ArrayList<>();
        nameList.add("regularAccount");
        nameList.add("creditAccount");
        nameList.add("savingsAccount");

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);


        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = findViewById(R.id.spinner);
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

        button_select_account = (Button) findViewById(R.id.button_select_account);
        button_select_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateAccountsActivity(account_type);
            }
        });

    }

    private void startCreateAccountsActivity(String account_type) {
        Intent intent = new Intent(CreateNewAccountActivity.this, CreateNewAccountActivity2.class);
        intent.putExtra(ACCOUNT_TYPE, account_type);
        startActivity(intent);
    }


}
