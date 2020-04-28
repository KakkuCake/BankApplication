package com.example.bankapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Validation {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    Context context;

    public Validation(Context context){  //Tämän rakentajan avulla voidaan tehdä muutoksia eri näkymissä.
            this.context=context;
        }


    protected boolean validateEmail(String emailInput) {

        TextInputLayout email = (TextInputLayout) ((Activity)context).findViewById(R.id.email);

        if (emailInput.isEmpty()) {
            email.setError(context.getResources().getString(R.string.fieldError));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError(context.getResources().getString(R.string.invalidEmail));
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    protected boolean validateFirstName(String usernameInput) {

        TextInputLayout first_name = (TextInputLayout) ((Activity)context).findViewById(R.id.first_name);

        if (usernameInput.isEmpty()) {
            first_name.setError(context.getResources().getString(R.string.fieldError));
            return false;
        } else if (usernameInput.length() > 17) {
            first_name.setError(context.getResources().getString(R.string.invalidName));
            return false;
        } else {
            first_name.setError(null);
            return true;
        }
    }

    protected boolean validateLastName(String usernameInput) {

        TextInputLayout last_name = (TextInputLayout) ((Activity)context).findViewById(R.id.last_name);

        if (usernameInput.isEmpty()) {
            last_name.setError(context.getResources().getString(R.string.fieldError));
            return false;
        } else if (usernameInput.length() > 25) {
            last_name.setError(context.getResources().getString(R.string.invalidName));
            return false;
        } else {
            last_name.setError(null);
            return true;
        }
    }

    protected boolean validatePassword(String passwordInput) {

        TextInputLayout password = (TextInputLayout) ((Activity)context).findViewById(R.id.password);

        if (passwordInput.isEmpty()) {
            password.setError(context.getResources().getString(R.string.fieldError));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError(context.getResources().getString(R.string.invalidPass));
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    protected boolean validateConfirmedPassword(String c_passwordInput) {

        TextInputLayout c_password = (TextInputLayout) ((Activity)context).findViewById(R.id.c_password);

        if (c_passwordInput.isEmpty()) {
            c_password.setError(context.getResources().getString(R.string.fieldError));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(c_passwordInput).matches()) {
            c_password.setError(context.getResources().getString(R.string.invalidPass));
            return false;
        } else {
            c_password.setError(null);
            return true;
        }
    }

    protected boolean checkThatPasswordsMatch(String passwordInput, String c_passwordInput) {

        if (passwordInput.equals(c_passwordInput)) {
            return true;
        } else {
            Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    protected boolean validateAccountNumber(String account_numberInput) {

        TextInputLayout account_number = (TextInputLayout) ((Activity)context).findViewById(R.id.account_number);

        try
        {
            float f = Float.valueOf(account_numberInput.trim()).floatValue();
            account_number.setError(null);
            return true;
        }
        catch (NumberFormatException nfe)
        {
            account_number.setError("Invalid account_number, please try again!");
            return false;
        }
    }

    protected boolean validateBalance(String balanceInput) {

        TextInputLayout balance = (TextInputLayout) ((Activity)context).findViewById(R.id.balance);

        try
        {
            float f = Float.valueOf(balanceInput.trim()).floatValue();
            balance.setError(null);
            return true;
        }
        catch (NumberFormatException nfe)
        {
            balance.setError("Invalid balance, please try again!");
            return false;
        }
    }



    protected boolean validateCredit(String creditInput) {

        TextInputLayout credit = (TextInputLayout) ((Activity)context).findViewById(R.id.credit);

        try
        {
            float f = Float.valueOf(creditInput.trim()).floatValue();
            credit.setError(null);
            return true;
        }
        catch (NumberFormatException nfe)
        {
            credit.setError("Invalid credit, please try again!");
            return false;
        }
    }

}
