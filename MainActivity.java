package com.example.skynet.skynet;


import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String url = "https://developers.onemap.sg/privateapi/";
    private String urlType = "themesvc/retrieveTheme?queryName=wireless_hotspots&token=";
    private String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEzOTEsInVzZXJf" +
            "aWQiOjEzOTEsImVtYWlsIjoiZWRkeWxpbTk1QGhvdG1haWwuY29tIiwiZm9yZXZlciI6ZmFsc2UsImlzc" +
            "yI6Imh0dHA6XC9cL29tMi5kZmUub25lbWFwLnNnXC9hcGlcL3YyXC91c2VyXC9zZXNzaW9uIiwiaWF0I" +
            "joxNTIyMDY3ODYyLCJleHAiOjE1MjI0OTk4NjIsIm5iZiI6MTUyMjA2Nzg2MiwianRpIjoiZmU0YTBkOGVjN" +
            "WY2ZjEzMGJhOGFkZGNmOTQ2NDAzNzkifQ.zlkHGk-zlqzsSMsHKDLBxo1uS1lcd5XkEbuisv6g2As";

    JSONObject json;
    AppDatabase mDB;
    Boolean internetConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            RequestQueue mRequestQueue;
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            final Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        catch (Exception e){
            Log.e("MainActivity", "Request Queue error", e);
        }

        InternetConnection internetConnection = InternetConnection.getInternetStatus();
        internetConnection.getInternet();

        mDB = AppDatabase.getInstance(getApplicationContext());
        retrieveJson();

        try {
            Button button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AsycThread(mDB, json).execute();
                }
            });
        }
        catch (Exception e){
            Log.e("Button", "onCreate error: ", e);
        }


    }

    private void retrieveJson() {
        try {
            String urls = this.getConcatUrl();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urls,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        json = response;
                    } catch (Exception e) {
                        Log.e("Json", "Failed assign Json", e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Json", "Volley error", error);
                }
            });
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("Json", "JsonObjectRequest failed", e);
        }
    }

    public String getConcatUrl() {
        return (url + urlType + apiKey);
    }
}

