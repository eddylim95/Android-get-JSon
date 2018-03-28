package com.example.skynet.skynet;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To enable internet in Emulator
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (InternetConnection.getInternetStatus().getInternet() == true){
            try {
                Log.d("MainActivity", "Internet connected");
                OneMapJsonHandler.getInstance().retrieveJson(getApplicationContext());
            }
            catch (Exception e){
                Log.e("MainActivity", "Internet connection failed, skipping Json retrieval", e);
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
                    new AsycThread(AppDatabase.getInstance(getApplicationContext())).execute();
                }
                catch (Exception e) {
                    Log.e("MainActivity", "AsyncThread Error", e);
                }
            }
        });
    }

}