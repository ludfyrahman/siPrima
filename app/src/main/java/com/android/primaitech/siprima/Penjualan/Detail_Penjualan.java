package com.android.primaitech.siprima.Penjualan;

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
import com.android.primaitech.siprima.Kavling.Detail_Kavling;
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

import java.util.HashMap;
import java.util.Map;

public class Detail_Penjualan extends AppCompatActivity {
    ProgressDialog pd;
    LinearLayout bg;
    TextView nama_kavling, nama_proyek, nama_karyawan, nama_pembeli, tanggal_penjualan, cara_beli, harga_jual_bersih, uang_booking, tanggal_bayar_booking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Penjualan.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        bg = (LinearLayout)findViewById(R.id.bg);
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Kavling " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Kavling.class));
                Detail_Penjualan.this.onBackPressed();
            }
        });
        loadJson();
        nama_kavling = (TextView)findViewById(R.id.nama_kavling);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        nama_karyawan = (TextView)findViewById(R.id.nama_karyawan);
        nama_pembeli = (TextView)findViewById(R.id.nama_pembeli);
        tanggal_penjualan = (TextView)findViewById(R.id.tanggal_penjualan);
        cara_beli = (TextView)findViewById(R.id.cara_beli);
        harga_jual_bersih = (TextView)findViewById(R.id.harga_jual_bersih);
        uang_booking = (TextView)findViewById(R.id.uang_booking);
        tanggal_bayar_booking = (TextView)findViewById(R.id.tanggal_bayar_booking);
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PENJUALAN+"detailpenjualan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_kavling.setText(data.getString("nama_kategori")+" "+data.getString("nama_kavling"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    nama_karyawan.setText(data.getString("nama_karyawan"));
                    nama_pembeli.setText(data.getString("nama_pembeli"));
                    tanggal_penjualan.setText(ServerAccess.parseDate(data.getString("create_at")));
                    tanggal_bayar_booking.setText(ServerAccess.parseDate(data.getString("tanggal_bayar_booking")));
                    harga_jual_bersih.setText(ServerAccess.numberConvert(data.getString("harga_jual_bersih")));
                    uang_booking.setText(ServerAccess.numberConvert(data.getString("uang_booking")));
                    cara_beli.setText(ServerAccess.status_kavling[data.getInt("cara_beli")]);
                    Glide.with(Detail_Penjualan.this)
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
