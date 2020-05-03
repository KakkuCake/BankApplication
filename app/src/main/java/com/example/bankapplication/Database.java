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
    private static String URL_ADD_MONEY_R = "http://192.168.1.162/android_register_login/add_money_regular_account.php";
    private static String URL_ADD_MONEY_C = "http://192.168.1.162/android_register_login/add_money_credit_account.php";
    private static String URL_ADD_MONEY_S = "http://192.168.1.162/android_register_login/add_money_savings_account.php";
    private static String URL_CHANGE_CREDIT_LIMIT = "http://192.168.1.162/android_register_login/change_credit_limit.php";
    private static String URL_WITHDRAW_MONEY = "http://192.168.1.162/android_register_login/withdraw_money.php";
    private static String URL_TRANSFER_MONEY = "http://192.168.1.162/android_register_login/transfer_money.php";
    private static String URL_GET_ACCOUNT_BALANCE = "http://192.168.1.162/android_register_login/get_account_balance.php";

    Context context;

    public Database(Context context) {  //Tämän rakentajan avulla voidaan tehdä muutoksia eri näkymissä.
        this.context=context;
    }

    Bank bank = Bank.getInstance();  //Käytetään singleton-periaatetta, jotta käytetään aina samaa pankkioliota.

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
                        float balance_float = Float.parseFloat(balance);  //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        bank.addRegularAccount(email, account_number, balance_float);
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, "The account number has been already taken", Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    } else if (success.equals("-2")) {
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
                        float balance_float = Float.parseFloat(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance flaot -muotoon.
                        float credit_float = Float.parseFloat(credit); //Kun luodaan uusi regularAccount olio, muunnetaan credit float -muotoon.
                        bank.addCreditAccount(email, account_number, balance_float, credit_float);
                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, "The account number has been already taken", Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    } else if (success.equals("-2")) {
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

                            float balance_float = Float.parseFloat(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                            bank.addRegularAccount(email, account_number, balance_float);
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

                            float balance_float = Float.parseFloat(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                            float credit_float = Float.parseFloat(credit); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.

                            bank.addCreditAccount(email, account_number, balance_float, credit_float);
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

    protected void addMoneyRegularAccount(View v, final String account_number, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_R, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText( context, "Deposit was successful", Toast.LENGTH_SHORT).show();
                                startHomeActivity(context);
                            } else {
                                Toast.makeText( context, "Deposit failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText( context, "Connection error: " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, "Connection error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void addMoneyCreditAccount(View v, final String account_number, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_C, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText( context, "Deposit was successful", Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText( context, "Deposit failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( context, "Connection error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, "Connection error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void changeCreditLimit(View v, final String account_number, final String credit) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHANGE_CREDIT_LIMIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(context, "Credit limit change was successful!", Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText( context, "Credit limit change failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( context, "Connection error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, "Connection error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("account_number", account_number);
                params.put("credit", credit);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void withdrawMoney(View v, final String account_number, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_WITHDRAW_MONEY , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("0")) {
                        Toast.makeText( context, "Withdraw failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( context, "Connection11 error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, "Connection22 error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    protected void transferMoney(View v, final String account_number, final String balance) {

        final Button button_transfer = (Button) ((Activity)context).findViewById(R.id.button_transfer);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_transfer.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TRANSFER_MONEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {   //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        Toast.makeText(context, "Transfer was successful!", Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, "We couldn't find an account with that account_number", Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        button_transfer.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, "Transfer failed", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_transfer.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_transfer.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_transfer.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("account_number", account_number);
                params.put("balance", balance);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    protected void getAccountBalance(final String account_number) {

        final Button button_account_number = (Button) ((Activity)context).findViewById(R.id.button_account_number);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_account_number.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_ACCOUNT_BALANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("account_found");

                    if (success.equals("1")) {

                        for  (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            String balance = object.getString("balance").trim();
                            HelperClass helper = HelperClass.getInstance();
                            helper.saveBalance(balance);
                        }

                    } else  {
                        Toast.makeText(context, "No account found with that account number", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_account_number.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Connection111 error occurred", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_account_number.setVisibility(View.VISIBLE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Connection222 Error", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_account_number.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("account_number", account_number);
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
