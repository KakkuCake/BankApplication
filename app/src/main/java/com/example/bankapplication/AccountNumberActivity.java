package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AccountNumberActivity extends AppCompatActivity {

    private Button button_account_number;
    private TextInputLayout account_number_layout;
    String my_account_number, account_numberInput;
    Validation validator = new Validation(this);
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_number);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        my_account_number = intent.getStringExtra(BankActionsFragment.ACCOUNT_NUMBER);

        account_number_layout = findViewById(R.id.account_number);

        button_account_number = findViewById(R.id.button_account_number);
        button_account_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_numberInput = account_number_layout.getEditText().getText().toString().trim();

                if (!validator.validateAccountNumberTransfer(account_numberInput)) {
                    return;
                }

                database.getAccountBalance(v, account_numberInput, my_account_number);
            }
        });

        Toast.makeText(this, "Choose the type of account you want to create and press the confirm button", Toast.LENGTH_LONG).show();


    }

}
