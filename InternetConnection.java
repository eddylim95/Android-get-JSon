package com.example.skynet.skynet;

import android.util.Log;

import java.net.InetAddress;

//Internet connection singleton

public class InternetConnection {
    private boolean internet = false;

    private static InternetConnection internetConnection = new InternetConnection();

    private InternetConnection(){
    }

    public static InternetConnection getInternetStatus(){
        return internetConnection;
    }

    public boolean getInternet(){
        return internet;
    }


    private boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            if (!ipAddr.equals("") == true){
                Log.d("Internet","Internet connected");
                internet = true;
            }
            else {
                Log.d("Internet","Internet not connected");
                internet = false;
            }
            return !ipAddr.equals("");

        } catch (Exception e) {
            Log.e("Internet", "Internet Error", e);
            internet = false;
            return false;
        }
    }
}
