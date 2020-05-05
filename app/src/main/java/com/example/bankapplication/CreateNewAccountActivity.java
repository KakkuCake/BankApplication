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

import java.util.ArrayList;
import java.util.HashMap;

public class CreateNewAccountActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button button_select_account;
    String account_type, mEmail;
    public static final String ACCOUNT_TYPE = "com.example.bankapplication.ACCOUNT_TYPE";
    ArrayList nameList;
    SessionManager sessionManager;

    HelperClass helper = new HelperClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        mEmail = user.get(sessionManager.EMAIL);

        button_select_account = (Button) findViewById(R.id.button_select_account);
        spinner = findViewById(R.id.spinner);

        nameList = helper.populateSpinner(mEmail);

        if (nameList.isEmpty())  {  // Käyttäjän ei pitäisi päätyä tähän if-looppiin missään tilanteessa, mutta varmistetaan silti.
            button_select_account.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            Toast.makeText(this, "You already have 3 accounts!", Toast.LENGTH_SHORT).show();
        } else {
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

            button_select_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (account_type.equals("regularAccount")) {
                        nameList.clear(); // Tyhjennetään varmuudeksi apuluokasta saatu arraylist.
                        startCreateRegularAccountActivity();
                    } else if (account_type.equals("creditAccount")) {
                        nameList.clear();
                        startCreateCreditAccountActivity();
                    } else if (account_type.equals("savingsAccount")) {
                        nameList.clear();
                        startCreateSavingsAccountActivity();
                    }
                }
            });

        }

    }

    private void startCreateRegularAccountActivity() {
        Intent intent = new Intent(CreateNewAccountActivity.this, CreateNewRegularAccountActivity.class);
        startActivity(intent);
    }

    private void startCreateCreditAccountActivity() {
        Intent intent = new Intent(CreateNewAccountActivity.this, CreateNewCreditAccountActivity.class);
        startActivity(intent);
    }

    private void startCreateSavingsAccountActivity() {
        Intent intent = new Intent(CreateNewAccountActivity.this, CreateNewSavingsAccountActivity.class);
        startActivity(intent);
    }

}
