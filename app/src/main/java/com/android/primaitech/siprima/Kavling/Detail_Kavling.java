package com.android.primaitech.siprima.Kavling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Kategori_kavling.Kategori_kavling;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;

public class Detail_Kavling extends AppCompatActivity {
    ProgressDialog pd;
    LinearLayout bg;
    TextView nama_kavling, nama_unit, nama_proyek, nama_kategori, tipe_rumah, harga_produksi, harga_jual, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kavling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Kavling.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        nama_kavling = (TextView)findViewById(R.id.nama_kavling);
        nama_unit = (TextView)findViewById(R.id.nama_unit);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        nama_kategori = (TextView)findViewById(R.id.nama_kategori);
        tipe_rumah = (TextView)findViewById(R.id.tipe_rumah);
        harga_produksi = (TextView)findViewById(R.id.harga_produksi);
        harga_jual = (TextView)findViewById(R.id.harga_jual);
        status = (TextView)findViewById(R.id.status);
        bg = (LinearLayout)findViewById(R.id.bg);
        Log.d("pesan", "kode kavling "+data.getStringExtra("kode"));
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Kavling " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Kavling.class));
                Detail_Kavling.this.onBackPressed();
            }
        });
        loadJson();
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KAVLING+"detailkavling", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_kavling.setText(data.getString("nama_kavling"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    nama_kategori.setText(data.getString("nama_kategori"));
                    tipe_rumah.setText(data.getString("tipe_rumah"));
                    harga_produksi.setText(ServerAccess.numberConvert(data.getString("harga_produksi")));
                    harga_jual.setText(ServerAccess.numberConvert(data.getString("harga_jual")));
                    status.setText(ServerAccess.status_kavling[data.getInt("status")]);
                    Glide.with(Detail_Kavling.this)
                            .load(ServerAccess.BASE_URL+"/"+data.getString("desain_rumah"))
                            .into(new SimpleTarget<GlideDrawable>() {
                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        bg.setBackground(resource);
                                    }
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.cancel();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("kodekavling", data.getStringExtra("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
