package com.example.skynet.skynet;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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



import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    private String url = "https://developers.onemap.sg/privateapi/";
    private String urlType = "themesvc/retrieveTheme?queryName=wireless_hotspots&token=";
    private String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEzOTEsInVzZXJf" +
            "aWQiOjEzOTEsImVtYWlsIjoiZWRkeWxpbTk1QGhvdG1haWwuY29tIiwiZm9yZXZlciI6ZmFsc2UsImlzc" +
            "yI6Imh0dHA6XC9cL29tMi5kZmUub25lbWFwLnNnXC9hcGlcL3YyXC91c2VyXC9zZXNzaW9uIiwiaWF0I" +
            "joxNTIyMDY3ODYyLCJleHAiOjE1MjI0OTk4NjIsIm5iZiI6MTUyMjA2Nzg2MiwianRpIjoiZmU0YTBkOGVjN" +
            "WY2ZjEzMGJhOGFkZGNmOTQ2NDAzNzkifQ.zlkHGk-zlqzsSMsHKDLBxo1uS1lcd5XkEbuisv6g2As";

    private int progress = 0;
    JSONObject json;
    AppDatabase mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        final Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        EditText editText = (EditText) findViewById(R.id.editSearch);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progress = 0;
        progressBar.setProgress(progress);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetAvailable();
            }
        });

        mDB = AppDatabase.getInstance(getApplicationContext());
        isInternetAvailable();
        retrieveJson();

        try {
            final Button button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AsycThread(mDB, json, view).execute().toString();
                }
            });
        }
        catch (Exception e){
            Log.e("MYAPP", "onCreate: ", e);
        }


    }



    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            EditText editText = (EditText) findViewById(R.id.editText);
            if (!ipAddr.equals("") == true){
                editText.setText("Internet Connected!");
            }
            else {
                editText.setText("Internet not Connected!");
            }
            return !ipAddr.equals("");

        } catch (Exception e) {
            EditText editText = (EditText) findViewById(R.id.editText);
            editText.setText("Internet Error");
            Log.e("MYAPP", "exception", e);
            return false;
        }
    }

    private void updateProgress(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(progress);
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
                        Log.e("MYAPP", "exception", e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MYAPP", "exception", error);
                }
            });
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("MYAPP", "exception", e);
        }
    }

    public String getConcatUrl() {
        return (url + urlType + apiKey);
    }
}

