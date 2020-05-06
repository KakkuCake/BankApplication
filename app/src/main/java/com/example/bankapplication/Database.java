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
    private static String URL_GET_ACCOUNT_BALANCE = "http://192.168.1.162/android_register_login/get_account_balance.php";
    private static String URL_CREATE_BANK_CARD = "http://192.168.1.162/android_register_login/create_bank_card.php";
    private static String URL_CHECK_BANK_CARD = "http://192.168.1.162/android_register_login/check_bank_card.php";
    private static String URL_SAVE_CARD_BALANCE = "http://192.168.1.162/android_register_login/save_card_balance.php";
    private static String URL_SAVE_CARD_LIMIT = "http://192.168.1.162/android_register_login/save_card_limit.php";

    Context context;


    public Database(Context context) {  //Tämän rakentajan avulla voidaan tehdä muutoksia eri näkymissä.
        this.context=context;
    }
    Bank bank = Bank.getInstance();  //Käytetään singleton-periaatetta, jotta käytetään aina samaa pankkioliota.

    protected static final String ACCOUNT_NUMBER_PAYEE = "com.example.bankapplication.ACCOUNT_NUMBER_PAYEE";  // This is used in getAccountBalance -method to give information to the next activity.
    protected static final String MY_ACCOUNT_NUMBER = "com.example.bankapplication.MY_ACCOUNT_NUMBER";  // This is used in getAccountBalance -method to give information to the next activity.

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
                        Toast.makeText(context, context.getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        startLogInActivity(context);

                    } else if(success.equals("-1")) {
                        Toast.makeText(context, context.getString(R.string.takenEmail), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(context, context.getString(R.string.registerSuccess), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.registerFail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_regist.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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

    }  /* This method takes the view v, first_name, last_name and password values and
     saves them to database if the registration is successful*/

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

                            checkRegularAccountData(email);
                            checkCreditAccountData(email);
                            checkSavingsAccountData(email);
                            checkBankCardData(email);

                            startHomeActivityAfterLogin(context, first_name, email);
                        }

                    } else {
                        loading.setVisibility(View.GONE);
                        button_login.setVisibility(View.VISIBLE);
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    button_login.setVisibility(View.VISIBLE);
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        button_login.setVisibility(View.VISIBLE);
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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

    } /* This method takes view v, email and password values and if they are written in the database,  user will be logged in.
     The method also creates session with session manager class and adds every account and bank card to arraylist if user have them*/

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
                                Toast.makeText(context, context.getString(R.string.changesSaved), Toast.LENGTH_SHORT).show();
                                SessionManager sessionManager = new SessionManager(context);
                                sessionManager.createSession(first_name, email, id);
                                startHomeActivity(context);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText( context, context.getString(R.string.changesFail) + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText( context, context.getString(R.string.changesFail) + error.toString(), Toast.LENGTH_SHORT).show();
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

    } /* This allows user to change first name, last name and
     password and saves them to database*/

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
                        Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, context.getString(R.string.numberTaken), Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    } else if (success.equals("-2")) {
                        Toast.makeText(context, context.getString(R.string.policy), Toast.LENGTH_LONG).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, context.getString(R.string.fail), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.fail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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
    } /* If users input values are valid, this method will save them to database and create
    RegularAccount instance and add it to Bank's arraylist */

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
                        Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, context.getString(R.string.numberTaken), Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    } else if (success.equals("-2")) {
                        Toast.makeText(context, context.getString(R.string.policy3), Toast.LENGTH_LONG).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, context.getString(R.string.fail), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.fail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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
    } /* If users input values are valid, this method will save them to database and create
    CreditAccount instance and add it to Bank's arraylist */

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
                        float balance_float = Float.parseFloat(balance);  //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        bank.addSavingsAccount(email, account_number, balance_float);
                        Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else if (success.equals("-1")) {
                        Toast.makeText(context, context.getString(R.string.numberTaken), Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    } else if (success.equals("-2")) {
                        Toast.makeText(context, context.getString(R.string.policy2), Toast.LENGTH_LONG).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, context.getString(R.string.fail), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_create_new_account.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.fail), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_create_new_account.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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
    } /* If users input values are valid, this method will save them to database and create
    SavingsAccount instance and add it to Bank's arraylist */

    private void checkRegularAccountData(final String email) {

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
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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

    } /* These four private methods are inner methods of Login -method. They takes email as a input value and checks if user have accounts or bankcard
    made with that email. If user has accounts or bankcard, these 4 methods will add them to Bank's arraylist */

    private void checkCreditAccountData(final String email) {

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
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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

    } //2.

    private void checkSavingsAccountData(final String email) {

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

                            float balance_float = Float.parseFloat(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                            bank.addSavingsAccount(email, account_number, balance_float);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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

    } //3.

    private void checkBankCardData(final String email) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_BANK_CARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("card_found");

                    if (success.equals("1")) {

                        for  (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String card_number = object.getString("card_number").trim();
                            String balance = object.getString("balance").trim();
                            String withdraw_limit = object.getString("withdraw_limit").trim();

                            float balance_float = Float.parseFloat(balance); //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                            float withdraw_limit_float = Float.parseFloat(withdraw_limit);
                            bank.addBankCard(email, card_number, balance_float, withdraw_limit_float);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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

    }  //4.

    protected void addMoneyRegularAccount(View v, final String account_number, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_R, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(context, context.getString(R.string.depositSuc), Toast.LENGTH_SHORT).show();
                                startHomeActivity(context);
                            } else {
                                Toast.makeText(context, context.getString(R.string.depositFail), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText( context, context.getString(R.string.connectionError) + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, context.getString(R.string.connectionError) + error.toString(), Toast.LENGTH_SHORT).show();
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

    } /* This method takes account number and balance as values and saves user's regular account's
    new balance to database */

    protected void addMoneyCreditAccount(View v, final String account_number, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_C, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText( context, context.getString(R.string.depositSuc), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText( context, context.getString(R.string.depositFail), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( context, context.getString(R.string.connectionError) + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, context.getString(R.string.connectionError) + error.toString(), Toast.LENGTH_SHORT).show();
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

    } /* This method takes account number and balance as values and saves user's credit account's
    new balance to database */

    protected void addMoneySavingsAccount(View v, final String account_number, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_S, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(context, context.getString(R.string.depositSuc), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, context.getString(R.string.depositFail), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( context, context.getString(R.string.connectionError) + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, context.getString(R.string.connectionError) + error.toString(), Toast.LENGTH_SHORT).show();
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

    } /* This method takes account number and balance as values and saves user's savings account's
    new balance to database */

    protected void changeCreditLimit(View v, final String account_number, final String credit) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHANGE_CREDIT_LIMIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")) {
                        Toast.makeText(context, context.getString(R.string.credChange), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText( context, context.getString(R.string.credChangeFail), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( context, context.getString(R.string.connectionError) + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, context.getString(R.string.connectionError) + error.toString(), Toast.LENGTH_SHORT).show();
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

    } /* This method takes account number and credit as values and saves user's credit account's
    new credit limit to database */

    protected void withdrawMoney(View v, final String account_number, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_WITHDRAW_MONEY , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("0")) {
                        Toast.makeText( context, context.getString(R.string.withDrawfail), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText( context, context.getString(R.string.connectionError) + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context, context.getString(R.string.connectionError) + error.toString(), Toast.LENGTH_SHORT).show();
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

    } /* This method takes account number and balance as values and saves user's
    new balance to database */

    protected void transferMoneyRegularAccount(View v, final String account_number, final String balance) {

        final Button button_transfer = (Button) ((Activity)context).findViewById(R.id.button_transfer);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_transfer.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_R, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {   //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        Toast.makeText(context, context.getString(R.string.transferSuc), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, context.getString(R.string.transferFail), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_transfer.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_transfer.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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
    }  /* This method transfers money from user's account to the regularaccount which account number user gives.
    If account number can't be found from database, user will be informed with Toast */

    protected void transferMoneyCreditAccount(View v, final String account_number, final String balance) {

        final Button button_transfer = (Button) ((Activity)context).findViewById(R.id.button_transfer);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_transfer.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_C, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {   //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        Toast.makeText(context, context.getString(R.string.transferSuc), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, context.getString(R.string.transferFail), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_transfer.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_transfer.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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
    }  /* This method transfers money from user's account to the creditaccount which account number user gives.
    If account number can't be found from database, user will be informed with Toast */

    protected void transferMoneySavingsAccount(View v, final String account_number, final String balance) {

        final Button button_transfer = (Button) ((Activity)context).findViewById(R.id.button_transfer);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        loading.setVisibility(View.VISIBLE);
        button_transfer.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD_MONEY_S, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {   //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        Toast.makeText(context, context.getString(R.string.transferSuc), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, context.getString(R.string.transferFail), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_transfer.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_transfer.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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
    } /* This method transfers money from user's account to the savingsaccount which account number user gives.
    If account number can't be found from database, user will be informed with Toast */

    protected void getAccountBalance(View v, String account_numberInput, final String my_account_number) {

        final String account_number = account_numberInput.substring(0, 1).toUpperCase() + account_numberInput.substring(1); //Muutetaan vielä varmuudeksi ensimmäinen kirjain isoksi niin kuin tietokannassakin on.

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
                            startTransferActivity(context, account_number, my_account_number);
                        }

                    } else  {
                        Toast.makeText(context, context.getString(R.string.noAccFound), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_account_number.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_account_number.setVisibility(View.VISIBLE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
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

    } /* This method tries to get account's balance and if it finds it, the balance will be added to
    Helperclass which instance will help the transaction.*/

    protected void addBankCard(View v, final String email, final String card_number, final String balance, final String withdraw_limit) {

        final Button button_create_card = (Button) ((Activity)context).findViewById(R.id.button_create_card);
        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);

        button_create_card.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_BANK_CARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")) {
                        float balance_float = Float.parseFloat(balance);
                        float withdraw_limit_float = Float.parseFloat(withdraw_limit);  //Kun luodaan uusi regularAccount olio, muunnetaan balance int -muotoon.
                        bank.addBankCard(email, card_number, balance_float, withdraw_limit_float);
                        Toast.makeText(context, context.getString(R.string.cardSucc), Toast.LENGTH_SHORT).show();
                        startHomeActivity(context);
                    } else {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        button_create_card.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    button_create_card.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
                        button_create_card.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("card_number", card_number);
                params.put("balance", balance);
                params.put("withdraw_limit", withdraw_limit);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    } /* This method takes the view v, card_number, balance and password
     withdraw limit values and saves them to database if they are valid.*/

    protected void saveBalanceBankCard(View v, final String email, final String balance) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_CARD_BALANCE, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");

                if (success.equals("1")) {
                    Toast.makeText(context, context.getString(R.string.depositSuc), Toast.LENGTH_SHORT).show();
                    startHomeActivity(context);
                } else {
                    Toast.makeText(context, context.getString(R.string.depositFail), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, context.getString(R.string.connectionError) + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, context.getString(R.string.connectionError) + error.toString(), Toast.LENGTH_SHORT).show();
                }
            })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("balance", balance);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }  /* This method takes email and balance as values and saves user's bank card's
    new balance to database */

   protected void saveLimitBankCard(View v, final String email, final String withdraw_limit) {

       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_CARD_LIMIT, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONObject jsonObject = new JSONObject(response);
                   String success = jsonObject.getString("success");

                   if (success.equals("1")) {
                       Toast.makeText(context, context.getString(R.string.withChange), Toast.LENGTH_SHORT).show();
                       startHomeActivity(context);
                   } else {
                       Toast.makeText(context, context.getString(R.string.withChangeFail), Toast.LENGTH_SHORT).show();
                   }

               } catch (JSONException e) {
                   e.printStackTrace();
                   Toast.makeText(context, context.getString(R.string.connectionError) + e.toString(), Toast.LENGTH_SHORT).show();
               }
           }
       },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(context, context.getString(R.string.connectionError) + error.toString(), Toast.LENGTH_SHORT).show();
                   }
               })
       {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("email", email);
               params.put("withdraw_limit", withdraw_limit);
               return params;
           }
       };

       RequestQueue requestQueue = Volley.newRequestQueue(context);
       requestQueue.add(stringRequest);

   } /* This method takes email and withdraw limit as values and saves user's bank card's
    new withdraw limit to database */

    public static void startHomeActivity(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    public static void startLogInActivity(Context context) {
        context.startActivity(new Intent(context, LogInActivity.class));
    }

    public static void startHomeActivityAfterLogin(Context context, String first_name, String email) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("first_name", first_name);
        intent.putExtra("email", email);
        context.startActivity(intent);
    }

    public static void startTransferActivity(Context context, String account_number, String my_account_number) {
        Intent intent = new Intent(context, TransferActivity.class);
        intent.putExtra(ACCOUNT_NUMBER_PAYEE, account_number);
        intent.putExtra(MY_ACCOUNT_NUMBER, my_account_number);
        context.startActivity(intent);
    }


}
