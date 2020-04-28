package com.example.bankapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    Context context;
    public Bank(Context context){  //Tämän rakentajan avulla voidaan tehdä muutoksia eri näkymissä.
        this.context=context;
    }
    private static String URL_CREATE_REGULAR_ACCOUNT = "http://192.168.1.162/android_register_login/create_regular_account.php";
    private static String URL_CREATE_CREDIT_ACCOUNT = "http://192.168.1.162/android_register_login/create_credit_account.php";
    private static String URL_CREATE_SAVINGS_ACCOUNT = "http://192.168.1.162/android_register_login/create_savings_account.php";


    private ArrayList<Account> aList = new ArrayList();

    public void addRegularAccount(View v, final String email, final String account_number, final String balance) {

        final Button button_create_new_account = (Button) ((Activity)context).findViewById(R.id.button_create_new_account);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_create_new_account.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_REGULAR_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startActivity(context);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ConnectionError", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void addCreditAccount(View v, final String email, final String account_number, final String balance, final String credit) {

        final Button button_create_new_account = (Button) ((Activity)context).findViewById(R.id.button_create_new_account);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_create_new_account.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_CREDIT_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startActivity(context);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ConnectionError", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("account_number", account_number);
                params.put("balance", balance);
                params.put("credit", credit);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void addSavingsAccount(View v, final String email, final String account_number, final String balance) {

        final Button button_create_new_account = (Button) ((Activity)context).findViewById(R.id.button_create_new_account);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_create_new_account.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_SAVINGS_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startActivity(context);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ConnectionError", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



    public void depositMoney(String acNumber, int amount) {
        //System.out.println("Depositing to the account: " + acNumber + " the amount " + amount);
        for (Account account : aList)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(amount);
            }
        }
    }

    public void withdrawMoney(String acNumber, int amount) {
        //System.out.println("Withdrawing to the account: " + acNumber + " the amount " + amount);
        for (Account account : aList)  {
            if (account.getAcNumber().equals(acNumber)) {
                account.setBalance(-amount);
            }
        }
    }

    public void removeAccount(String acNumber) {
        //System.out.println("Account removed");
        int i = 0;
        for (Account account : aList)  {
            if (account.getAcNumber().equals(acNumber)) {
                aList.remove(i);
                System.out.println("Account removed.");
                break;
            }
            i++;
        }
    }

    public void printAccountInformation(String acNumber, int amount) {
        //System.out.println("Searching for account: " + acNumber);
        for (Account account: aList) {
            if (account.getAcNumber().equals(acNumber)) {
                System.out.println("Account number: " + acNumber + " Amount of money: " + account.getBalance());
            }
        }
    }

    public void printAllAccounts() {
        System.out.println("All accounts:");
        for (Account account: aList) {
            System.out.print("Account number: " + account.getAcNumber() +
                    " Amount of money: " + account.getBalance());
            if (account instanceof CreditAccount) {
                System.out.println(" Credit limit: " + ((CreditAccount) account).getCreditLimit());
            }
            System.out.println();
        }
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }


}
