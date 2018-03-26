package com.example.skynet.skynet;

import android.arch.persistence.room.Database;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by eddyl on 24/3/2018.
 */

public interface JsonHandler {

    public void storeInSQL(AppDatabase db, JSONObject jsonObject);

}
