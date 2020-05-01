package com.example.bankapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class BankActionsFragment extends Fragment {

    private Spinner spinner;
    private static final String ARG_EMAIL = "argEmail";
    public static final String ACCOUNT_NUMBER = "com.example.bankapplication.ACCOUNT_NUMBER";
    String mEmail, account;
    private Button button_transaction;
    ArrayList<String> arr;

    public static BankActionsFragment newInstance(String email) {
        BankActionsFragment fragment = new BankActionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    Bank bank = Bank.getInstance();  //Käytetään singleton-periaatetta, koska pankkioliota tarvitaan useammassa luokassa/näkymässä.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bank_actions, container, false);

        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_EMAIL);
        }

        arr = bank.arraylistOfAccounts(mEmail);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = view.findViewById(R.id.spinner2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                account = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button_transaction = (Button) view.findViewById(R.id.button_transaction);
        button_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(account);
            }
        });

        return view;
    }

    private void startActivity(String account) {
        Intent intent = new Intent(getActivity(), BankTransactionsActivity.class);
        intent.putExtra(ACCOUNT_NUMBER, account);
        startActivity(intent);
    }



}