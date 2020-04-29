package com.example.bankapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BankActionsFragment extends Fragment {

    private Button button_transaction;

    Bank bank = Bank.getInstance();  //Käytetään singleton-periaatetta, koska pankkioliota tarvitaan useammassa luokassa/näkymässä.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank_actions, container, false);

        button_transaction = (Button) view.findViewById(R.id.button_transaction);
        button_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bank.printAllAccounts();
            }
        });

        return view;
    }

    private void startCreateNewAccountActivity() {
        Intent intent = new Intent(getActivity(), CreateNewAccountActivity.class);
        startActivity(intent);
    }

}