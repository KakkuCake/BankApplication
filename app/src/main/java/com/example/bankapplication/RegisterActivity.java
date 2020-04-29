package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;


public class RegisterActivity extends AppCompatActivity {

    private Button button_regist;
    private TextInputLayout first_name, last_name, email, password, c_password;

    Validation validator = new Validation(this);
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);

        button_regist = findViewById(R.id.button_regist);
        button_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailInput = email.getEditText().getText().toString().trim();
                String first_nameInput = first_name.getEditText().getText().toString().trim();
                String last_nameInput = last_name.getEditText().getText().toString().trim();
                String passwordInput = password.getEditText().getText().toString().trim();
                String c_passwordInput = c_password.getEditText().getText().toString().trim();

                if (!validator.validateEmail(emailInput) | !validator.validateFirstName(first_nameInput) | !validator.validateLastName(last_nameInput)
                        | !validator.checkThatPasswordsMatch(passwordInput, c_passwordInput) |  !validator.validatePassword(passwordInput) |  !validator.validateConfirmedPassword(c_passwordInput)) {
                    return;
                }

                final String email = emailInput;
                final String first_name = first_nameInput;
                final String last_name = last_nameInput;
                final String password = passwordInput;

                database.Regist(v, email, first_name, last_name, password);

            }
        });
    }


}