package com.android.primaitech.siprima.Kategori_kavling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.Divisi.Form_Divisi;
import com.android.primaitech.siprima.Divisi.Temp.Temp_Divisi;
import com.android.primaitech.siprima.Kategori_kavling.Temp.Temp_Kategori_Kavling;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.Unit_Bisnis.Pilih_Unit_Bisnis;
import com.android.primaitech.siprima.Unit_Bisnis.Temp.Temp_Unit_Bisnis;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Form_Kategori_Kavling extends AppCompatActivity {

    EditText nama_kategori;
    TextView pengawas, proyek;
    Button simpan;
    public static String kode_kategori_kavling = "", kode_pengawas = "", kode_proyek="";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_kategori_kavling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Form_Kategori_Kavling.this, Kategori_kavling.class));
            }
        });
        pd = new ProgressDialog(Form_Kategori_Kavling.this);

        nama_kategori = (EditText)findViewById(R.id.nama_kategori);
        pengawas = (TextView)findViewById(R.id.pengawas);
        proyek = (TextView)findViewById(R.id.proyek);
        simpan = (Button) findViewById(R.id.simpan);

        Intent data = getIntent();

        pengawas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Temp_Unit_Bisnis.getInstance(getBaseContext()).setNamaMenu("divisi");
                startActivity(new Intent(Form_Kategori_Kavling.this, Pilih_Unit_Bisnis.class));
            }
        });
        proyek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Temp_Unit_Bisnis.getInstance(getBaseContext()).setNamaMenu("divisi");
                startActivity(new Intent(Form_Kategori_Kavling.this, Pilih_Unit_Bisnis.class));
            }
        });

        if (Temp_Kategori_Kavling.getInstance(getBaseContext()).isExist()){
            loadData();
        }
        if (Temp_Kategori_Kavling.getInstance(getBaseContext()).getTipe_form().equals("edit")){
            if(data.hasExtra("nama_proyek")) {
                Temp_Kategori_Kavling.getInstance(getBaseContext()).setNamaProyek(data.getStringExtra("nama_proyek"));
                Temp_Kategori_Kavling.getInstance(getBaseContext()).setKodeProyek(data.getStringExtra("kode_proyek"));
                proyek.setText(Temp_Kategori_Kavling.getInstance(getBaseContext()).getNama_proyek());
                kode_proyek = Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_proyek();
            }
            if(data.hasExtra("nama_karyawan")) {
                Temp_Kategori_Kavling.getInstance(getBaseContext()).setNamaPengawas(data.getStringExtra("nama_karyawan"));
                Temp_Kategori_Kavling.getInstance(getBaseContext()).setKodeMandor(data.getStringExtra("kode_karyawan"));
                pengawas.setText(Temp_Kategori_Kavling.getInstance(getBaseContext()).getNama_pengawas());
                kode_pengawas = Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_mandor();
            }
                loadJson();
            toolbar.setTitle("Ubah Kategori Kavling");
        }else{
            toolbar.setTitle("Tambah Kategori Kavling");
        }

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Temp_Kategori_Kavling.getInstance(getBaseContext()).getTipe_form().equals("edit")){

                    ubah();
                }else{
                    simpan();
                }
            }
        });
    }
    private void loadData(){
        proyek.setText(Temp_Kategori_Kavling.getInstance(getBaseContext()).getNama_proyek());
        pengawas.setText(Temp_Kategori_Kavling.getInstance(getBaseContext()).getNama_pengawas());
        nama_kategori.setText(Temp_Kategori_Kavling.getInstance(getBaseContext()).getNama_kategori());
    }
    private void loadJson()
    {
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KATEGORI_KAVLING+"detailkategori", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_kategori.setText(data.getString("nama_kategori"));
                    Temp_Kategori_Kavling.getInstance(getBaseContext()).setNamaKategori(data.getString("nama_kategori"));
                    kode_kategori_kavling = data.getString("kode_kategori");
                    Temp_Kategori_Kavling.getInstance(getBaseContext()).setKodeKategori(data.getString("kode_kategori"));
                    Temp_Kategori_Kavling.getInstance(getBaseContext()).setKodeProyek(data.getString("kode_proyek"));
                    Temp_Kategori_Kavling.getInstance(getBaseContext()).setKodeMandor(data.getString("kode_mandor"));
                    load();
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
                params.put("kode_kategori", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_kategori());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void load(){
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PROYEK+"detailproyek", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    if (!res.getString("data").equals("null")){
                        JSONObject data = res.getJSONObject("data");
                        proyek.setText(data.getString("nama_proyek"));
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
                params.put("kode_proyek", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_proyek());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);

        StringRequest send = new StringRequest(Request.Method.POST, ServerAccess.URL_KARYAWAN+"detailproyek", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    if (!res.getString("data").equals("null")){
                        JSONObject data = res.getJSONObject("data");
                        pengawas.setText(data.getString("nama_karyawan"));
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
                params.put("kode_karyawan", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_mandor());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Divisi.class));
    }
    private void simpan(){
        final String nm_kategori= nama_kategori.getText().toString().trim();
        if(nm_kategori.equals("")){
            Toast.makeText(this, "Nama Kategori Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_kategori.setFocusable(true);
        }else if(kode_pengawas.equals("")){
            Toast.makeText(this, "Pengawas Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(kode_proyek.equals("")){
            Toast.makeText(this, "Proyek Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_KATEGORI_KAVLING+"prosestambah",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Kategori_Kavling.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Kategori_kavling.class));
                                } else {
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        getBaseContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(
                                    getBaseContext(),
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                    params.put("kode_mandor", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_mandor());
                    params.put("kode_proyek", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_proyek());
                    params.put("nama_kategori", nm_kategori);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
    private void ubah(){
        final String nm_kategori= nama_kategori.getText().toString().trim();
        if(nm_kategori.equals("")){
            Toast.makeText(this, "Nama Kategori Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_kategori.setFocusable(true);
        }else if(kode_pengawas.equals("")){
            Toast.makeText(this, "Pengawas Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(kode_proyek.equals("")){
            Toast.makeText(this, "Proyek Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_KATEGORI_KAVLING+"updatekategori",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Kategori_Kavling.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Kategori_kavling.class));
                                } else {
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        getBaseContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(
                                    getBaseContext(),
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                    params.put("kode_mandor", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_mandor());
                    params.put("kode_proyek", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_proyek());
                    params.put("nama_kategori", nm_kategori);
                    params.put("kode_kategori", Temp_Kategori_Kavling.getInstance(getBaseContext()).getKode_kategori());
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
