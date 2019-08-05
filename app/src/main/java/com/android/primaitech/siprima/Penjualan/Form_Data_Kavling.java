package com.android.primaitech.siprima.Penjualan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Kavling.Detail_Kavling;
import com.android.primaitech.siprima.Kavling.Pilih_Kavling;
import com.android.primaitech.siprima.Pembeli.Pilih_Pembeli;
import com.android.primaitech.siprima.Penjualan.Temp.Temp_Penjualan;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.githang.stepview.StepView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form_Data_Kavling extends AppCompatActivity {
    StepView mStepView;
    Button next, prev;
    ProgressDialog pd;
    TextView nama_kavling,nama_pembeli, nama_karyawan, harga_jual, tipe_rumah, luas_bangunan, luas_tanah, nama_proyek;
    ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data_kavling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tambah_Penjualan.class));
            }
        });
        mStepView = (StepView) findViewById(R.id.step_view);
        List<String> steps = Arrays.asList(new String[]{"Data Pembeli", "Data Kavling", "Pembayaran", "Konfirmasi"});
        mStepView.setSteps(steps);
        pd = new ProgressDialog(Form_Data_Kavling.this);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        int nextStep = mStepView.getCurrentStep() + 1;
            if (nextStep > mStepView.getStepCount()) {
                nextStep = 1;
            }
        mStepView.selectedStep(nextStep);
        next = (Button)findViewById(R.id.next);
        prev = (Button)findViewById(R.id.prev);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Data_Kavling.this, Form_Pembayaran.class));
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Data_Kavling.this, Tambah_Penjualan.class));
            }
        });
        nama_kavling = (TextView)findViewById(R.id.nama_kavling);
        nama_karyawan = (TextView)findViewById(R.id.nama_karyawan);
        harga_jual = (TextView)findViewById(R.id.harga_jual);
        tipe_rumah = (TextView)findViewById(R.id.tipe_rumah);
        luas_bangunan = (TextView)findViewById(R.id.luas_bangunan);
        luas_tanah = (TextView)findViewById(R.id.luas_tanah);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        gambar  = (ImageView)findViewById(R.id.gambar);
        nama_karyawan.setText(AuthData.getInstance(this).getUsername());
        nama_kavling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Data_Kavling.this, Pilih_Kavling.class));
            }
        });
        Intent data = getIntent();
        loadJson(Temp_Penjualan.getInstance(getBaseContext()).getKode_kavling());
        if(!data.hasExtra("kode")){
            Log.d("pesan", "kode kavling kosong");
        }else{
            Temp_Penjualan.getInstance(getBaseContext()).setKode_kavling(data.getStringExtra("kode"));
            loadJson(data.getStringExtra("kode"));
        }
        loadProyek();
    }
    public void onBackPressed() {
        startActivity(new Intent(Form_Data_Kavling.this, Tambah_Penjualan.class));
    }
    private void loadJson(final String kode)
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KAVLING+"detailkavling", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_kavling.setText(data.getString("nama_kavling"));
                    tipe_rumah.setText(data.getString("tipe_rumah"));
                    harga_jual.setText(ServerAccess.numberConvert(data.getString("harga_jual")));
                    luas_bangunan.setText(data.getString("panjang")+" x "+data.getString("lebar"));
                    luas_tanah.setText(data.getString("panjang_tanah")+" x "+data.getString("lebar_tanah"));
                    Temp_Penjualan.getInstance(getBaseContext()).setHarga_jual(data.getString("harga_jual"));
                    Glide.with(Form_Data_Kavling.this)
                            .load(ServerAccess.BASE_URL+"/"+data.getString("desain_rumah"))
                            .into(gambar);
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
                params.put("kodekavling", kode);
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void loadProyek()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PROYEK+"detailproyek", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_proyek.setText(data.getString("nama_proyek"));
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
                params.put("kode_proyek", AuthData.getInstance(getBaseContext()).getKode_proyek());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
