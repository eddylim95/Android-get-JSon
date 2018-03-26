package com.example.skynet.skynet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by eddyl on 25/3/2018.
 */

public class AsycThread extends AsyncTask<Void, Void, String> {

    private AppDatabase appDatabase;
    private JSONObject jsonObject;
    private View view;

    public AsycThread(AppDatabase appDatabase, JSONObject jsonObject, View view){
        this.appDatabase = appDatabase;
        this.jsonObject = jsonObject;
        this.view = view;
    }

    protected String doInBackground(Void... voids) {
        try {
            OneMapJsonHandler oneMapJsonHandler = new OneMapJsonHandler();
            oneMapJsonHandler.storeInSQL(appDatabase, jsonObject);
            /*String[] Names = new String[100];
            Names[0] = "Bedok Reservoir-Punggol Garden RC (Caf Corner)";
            appDatabase.hotspotDao().findAllNames(Names);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i< 90; i++){
                stringBuilder.append(Names[i]);
            }
            return stringBuilder.toString();*/
            String test = appDatabase.hotspotDao().findByPostcode(470719).getNAME();
            Log.d("SQL", test);
            return test;
        }
        catch (Exception e){
            Log.e("MYAPP", "doInBackground: ", e);
            return "ERROR";
        }
    }

}
