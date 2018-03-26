package com.example.skynet.skynet;

import android.arch.persistence.room.Database;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetAddress;

/**
 * Created by eddyl on 24/3/2018.
 */

public class OneMapJsonHandler implements JsonHandler {

    private int index;
    private int numHotspots;


    public OneMapJsonHandler() {
    }

    @Override
    public void storeInSQL(AppDatabase db, JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("SrchResults");
            numHotspots = Integer.parseInt(jsonArray.getJSONObject(0).getString("FeatCount"));
            int[] addressPostalCode = new int[numHotspots];
            double[] longtitude = new double[numHotspots];
            double[] lattitude = new double[numHotspots];
            String[] name = new String[numHotspots];
            String[] description = new String[numHotspots];
            String[] addressStreetName = new String[numHotspots];
            String[] operatorName = new String[numHotspots];
            StringBuilder stringBuilder = new StringBuilder();
            String temp[] = new String[2];
            db.hotspotDao().dropTable();
            for (int i = 0; i < numHotspots; i++) {
                try{
                    index = i;
                    name[i] = jsonArray.getJSONObject(i + 1).getString("NAME");
                    description[i] = jsonArray.getJSONObject(i + 1).getString("DESCRIPTION");
                    addressStreetName[i] = jsonArray.getJSONObject(i + 1).getString("ADDRESSSTREETNAME");
                    operatorName[i] = jsonArray.getJSONObject(i + 1).getString("OPERATOR_NAME");
                    addressPostalCode[i] = Integer.parseInt(jsonArray.getJSONObject(i + 1).getString("ADDRESSPOSTALCODE"));
                    temp = jsonArray.getJSONObject(i + 1).getString("LatLng").split(",");
                    lattitude[i] = Double.parseDouble(temp[0]);
                    longtitude[i] = Double.parseDouble(temp[1]);
                    Hotspot hotspot = new Hotspot(index, lattitude[i], longtitude[i], addressPostalCode[i],
                            description[i], name[i], addressStreetName[i], operatorName[i]);
                    db.hotspotDao().insertAll(hotspot);
                }
                catch (Exception e){
                    Log.e("SQL", "storeInSQL: ", e);
                }
            }
        } catch (Exception e) {
            Log.e("DROP", "Json handler error", e);
        }
    }


}






