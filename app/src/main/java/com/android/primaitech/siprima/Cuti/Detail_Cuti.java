package com.android.primaitech.siprima.Cuti;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.android.primaitech.siprima.Akun.Login;
import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.Kegiatan.Detail_Kegiatan;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Detail_Cuti extends AppCompatActivity {
    ProgressDialog pd;
    Button terima, batalkan,tolak;
    TextView tgl_awal, tgl_akhir, keterangan, status, create_at, acc_at, nama_karyawan, nama_proyek, nama_unit, namaacc, lama_cuti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cuti);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        final Intent data = getIntent();
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Detail Cuti " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cuti.class));
            }
        });
        tgl_awal = (TextView)findViewById(R.id.tgl_awal);

        tgl_akhir = (TextView)findViewById(R.id.tgl_akhir);
        keterangan = (TextView)findViewById(R.id.keterangan);
        status = (TextView)findViewById(R.id.status);
        acc_at = (TextView)findViewById(R.id.acc_at);
        nama_karyawan = (TextView)findViewById(R.id.nama_karyawan);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        nama_unit = (TextView)findViewById(R.id.nama_unit);
        namaacc = (TextView)findViewById(R.id.namaacc);
        lama_cuti = (TextView)findViewById(R.id.lama_cuti);
        pd = new ProgressDialog(this);
        terima = (Button) findViewById(R.id.terima);
        batalkan = (Button) findViewById(R.id.batalkan);
        tolak = (Button) findViewById(R.id.tolak);
        terima.setVisibility(View.GONE);
        batalkan.setVisibility(View.GONE);
        tolak.setVisibility(View.GONE);
        loadJson(data.getStringExtra("kode"));
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Detail_Cuti.this)
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Menerima Cuti ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                konfirmasi(data.getStringExtra("kode"));


                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Detail_Cuti.this)
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Menolaks Cuti ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tolak(data.getStringExtra("kode"));


                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
        batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Detail_Cuti.this)
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Membatalkan Cuti ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                batal(data.getStringExtra("kode"));


                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
    }
    private void loadJson(final String kode)
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CUTI+"detailcuti", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONObject dataAksi = res.getJSONObject("aksi");
                    tgl_awal.setText(ServerAccess.parseDate(data.getString("tgl_awal")));
                    tgl_akhir.setText(ServerAccess.parseDate(data.getString("tgl_akhir")));
                    keterangan.setText(data.getString("keterangan"));
                    status.setText(data.getString("status"));
                    acc_at.setText(data.getString("acc_at"));
                    nama_karyawan.setText(data.getString("nama_karyawan"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    nama_unit.setText(data.getString("nama_unit"));
                    namaacc.setText(data.getString("namaacc"));
                    lama_cuti.setText(data.getString("lama_cuti")+" Hari");
                    if (dataAksi.getBoolean("konfirmasi"))
                        terima.setVisibility(View.VISIBLE);
                    switch (data.getString("status")){
                        case "Di Tolak":
                            batalkan.setVisibility(View.GONE);
                            tolak.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            break;
                        case "Menunggu":
                            batalkan.setVisibility(View.GONE);
                            tolak.setVisibility(View.VISIBLE);
                            terima.setVisibility(View.VISIBLE);
                            break;
                        case "Di Batalkan":
                            tolak.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            batalkan.setVisibility(View.GONE);
                            break;
                        default:
                            batalkan.setVisibility(View.VISIBLE);
                            terima.setVisibility(View.GONE);
                            tolak.setVisibility(View.GONE);
                    }
                    try{

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date c = Calendar.getInstance().getTime();
                        String now = formatter.format(c);
                        String str1 = data.getString("tgl_awal");
                        Date tanggal_cuti = formatter.parse(str1);

                        String str2 = now;
                        Date sekarang = formatter.parse(str2);

                        if (tanggal_cuti.compareTo(sekarang)<0){
                            batalkan.setVisibility(View.GONE);
                        }else{
                            batalkan.setVisibility(View.VISIBLE);
                        }

                    }catch (ParseException e1){
                        e1.printStackTrace();
                    }
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
                params.put("kodedetailcuti", kode);
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void konfirmasi(final String kode){
            pd.setMessage("Menampilkan Data");
            pd.setCancelable(false);
            pd.show();
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CUTI+"konfirmasicuti", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        JSONObject arr = res.getJSONObject("respon");

                        if(arr.getBoolean("status")){
                            Toast.makeText(getBaseContext(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), Cuti.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getBaseContext(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                        }
                        pd.cancel();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pd.cancel();
                        Log.d("pesan", "error "+e.getMessage());
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
                    params.put("status", "1");
                    params.put("kodecuti", kode);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(senddata);
    }
    private void tolak(final String kode){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CUTI+"konfirmasicuti", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject arr = res.getJSONObject("respon");

                    if(arr.getBoolean("status")){
                        Toast.makeText(getBaseContext(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), Cuti.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getBaseContext(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                    }
                    pd.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.cancel();
                    Log.d("pesan", "error "+e.getMessage());
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
                params.put("status", "2");
                params.put("kodecuti", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    private void batal(final String kode){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CUTI+"batalkancuti", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject arr = res.getJSONObject("respon");

                    if(arr.getBoolean("status")){
                        Toast.makeText(getBaseContext(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), Cuti.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getBaseContext(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                    }
                    pd.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.cancel();
                    Log.d("pesan", "error "+e.getMessage());
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
                params.put("kodecuti", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}
