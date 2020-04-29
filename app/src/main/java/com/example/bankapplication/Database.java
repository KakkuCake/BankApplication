package com.example.bankapplication;

import android.app.Activity;
import android.app.ProgressDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static String URL_REGIST = "http://192.168.1.162/android_register_login/register.php";
    private static String URL_LOGIN = "http://192.168.1.162/android_register_login/login.php";
    private static String URL_EDIT = "http://192.168.1.162/android_register_login/edit_profile.php";
    private static String URL_CREATE_REGULAR_ACCOUNT = "http://192.168.1.162/android_register_login/create_regular_account.php";
    private static String URL_CREATE_CREDIT_ACCOUNT = "http://192.168.1.162/android_register_login/create_credit_account.php";
    private static String URL_CREATE_SAVINGS_ACCOUNT = "http://192.168.1.162/android_register_login/create_savings_account.php";
    private static String URL_CHECK_REGULAR_ACCOUNT = "http://192.168.1.162/android_register_login/check_regular_account.php";
    private static String URL_CHECK_CREDIT_ACCOUNT = "http://192.168.1.162/android_register_login/check_credit_account.php";
    private static String URL_CHECK_SAVINGS_ACCOUNT = "http://192.168.1.162/android_register_login/check_savings_account.php";

    Context context;

    public Database(Context context){  //Tämän rakentajan avulla voidaan tehdä muutoksia eri näkymissä.
        this.context=context;
    }

    Bank bank = Bank.getInstance();  //Käytetään singleton-periaatetta, koska pankkioliota tarvitaan useammassa luokassa/näkymässä.

    protected void Regist(View v, final String email, final String first_name, final String last_name, final String password) {

        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);
        final Button button_regist = (Button) ((Activity)context).findViewById(R.id.button_regist);

        loading.setVisibility(View.VISIBLE);
        button_regist.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startLogInActivity(context);

                    } else if(success.equals("-1")) {
                        Toast.makeText(context, "Sähköposti varattu", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_regist.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection error occurred", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void Login(View v, final String email, final String password) {

        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);
        final Button button_login = (Button) ((Activity)context).findViewById(R.id.button_login);

        loading.setVisibility(View.VISIBLE);
        button_login.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")) {

                        for  (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String first_name = object.getString("first_name").trim();
                            String email = object.getString("email").trim();
                            String id = object.getString("id").trim();

                            loading.setVisibility(View.GONE);

                            SessionManager sessionManager = new SessionManager(context);
                            sessionManager.createSession(first_name, email, id);
                            startHomeActivityAfterLogin(context, first_name, email);

                        }

                    } else {
                        loading.setVisibility(View.GONE);
                        button_login.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    button_login.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        button_login.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void editProfile(View v, final String email, final String first_name, final String last_name, final String password, final String id) {  // Tällä metodilla voi muuttaa käyttäjän etunimeä, sukunimeä ja salasanaa.

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText( context, "Success!", Toast.LENGTH_SHORT).show();
                                SessionManager sessionManager = new SessionManager(context);
                                sessionManager.createSession(first_name, email, id);
                                startHomeActivity(context);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText( context, "Error at saving the details: " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText( context, "Error at saving the details: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("password", password);
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void addRegularAccount(View v, final String email, final String account_number, final String balance) {

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
                        int balance_int = Integer.parseInt(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        bank.addRegularAccount(email, account_number, balance_int);
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, "You already have one regularaccount, please read more about our banks policy", Toast.LENGTH_LONG).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
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

    protected void addCreditAccount(View v, final String email, final String account_number, final String balance, final String credit) {

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
                        int balance_int = Integer.parseInt(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        int credit_int = Integer.parseInt(credit); //Kun luodaan uusi regularAccount olio, muunnetaan credit int -muotoon.
                        bank.addCreditAccount(email, account_number, balance_int, credit_int);
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, "You already have one creditaccount, please read more about our banks policy", Toast.LENGTH_LONG).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
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

    protected void addSavingsAccount(View v, final String email, final String account_number, final String balance) {

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
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, "You already have one savingsaccount, please read more about our banks policy", Toast.LENGTH_LONG).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
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

    protected void checkRegularAccountData(final String email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_REGULAR_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("regular_account_found");

                    if (success.equals("1")) {

                        for  (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String account_number = object.getString("account_number").trim();
                            String balance = object.getString("balance").trim();

                            int balance_int = Integer.parseInt(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                            bank.addRegularAccount(email, account_number, balance_int);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Connection1 error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection11 error occurred", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void checkCreditAccountData(final String email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_CREDIT_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("credit_account_found");

                    if (success.equals("1")) {

                        for  (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String account_number = object.getString("account_number").trim();
                            String balance = object.getString("balance").trim();
                            String credit = object.getString("credit").trim();

                            int balance_int = Integer.parseInt(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                            int credit_int = Integer.parseInt(credit); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.

                            System.out.println("credit INT: " + balance_int);
                            System.out.println("credit: " + credit_int);

                            bank.addCreditAccount(email, account_number, balance_int,credit_int);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Connection2 error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection22 error occurred", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void checkSavingsAccountData(final String email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_SAVINGS_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("savings_account_found");

                    if (success.equals("1")) {

                        for  (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String account_number = object.getString("account_number").trim();
                            String balance = object.getString("balance").trim();

                            int balance_int = Integer.parseInt(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                          //  bank.addSavingsAccount(account_number, balance_int);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Connection3 error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection33 error occurred", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public static void startHomeActivity(Context context) { //Tämän metodin avulla voidaa käynnistää kotiaktiviteetti toisesta näkymästä.
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    public static void startLogInActivity(Context context) { //Tämän metodin avulla voidaa käynnistää kotiaktiviteetti toisesta näkymästä.
        context.startActivity(new Intent(context, LogInActivity.class));
    }

    public static void startHomeActivityAfterLogin(Context context, String first_name, String email) { //Tämän metodin avulla voidaa käynnistää kotiaktiviteetti toisesta näkymästä.
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("first_name", first_name);
        intent.putExtra("email", email);
        context.startActivity(intent);
    }


}
