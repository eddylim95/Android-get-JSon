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

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class GetJSon {
        private void retrieveJson(){
                try{
                    RequestQueue mRequestQueue;
                    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
                    Network network = new BasicNetwork(new HurlStack());
                    mRequestQueue = new RequestQueue(cache, network);
                    mRequestQueue.start();

                    String url = "URL";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //store or print response here

                                }
                            catch (Exception e) {
                                Log.e("MYAPP", "exception", e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("MYAPP", "exception", error);
                        }
                    });
                    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
                }
                catch(Exception e){
                    Log.e("MYAPP", "exception", e);
                }
            }
}
