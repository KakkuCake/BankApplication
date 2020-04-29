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

public class AccountFragment extends Fragment {

    private Button button_edit_profile, button_create_accounts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


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
                startCreateNewAccountActivity();

            }
        });

        return view;
    }
    private void startEditProfileActivity() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void startCreateNewAccountActivity() {
        Intent intent = new Intent(getActivity(), CreateNewAccountActivity.class);
        startActivity(intent);
    }



}