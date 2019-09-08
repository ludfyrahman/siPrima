package com.android.primaitech.siprima;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.primaitech.siprima.Akun.Login;
import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String PACKAGE_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        PACKAGE_NAME = getApplicationContext().getPackageName();
        Log.d("pesan", "package namenya adalah "+PACKAGE_NAME);
        final Intent i = new Intent(this, Login.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
    public void cektoken(){
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CEK_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);

                    JSONObject respon = res.getJSONObject("respon");
                    if(respon.getBoolean("boolauth")){
//                      Toast.makeText(splash_screen.this, respon.getString("pesan"), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getBaseContext(), "Session Expired", Toast.LENGTH_SHORT).show();
                        MainActivity.this.finish();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errornyaa ", "" + error);
                Toast.makeText(getBaseContext(), "Gagal Mengecek Token, "+error, Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", AuthData.getInstance(getApplicationContext()).getAuthKey());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }

}
