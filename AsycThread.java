package com.example.skynet.skynet;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by eddyl on 25/3/2018.
 */

public class AsycThread extends AsyncTask<Void, Void, Void> {

    private AppDatabase appDatabase;

    public AsycThread(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    protected Void doInBackground(Void... voids) {
        try {

            OneMapJsonHandler.getInstance().storeInSQL(appDatabase);
            String test = appDatabase.hotspotDao().findByPostcode(470719).getNAME(); //Query test
            Log.d("AsyncThread", test);
        }
        catch (Exception e){
            Log.e("AsyncThread", "Async doInBackground Error", e);
        }
        return null;
    }

}
