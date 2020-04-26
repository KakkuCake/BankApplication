package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountsActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button button_create_new_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_accounts);

        spinner = findViewById(R.id.spinner);

        List<String> nameList = new ArrayList<>();
        nameList.add("RegularAccount");
        nameList.add("CreditAccount");
        nameList.add("SavingsAccount");


        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_create_new_account = findViewById(R.id.button_create_new_account);
        button_create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSelectedAccount(v);
            }
        });

        Toast.makeText(this, "Choose the type of account you want to create and press the confirm button", Toast.LENGTH_LONG).show();


    }

    public void getSelectedAccount(View v){
        String name = (String) spinner.getSelectedItem();
    }

}
