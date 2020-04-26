package com.example.bankapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String ARG_NAME = "argText";
    private static final String ARG_EMAIL = "argEmail";
    String mName, mEmail;
    private TextView name, email;
    private Button button_logout;
    private Button button_edit_profile;
    SessionManager sessionManager;

    public static HomeFragment newInstance(String name, String email) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);

        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
            mEmail = getArguments().getString(ARG_EMAIL);
        }


        name.setText(mName);
        email.setText(mEmail);

        button_edit_profile = (Button) view.findViewById(R.id.button_edit_profile);
        button_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityEditProfile();
            }
        });

        return view;
    }

    // This method gets the user details
    private void startActivityEditProfile() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

}