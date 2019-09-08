package com.android.primaitech.siprima.Divisi;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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
import com.android.primaitech.siprima.Divisi.Temp.Temp_Divisi;
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

public class Form_Divisi extends AppCompatActivity {
    EditText nama_divisi;
    TextView usaha;
    Button simpan;
    public static String tipe_akun="1", kode_rab="", kode_usaha="";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_divisi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Form_Divisi.this, Divisi.class));
            }
        });
        pd = new ProgressDialog(Form_Divisi.this);

        nama_divisi = (EditText)findViewById(R.id.nama_divisi);
        nama_divisi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Divisi.getInstance(getBaseContext()).setNamaDivisi(nama_divisi.getText().toString());
            }
        });
        usaha = (TextView)findViewById(R.id.usaha);
        simpan = (Button) findViewById(R.id.simpan);

        Intent data = getIntent();

        usaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Temp_Unit_Bisnis.getInstance(getBaseContext()).setNamaMenu("divisi");
                startActivity(new Intent(Form_Divisi.this, Pilih_Unit_Bisnis.class));
            }
        });

        if (Temp_Divisi.getInstance(getBaseContext()).isExist()){
            loadData();
        }
        if (Temp_Divisi.getInstance(getBaseContext()).getTipe_form().equals("edit")){
            if(data.hasExtra("nama_usaha")) {
                Temp_Divisi.getInstance(getBaseContext()).setUnitBisnis(data.getStringExtra("nama_usaha"));
                Temp_Divisi.getInstance(getBaseContext()).setKodeUsaha(data.getStringExtra("kode_usaha"));
                usaha.setText(Temp_Divisi.getInstance(getBaseContext()).getUnit_bisnis());
                kode_usaha = Temp_Divisi.getInstance(getBaseContext()).getKode_usaha();
                Log.d("pesan", "kode usaha "+Temp_Divisi.getInstance(getBaseContext()).getKode_usaha());
            }else{
                loadJson();
            }
            Log.d("pesan", "loadjson fungsi");
            toolbar.setTitle("Ubah Divisi");
        }else{
            toolbar.setTitle("Tambah Divisi");
        }

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Temp_Divisi.getInstance(getBaseContext()).getTipe_form().equals("edit")){

                    ubah();
                }else{
                    simpan();
                }
            }
        });
    }
    private void loadData(){
        usaha.setText(Temp_Divisi.getInstance(getBaseContext()).getUnit_bisnis());
        nama_divisi.setText(Temp_Divisi.getInstance(getBaseContext()).getNama_divisi());
    }
    private void loadJson()
    {
        Log.d("pesan", "loadjson fungsi load");
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_DIVISI+"detail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_divisi.setText(data.getString("nama_divisi"));
                    Temp_Divisi.getInstance(getBaseContext()).setNamaDivisi(data.getString("nama_divisi"));
                    Temp_Divisi.getInstance(getBaseContext()).setKodeUsaha(data.getString("kode_unit"));
                    kode_usaha = data.getString("kode_unit");
                    loadUnitBisnis();
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
                params.put("kode_divisi", Temp_Divisi.getInstance(getBaseContext()).getKode_divisi());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void loadUnitBisnis(){
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_UNIT_BISNIS+"detailunitbisnis", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    if (!res.getString("data").equals("null")){
                        JSONObject data = res.getJSONObject("data");
                        usaha.setText(data.getString("nama_unit"));
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
                params.put("kode_unit", Temp_Divisi.getInstance(getBaseContext()).getKode_usaha());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Divisi.class));
    }
    private void simpan(){
        final String nm_divisi= nama_divisi.getText().toString().trim();
        if(nm_divisi.equals("")){
            Toast.makeText(this, "Nama Divisi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_divisi.setFocusable(true);
        }else if(kode_usaha.equals("")){
            Toast.makeText(this, "Unit Bisnis Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_DIVISI+"prosestambah",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Divisi.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Divisi.class));
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
                    params.put("nama", nm_divisi);
                    params.put("kode_unit", kode_usaha);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
    private void ubah(){

        final String nm_divisi= nama_divisi.getText().toString().trim();
        if(nm_divisi.equals("")){
            Toast.makeText(this, "Nama Divisi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_divisi.setFocusable(true);
        }else if(Temp_Divisi.getInstance(getBaseContext()).getKode_usaha().equals("")){
            Toast.makeText(this, "Unit Bisnis Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("pesan", "kode usaha do simpan "+Temp_Divisi.getInstance(getBaseContext()).getKode_usaha());
            Log.d("pesan", "kode usaha variabel do simpan "+kode_usaha);
            Log.d("pesan", "kode divisi "+Temp_Divisi.getInstance(getBaseContext()).getKode_divisi());
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_DIVISI+"updatedivisi",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Divisi.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Divisi.class));
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
                    params.put("nama", nm_divisi);
                    params.put("kode_proyek", Temp_Divisi.getInstance(getBaseContext()).getKode_usaha());
                    params.put("kode_divisi", Temp_Divisi.getInstance(getBaseContext()).getKode_divisi());
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
