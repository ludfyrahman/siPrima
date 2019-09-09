package com.primagroup.primaitech.siprima.Akun_Bank;

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

import com.primagroup.primaitech.siprima.Akun_Bank.Temp.Temp_Akun_Bank;
import com.primagroup.primaitech.siprima.Config.AppController;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.RequestHandler;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Proyek.Pilih_Proyek;
import com.android.primaitech.siprima.R;
import com.primagroup.primaitech.siprima.RAB.Pilih_RAB;
import com.primagroup.primaitech.siprima.Unit_Bisnis.Pilih_Unit_Bisnis;
import com.primagroup.primaitech.siprima.Unit_Bisnis.Temp.Temp_Unit_Bisnis;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Form_Akun_Bank extends AppCompatActivity {
    EditText nama_rekening, nama_bank, no_rekening;
    TextView rab, usaha;
    Button simpan, ab_proyek, ab_unit_bisnis;
    public static String tipe_akun="1", kode_rab="", kode_usaha="";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_akun_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Form_Akun_Bank.this, Akun_bank.class));
            }
        });
        pd = new ProgressDialog(Form_Akun_Bank.this);

        nama_rekening = (EditText)findViewById(R.id.nama_rekening);
        nama_rekening.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Akun_Bank.getInstance(getBaseContext()).setNamaRekening(nama_rekening.getText().toString());
            }
        });
        nama_bank = (EditText)findViewById(R.id.nama_bank);
        nama_bank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Akun_Bank.getInstance(getBaseContext()).setNamaBank(nama_bank.getText().toString());
            }
        });
        no_rekening = (EditText)findViewById(R.id.no_rekening);
        no_rekening.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Akun_Bank.getInstance(getBaseContext()).setNoRekening(no_rekening.getText().toString());
            }
        });
        rab = (TextView)findViewById(R.id.rab);
        usaha = (TextView)findViewById(R.id.usaha);
        simpan = (Button) findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Temp_Akun_Bank.getInstance(getBaseContext()).getTipe_form().equals("edit")){
                    ubah();
                }else{
                    simpan();
                }
            }
        });
        Akun_bank m = new Akun_bank();
        Intent data = getIntent();
        tipe_akun = m.tipe_akun;
        rab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Pilih_RAB.class);
                intent.putExtra("kode", tipe_akun);
                startActivity(intent);
            }
        });
        usaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tipe_akun.equals("1")){
                    Temp_Unit_Bisnis.getInstance(getBaseContext()).setNamaMenu("akunbank");
                    startActivity(new Intent(Form_Akun_Bank.this, Pilih_Unit_Bisnis.class));
                }else{
                    startActivity(new Intent(Form_Akun_Bank.this, Pilih_Proyek.class));
                }
            }
        });
        if(data.hasExtra("nama_usaha")) {
            Temp_Akun_Bank.getInstance(getBaseContext()).setNamaUsaha(data.getStringExtra("nama_usaha"));
            Temp_Akun_Bank.getInstance(getBaseContext()).setKodeUsaha(data.getStringExtra("kode_usaha"));
            usaha.setText(Temp_Akun_Bank.getInstance(getBaseContext()).getNama_usaha());
            kode_usaha = Temp_Akun_Bank.getInstance(getBaseContext()).getKode_usaha();
        }
        if(data.hasExtra("nama_rab")) {
            Temp_Akun_Bank.getInstance(getBaseContext()).setNamaRab(data.getStringExtra("nama_rab"));
            Temp_Akun_Bank.getInstance(getBaseContext()).setKodeRab(data.getStringExtra("kode_rab"));
            rab.setText(Temp_Akun_Bank.getInstance(getBaseContext()).getNama_rab());
            kode_rab = Temp_Akun_Bank.getInstance(getBaseContext()).getKode_rab();
            Log.d("pesan", "kode rab adalah "+kode_rab);
        }
        Log.d("pesan", "nama rekening adalah "+Temp_Akun_Bank.getInstance(getBaseContext()).getNama_rekening());
        if (Temp_Akun_Bank.getInstance(getBaseContext()).isExist()){
            loadData();
        }
        if (Temp_Akun_Bank.getInstance(getBaseContext()).getTipe_form().equals("edit")){
            loadJson();
            toolbar.setTitle("Ubah Akun Bank ");
        }else{
            toolbar.setTitle("Tambah Akun Bank ");
        }
    }
    private void loadData(){
        usaha.setText(Temp_Akun_Bank.getInstance(getBaseContext()).getNama_usaha());
        rab.setText(Temp_Akun_Bank.getInstance(getBaseContext()).getNama_rab());
        nama_rekening.setText(Temp_Akun_Bank.getInstance(getBaseContext()).getNama_rekening());
        nama_bank.setText(Temp_Akun_Bank.getInstance(getBaseContext()).getNama_bank());
        no_rekening.setText(Temp_Akun_Bank.getInstance(getBaseContext()).getNo_rekening());
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_AKUN_BANK+"detail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_rekening.setText(data.getString("nama_rekening"));
                    Temp_Akun_Bank.getInstance(getBaseContext()).setNamaRekening(data.getString("nama_rekening"));
                    nama_bank.setText(data.getString("nama_bank"));
                    Temp_Akun_Bank.getInstance(getBaseContext()).setNamaBank(data.getString("nama_bank"));
                    no_rekening.setText(data.getString("no_rekening"));
                    Temp_Akun_Bank.getInstance(getBaseContext()).setNoRekening(data.getString("no_rekening"));
                    kode_rab = data.getString("kode_rab");
                    Temp_Akun_Bank.getInstance(getBaseContext()).setKodeRab(data.getString("kode_rab"));
                    kode_usaha = data.getString("kode_usaha");
                    Temp_Akun_Bank.getInstance(getBaseContext()).setKodeUsaha(data.getString("kode_usaha"));
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
                params.put("kode_akun", data.getStringExtra("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
//    private void loadRab(String kode){
//        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_AKUN_BANK+"detail", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject res = null;
//                try {
//                    pd.cancel();
//                    res = new JSONObject(response);
//                    JSONObject data = res.getJSONObject("data");
//                    nama_rekening.setText(data.getString("nama_rekening"));
//                    nama_bank.setText(data.getString("nama_bank"));
//                    no_rekening.setText(data.getString("no_rekening"));
//                    kode_rab = data.getString("kode_rab");
//                    kode_usaha = data.getString("kode_usaha");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    pd.cancel();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        pd.cancel();
//                        Log.d("volley", "errornya : " + error.getMessage());
//                    }
//                }) {
//
//            @Override
//            public Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
//                params.put("kode_akun", data.getStringExtra("kode"));
//                return params;
//            }
//        };
//
//        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
//    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Akun_bank.class));
    }
    private void simpan(){
        final String nm_rekening= nama_rekening.getText().toString().trim();
        final String nm_bank= nama_bank.getText().toString().trim();
        final String no_reke= no_rekening.getText().toString().trim();
        if(nm_rekening.equals("")){
            Toast.makeText(this, "Nama Rekening Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_rekening.setFocusable(true);
        }else if(nm_bank.equals("")){
            Toast.makeText(this, "Nama Bank Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_bank.setFocusable(true);
        }else if(no_reke.equals("")){
            Toast.makeText(this, "No Rekening Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            no_rekening.setFocusable(true);
        }else if(kode_rab.equals("")){
            Toast.makeText(this, "Rab Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(kode_usaha.equals("")){
            Toast.makeText(this, "Usaha Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_AKUN_BANK+"simpanakunbank",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Akun_Bank.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Akun_bank.class));
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
                    params.put("kode_usaha", kode_usaha);
                    params.put("nama_rekening", nm_rekening);
                    params.put("nama_bank", nm_bank);
                    params.put("no_rekening", no_reke);
                    params.put("kode_rab", kode_rab);
                    params.put("tipe_akun", tipe_akun);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
    private void ubah(){
        final String nm_rekening= nama_rekening.getText().toString().trim();
        final String nm_bank= nama_bank.getText().toString().trim();
        final String no_reke= no_rekening.getText().toString().trim();
        final String kode_akun= Temp_Akun_Bank.getInstance(getBaseContext()).getKode_akun();
        if(nm_rekening.equals("")){
            Toast.makeText(this, "Nama Rekening Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_rekening.setFocusable(true);
        }else if(nm_bank.equals("")){
            Toast.makeText(this, "Nama Bank Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_bank.setFocusable(true);
        }else if(no_reke.equals("")){
            Toast.makeText(this, "No Rekening Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            no_rekening.setFocusable(true);
        }else if(kode_rab.equals("")){
            Toast.makeText(this, "Rab Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(kode_usaha.equals("")){
            Toast.makeText(this, "Usaha Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_AKUN_BANK+"updateakunbank",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Akun_Bank.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Akun_bank.class));
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
                    params.put("kode_usaha", kode_usaha);
                    params.put("nama_rekening", nm_rekening);
                    params.put("nama_bank", nm_bank);
                    params.put("no_rekening", no_reke);
                    params.put("kode_rab", kode_rab);
                    params.put("kode_akun", kode_akun);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
