package com.example.skynet.skynet;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String url = "https://developers.onemap.sg/privateapi/";
    private String urlType = "themesvc/retrieveTheme?queryName=wireless_hotspots&token=";
    private String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEzOTEsInVzZXJf" +
            "aWQiOjEzOTEsImVtYWlsIjoiZWRkeWxpbTk1QGhvdG1haWwuY29tIiwiZm9yZXZlciI6ZmFsc2UsImlzc" +
            "yI6Imh0dHA6XC9cL29tMi5kZmUub25lbWFwLnNnXC9hcGlcL3YyXC91c2VyXC9zZXNzaW9uIiwiaWF0I" +
            "joxNTIyMDY3ODYyLCJleHAiOjE1MjI0OTk4NjIsIm5iZiI6MTUyMjA2Nzg2MiwianRpIjoiZmU0YTBkOGVjN" +
            "WY2ZjEzMGJhOGFkZGNmOTQ2NDAzNzkifQ.zlkHGk-zlqzsSMsHKDLBxo1uS1lcd5XkEbuisv6g2As";

    public AppDatabase mDB;
    public Boolean internetConnection = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mDB = AppDatabase.getInstance(getApplicationContext());

        internetConnection = InternetConnection.getInternetStatus().getInternet();
        Log.d("MainActivity", "internetCon set" + internetConnection);
        if (this.internetConnection == true){
            try {
                OneMapJsonHandler.getInstance().retrieveJson(getApplicationContext());
            }
            catch (Exception e){
                Log.e("MainActivity", "retrieve Json failed", e);
            }
        }
        else {
            Log.d("MainActivity", "No Internet Connection, skipping Json retrieval");
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try {
                    new AsycThread(mDB).execute();
                }
                catch (Exception e) {
                    Log.e("MainActivity", "AsyncThread Error", e);
                }
            }
        });
    }

}