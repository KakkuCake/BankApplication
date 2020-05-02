package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class AddMoneyActivity extends AppCompatActivity {

    private Button button_add_money2;
    private TextInputLayout balance;
    String account_number, mEmail;
    SessionManager sessionManager;

    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail(); // Selvitetään kirjautuneen käyttäjän etunimi ja sähköposti.
        mEmail = user.get(sessionManager.EMAIL);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        account_number = intent.getStringExtra(RegularAccountActivity.ACCOUNT_NUMBER);

        balance = findViewById(R.id.balance);

        button_add_money2 = findViewById(R.id.button_add_money2);
        button_add_money2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String deposit = balance.getEditText().getText().toString().trim();
                if (!validator.validateBalance(deposit)) {
                    return;
                }

                float amount = Float.parseFloat(deposit);
                Account myAccount = bank.returnAccount(account_number);
                float accounts_balance = myAccount.getBalance();//Let's get user's new balance from bank so we can count the new balance.
                System.out.println(accounts_balance);
                float new_balance = amount + accounts_balance;
                System.out.println(new_balance);

                bank.depositMoney(account_number, amount);
                String new_balance_string = String.valueOf(new_balance);

                char first_letter = account_number.charAt(0);
                String account_mark = "" + first_letter;  //Let's get the account mark (which is either R, C, or S) to know which account user is using.
                if (account_mark.equals("R")) {
                    database.addMoneyRegularAccount(v, mEmail, account_number, new_balance_string ); //Finally let's save the new balance to database.
                } else if (account_mark.equals("C")) {
                    database.addMoneyCreditAccount(v, mEmail, account_number, new_balance_string ); //Finally let's save the new balance to database.
                } else {
                    System.out.println("");
                }
            }
        });

    }

    private void startHomeActivity() {
        startActivity(new Intent(AddMoneyActivity.this, HomeActivity.class));
    }

}
