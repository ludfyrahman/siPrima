package com.android.primaitech.siprima.Pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Tambah_Pembeli extends AppCompatActivity {
    EditText nama_pembeli, nik, no_hp, email, alamat_pembeli;
    Button simpan;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembeli);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Tambah_Pembeli.this.onBackPressed();
            }
        });
        pd = new ProgressDialog(Tambah_Pembeli.this);
        nama_pembeli = (EditText)findViewById(R.id.nama_pembeli);
        nik = (EditText)findViewById(R.id.nik);
        no_hp = (EditText)findViewById(R.id.no_hp);
        email = (EditText)findViewById(R.id.email);
        alamat_pembeli = (EditText)findViewById(R.id.alamat_pembeli);
        simpan = (Button) findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
    }
    private void simpan(){
        final String nama= nama_pembeli.getText().toString().trim();
        final String no_ktp= nik.getText().toString().trim();
        final String hp= no_hp.getText().toString().trim();
        final String mail= email.getText().toString().trim();
        final String alamat= alamat_pembeli.getText().toString().trim();
        if(nama.equals("")){
            Toast.makeText(this, "Nama Pembeli Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_pembeli.setFocusable(true);
        }else if(no_ktp.equals("")){
            Toast.makeText(this, "No Ktp Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nik.setFocusable(true);
        }else if(hp.equals("")){
            Toast.makeText(this, "No Hp Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            no_hp.setFocusable(true);
        }else if(mail.equals("")){
            Toast.makeText(this, "Email Pembeli Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_pembeli.setFocusable(true);
        }else if(alamat.equals("")){
            Toast.makeText(this, "Alamat Pembeli Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            alamat_pembeli.setFocusable(true);
        }else{
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_PEMBELI+"tambahpembeli",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Toast.makeText(
                                            Tambah_Pembeli.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    Tambah_Pembeli.this.onBackPressed();
//                                    startActivity(new Intent(Tambah_Follow_Up.this, Dashboard.class));

                                } else {
                                    Toast.makeText(
                                            Tambah_Pembeli.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        Tambah_Pembeli.this,
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
                                    Tambah_Pembeli.this,
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(Tambah_Pembeli.this).getAuthKey());
                    params.put("nama_pembeli", nama);
                    params.put("no_ktp", no_ktp);
                    params.put("no_hp", hp);
                    params.put("email", mail);
                    params.put("kode_karyawan", AuthData.getInstance(getBaseContext()).getAksesData());
                    params.put("alamat_pembeli", alamat);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
