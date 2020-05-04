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

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        mEmail = user.get(sessionManager.EMAIL);

        nameList = populateSpinner();

        if (nameList.isEmpty())  {
            Toast.makeText(this, "You already have 3 accounts!", Toast.LENGTH_SHORT).show();

        } else {
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


    private ArrayList<String> populateSpinner() {

        ArrayList<String> arr = bank.arraylistOfAccounts(mEmail);

        for (String a : arr) {
            System.out.println(a);
        }

        nameList = new ArrayList(); //Täytetään ensiksi lista
        nameList.add("regularAccount");
        nameList.add("creditAccount");
        nameList.add("savingsAccount");

        for (String s : arr) {   //Jos käyttäjällä on jo R-, C- tai S-tili niin poistetaan se pudotusvalikosta. Käyttäjällä saa olla max 1 tili/tilityyppi
            char first_letter = s.charAt(0);
            String account_mark = "" + first_letter;  //Let's get the account mark (which is either R, C, or S) to know which account user is using.
            if (account_mark.equals("R"))
                nameList.remove("regularAccount");
            else if (account_mark.equals("C"))
                nameList.remove("creditAccount");
            else if (account_mark.equals("S"))
                nameList.remove("savingsAccount");
        }

        return nameList;

    }


}
