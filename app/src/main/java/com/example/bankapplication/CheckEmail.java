package com.example.bankapplication;

import android.app.Activity;
import android.content.Context;
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

import java.util.HashMap;
import java.util.Map;

public class CheckEmail {

    private static String URL_CHECK_EMAIL = "http://192.168.1.162/android_register_login/check_email.php";
    Context context;
    public static int ErrorCounter = 0;

    public CheckEmail(Context context){  //Tämän rakentajan avulla voidaan tehdä muutoksia eri näkymissä.
        this.context=context;
    }

    protected void checkIfEmailAvailable(final String email) {

        final ProgressBar loading = (ProgressBar) ((Activity)context).findViewById(R.id.loading);
        final Button button_regist = (Button) ((Activity)context).findViewById(R.id.button_regist);

        loading.setVisibility(View.VISIBLE);
        button_regist.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_EMAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(!(success.equals("1"))) {
                        Toast.makeText(context, "SÄHKÖPOSTI EI KÄY", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "ERROOOOR", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    button_regist.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Yhteysvirhe", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        button_regist.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        System.out.println(ErrorCounter);

    }

}
