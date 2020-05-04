package com.example.bankapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.util.Locale;

public class ChangeLanguage {

    Context context;

    public ChangeLanguage(Context context) {  //Tämän rakentajan avulla voidaan tehdä muutoksia eri näkymissä.
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void changeLocale(String locale) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(locale));
        resources.updateConfiguration(config, dm);
    }


    public void changeLanguage(){
        final String[] lista = {"In English", "Suomeksi"};
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        //aBuilder.setTitle("choose language");
        aBuilder.setSingleChoiceItems(lista, -1, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 1){
                    changeLocale("fi");
                    System.out.println("sdsd");
                }else if(which == 0){
                    changeLocale("en");
                    System.out.println("sdsd");
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = aBuilder.create();
        dialog.show();

    }

    public static void startLogInActivity(Context context) { //Tämän metodin avulla voidaa käynnistää kotiaktiviteetti toisesta näkymästä.
        context.startActivity(new Intent(context, LogInActivity.class));
        }

}
