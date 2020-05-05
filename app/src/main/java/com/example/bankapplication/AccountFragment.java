package com.example.bankapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountFragment extends Fragment {

    private Button button_edit_profile, button_create_accounts, button_create_bank_card, button_card_transactions;
    String mEmail;
    ArrayList nameList;
    HelperClass helper = new HelperClass();
    SessionManager sessionManager;

    Bank bank = Bank.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        mEmail = user.get(sessionManager.EMAIL);


        button_edit_profile = (Button) view.findViewById(R.id.button_edit_profile);
        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditProfileActivity();
            }
        });

        button_create_accounts = (Button) view.findViewById(R.id.button_create_accounts);
        button_create_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateNewAccountActivity(mEmail);

            }
        });

        button_create_bank_card = (Button) view.findViewById(R.id.button_create_bank_card);
        button_create_bank_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateCardActivity();
            }
        });

        button_card_transactions = (Button) view.findViewById(R.id.button_card_transactions);
        button_card_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTransactionsActivity();
            }
        });

        return view;
    }

    private void startEditProfileActivity() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void startCreateNewAccountActivity(String email) {
        nameList = helper.populateSpinner(email);
        if (nameList.isEmpty())  {  // Käyttäjän ei pitäisi päätyä tähän if-looppiin missään tilanteessa, mutta varmistetaan silti.
            Toast.makeText(getActivity(), "You already have 3 accounts!", Toast.LENGTH_SHORT).show();
        } else {
            nameList.clear();
            Intent intent = new Intent(getActivity(), CreateNewAccountActivity.class);
            startActivity(intent);
        }
    }

    private void startCreateCardActivity() {
        BankCard bankCard = bank.returnCard(mEmail);
        if (bankCard == null) {
            Intent intent = new Intent(getActivity(), CreateCardActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "You already have bankcard!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTransactionsActivity() {
        Intent intent = new Intent(getActivity(), CardTransactionsActivity.class);
        startActivity(intent);
    }


}