package Skynet_Main;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by eddyl on 25/3/2018.
 */

public class AsycQuery extends AsyncTask<Void, Void, Void> {

    private AppDatabase appDatabase;

    public AsycQuery(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    protected Void doInBackground(Void... voids) {
        try {
            List hotspots = appDatabase.hotspotDao().getAll();
            Log.d("AsyncQuery", hotspots.toString());//Query test
        }
        catch (Exception e){
            Log.e("AsyncQuery", "Async doInBackground Error", e);
        }
        return null;
    }

}
