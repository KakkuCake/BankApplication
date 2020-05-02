package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                if (account_type.equals("regularAccount")) {
                    startCreateRegularAccountActivity();
                } else if (account_type.equals("creditAccount")) {
                    startCreateCreditAccountActivity();
                } else if (account_type.equals("savingsAccount")) {
                    System.out.println("S");
                }
            }
        });

    }

    private void startCreateRegularAccountActivity() {
        Intent intent = new Intent(CreateNewAccountActivity.this, CreateNewRegularAccountActivity.class);
        startActivity(intent);
    }

    private void startCreateCreditAccountActivity() {
        Intent intent = new Intent(CreateNewAccountActivity.this, CreateNewCreditAccountActivity.class);
        startActivity(intent);
    }


}
