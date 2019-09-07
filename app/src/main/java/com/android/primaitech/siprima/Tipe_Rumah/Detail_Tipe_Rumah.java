package com.android.primaitech.siprima.Tipe_Rumah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Kunjungan_Pembeli;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Detail_Tipe_Rumah extends AppCompatActivity {
    ProgressDialog pd;
    TextView nama_tipe, luas_rumah, luas_tanah, biaya_kontruksi, harga_jual, biaya_tanah, total_biaya_konstruksi, internal_cost,
            eksternal_cost, hpp, margin_profit, nama_proyek;
    LinearLayout bg;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tipe_rumah);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Tipe_Rumah.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        nama_tipe = (TextView)findViewById(R.id.nama_tipe);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        luas_rumah = (TextView)findViewById(R.id.luas_bangunan);
        luas_tanah = (TextView)findViewById(R.id.luas_tanah);
        biaya_kontruksi = (TextView)findViewById(R.id.biaya_konstruksi);
        harga_jual = (TextView)findViewById(R.id.harga_jual);
        biaya_tanah = (TextView)findViewById(R.id.biaya_tanah);
        total_biaya_konstruksi = (TextView)findViewById(R.id.total_biaya_konstruksi);
        internal_cost = (TextView)findViewById(R.id.internal_cost);
        eksternal_cost = (TextView)findViewById(R.id.eksternal_cost);
        hpp = (TextView)findViewById(R.id.hpp);
        margin_profit = (TextView)findViewById(R.id.margin_profit);
        bg = (LinearLayout)findViewById(R.id.bg);
        loadJson();
        Intent data = getIntent();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tipe_Rumah.class));
//                Detail_Pembeli.this.onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
//        spv_dev_list_komplain.this.finish();
        startActivity(new Intent(getBaseContext(), Tipe_Rumah.class));
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_TIPE_RUMAH+"detailtipe", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONObject proyek = res.getJSONObject("proyek");
                    toolbar.setTitle(res.getString("title"));
                    nama_tipe.setText(data.getString("nama_tipe"));
                    luas_rumah.setText(data.getString("luas_rumah"));
                    luas_tanah.setText(data.getString("luas_tanah"));
                    biaya_kontruksi.setText(ServerAccess.numberConvert(data.getString("biaya_kontruksi")));
                    harga_jual.setText(ServerAccess.numberConvert(data.getString("harga_jual")));
                    double hpp_lahan = proyek.getDouble("hpp_lahan");
                    double lahan_terbangun = proyek.getDouble("lahan_terbangun") / 100.0;
                    double tnh = (hpp_lahan / (lahan_terbangun * proyek.getDouble("luas_proyek"))) * data.getDouble("luas_tanah") ;
                    biaya_tanah.setText(ServerAccess.numberConvert(new BigDecimal(tnh).toString()));
                    double ttlkontruksi = data.getDouble("luas_rumah") * data.getDouble("biaya_kontruksi");
                    double dis = 0;
                    if (data.getString("mp_persen") != "null" || Integer.parseInt(data.getString("mp_persen")) > 0){
                        dis = data.getDouble("mp_persen") / 100.0;
                    }
                    double jual = tnh + ttlkontruksi + data.getDouble("ttl_ext");
                    double marginrp = dis * jual;
                    jual = (dis * jual) + jual;
                    double tanpaeks = jual - data.getDouble("ttl_ext");
                    internal_cost.setText(ServerAccess.numberConvert(new BigDecimal((tnh + ttlkontruksi)).toString()));
                    eksternal_cost.setText(ServerAccess.numberConvert(data.getString("ttl_ext")));
                    hpp.setText(ServerAccess.numberConvert(new BigDecimal((tnh + ttlkontruksi + data.getDouble("ttl_ext"))).toString()));
                    margin_profit.setText(ServerAccess.numberConvert(new BigDecimal(marginrp).toString()));
                    total_biaya_konstruksi.setText(ServerAccess.numberConvert(new BigDecimal(ttlkontruksi).toString()));
                    nama_proyek.setText(proyek.getString("nama_proyek"));
                    Glide.with(Detail_Tipe_Rumah.this)
                            .load(ServerAccess.BASE_URL+"/"+data.getString("desain_rumah"))
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
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
                params.put("kode_tipe", data.getStringExtra("kode"));
                params.put("show", "1");
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
