package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class TransferActivity extends AppCompatActivity {

    private Button button_transfer;
    private TextInputLayout balance;
    String my_account_number, account_number_payee;

    Validation validator = new Validation(this);
    Database database = new Database(this);
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        button_transfer = findViewById(R.id.button_transfer);
        balance = findViewById(R.id.balance);

        Intent intent = getIntent(); //Let's get the account number which user chose in BankActionsFragment.
        my_account_number = intent.getStringExtra(Database.MY_ACCOUNT_NUMBER);
        account_number_payee = intent.getStringExtra(Database.ACCOUNT_NUMBER_PAYEE);
        System.out.println("My account_number is  " + my_account_number + " and account_number_payee is  " + account_number_payee);

        button_transfer = findViewById(R.id.button_transfer);
        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String balanceInput = balance.getEditText().getText().toString().trim(); ;

                if (!validator.validateBalance(balanceInput)) {
                    return;
                }

                float amount = Float.parseFloat(balanceInput);
                float payees_balance = Float.parseFloat(getBalance());//This is the balance of the account the user put in the previous activity.

                if (amount > payees_balance) {
                    balance.setError("You have not enough balance!");
                } else {
                    bank.withdrawMoney(my_account_number, amount);

                    Account myAccount = bank.returnAccount(my_account_number);
                    float my_balance = myAccount.getBalance();//Let's get user's new balance from bank so we can count the new balance.
                    String my_new_balance = String.valueOf(my_balance);
                    System.out.println("my balance after withdraw is " + my_new_balance);
                    database.withdrawMoney(v, my_account_number, my_new_balance);

                    System.out.println("The balance of account " + account_number_payee + " is + " + payees_balance);
                    float ac_new_balance = amount + payees_balance;
                    System.out.println("The balance of account " + account_number_payee + " after transfer is " + ac_new_balance);
                    String new_balance_string = String.valueOf(ac_new_balance);
                    System.out.println("The balance of account " + account_number_payee + " after transfer is as a string " + new_balance_string);


                    char first_letter = account_number_payee.charAt(0);
                    String account_mark = "" + first_letter;  //Let's get the account mark (which is either R, C, or S) to know which account user is using.

                    if (account_mark.equals("R")) {
                        database.transferMoneyRegularAccount(v, account_number_payee, new_balance_string);
                    } else if (account_mark.equals("C")) {
                        database.transferMoneyCreditAccount(v, account_number_payee, new_balance_string);
                    } else {
                        System.out.println("");
                    }
                }

            }
        });

        Toast.makeText(this, "Choose the type of account you want to create and press the confirm button", Toast.LENGTH_LONG).show();
    }

    protected String getBalance() {
        HelperClass helper = HelperClass.getInstance();
        String ac_balance = helper.getBalance();
        helper.clearList();
        return ac_balance;
    }
}
