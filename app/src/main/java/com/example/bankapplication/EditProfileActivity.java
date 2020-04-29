package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private TextInputLayout first_name, last_name, password, c_password;
    private Button button_save;
    SessionManager sessionManager;
    String getId;
    String getEmail;
    Validation validator = new Validation(this);
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        getEmail = user.get(sessionManager.EMAIL);

        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first_nameInput = first_name.getEditText().getText().toString().trim();
                String last_nameInput = last_name.getEditText().getText().toString().trim();
                String passwordInput = password.getEditText().getText().toString().trim();
                String c_passwordInput = c_password.getEditText().getText().toString().trim();

                if (!validator.validateFirstName(first_nameInput) | !validator.validateLastName(last_nameInput)
                        | !validator.checkThatPasswordsMatch(passwordInput, c_passwordInput) |  !validator.validatePassword(passwordInput) |  !validator.validateConfirmedPassword(c_passwordInput)) {
                    return;
                }

                final String email = getEmail;
                final String first_name = first_nameInput;
                final String last_name = last_nameInput;
                final String password = passwordInput;
                final String id = getId;

                database.editProfile(v, email, first_name, last_name, password, id);
            }
        });

    }

}
