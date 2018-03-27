package com.example.skynet.skynet;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by eddyl on 25/3/2018.
 */

public class AsycThread extends AsyncTask<Void, Void, Void> {

    private AppDatabase appDatabase;
    private JSONObject jsonObject;

    public AsycThread(AppDatabase appDatabase, JSONObject jsonObject){
        this.appDatabase = appDatabase;
        this.jsonObject = jsonObject;
    }

    protected Void doInBackground(Void... voids) {
        try {
            OneMapJsonHandler oneMapJsonHandler = new OneMapJsonHandler();
            oneMapJsonHandler.storeInSQL(appDatabase, jsonObject);
            String test = appDatabase.hotspotDao().findByPostcode(470719).getNAME(); //Query test
            Log.d("SQL", test);
        }
        catch (Exception e){
            Log.e("Background", "doInBackground: ", e);
        }
        return null;
    }

}
