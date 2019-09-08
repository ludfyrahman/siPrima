package com.android.primaitech.siprima.Tipe_Rumah;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Pembeli.Temp.Temp_Pembeli;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.Tipe_Rumah.Temp.Temp_Tipe_Rumah;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Form_Tipe_Rumah extends AppCompatActivity {
    EditText nama_pembeli, nik, no_hp, email, alamat_pembeli;
    Button simpan;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tipe_rumah);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Form_Tipe_Rumah.this.onBackPressed();
            }
        });
        pd = new ProgressDialog(Form_Tipe_Rumah.this);
//        nama_pembeli = (EditText)findViewById(R.id.nama_pembeli);
//        nama_pembeli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                Temp_Pembeli.getInstance(getBaseContext()).setNamaPembeli(nama_pembeli.getText().toString());
//            }
//        });
//        nik = (EditText)findViewById(R.id.nik);
//        nik.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                Temp_Pembeli.getInstance(getBaseContext()).setNik(nik.getText().toString());
//            }
//        });
//        no_hp = (EditText)findViewById(R.id.no_hp);
//        no_hp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                Temp_Pembeli.getInstance(getBaseContext()).setNoHp(no_hp.getText().toString());
//            }
//        });
//        email = (EditText)findViewById(R.id.email);
//        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                Temp_Pembeli.getInstance(getBaseContext()).setEmail(email.getText().toString());
//            }
//        });
//        alamat_pembeli = (EditText)findViewById(R.id.alamat_pembeli);
//        alamat_pembeli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                Temp_Pembeli.getInstance(getBaseContext()).setAlamatPembeli(alamat_pembeli.getText().toString());
//            }
//        });
        simpan = (Button) findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Temp_Tipe_Rumah.getInstance(getBaseContext()).getTipe_form().equals("edit")){
                    ubah();
                }else{
                    simpan();
                }
            }
        });
        if (Temp_Tipe_Rumah.getInstance(getBaseContext()).getTipe_form().equals("edit")){
            loadJson();
            toolbar.setTitle("Ubah Tipe Rumah");
        }else{
            toolbar.setTitle("Tambah Tipe Rumah");
        }
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PEMBELI+"detailpembeli", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_pembeli.setText(data.getString("nama_pembeli"));
                    Temp_Pembeli.getInstance(getBaseContext()).setNamaPembeli(data.getString("nama_pembeli"));
                    nik.setText(data.getString("no_ktp"));
                    Temp_Pembeli.getInstance(getBaseContext()).setNik(data.getString("no_ktp"));
                    no_hp.setText(data.getString("no_hp"));
                    Temp_Pembeli.getInstance(getBaseContext()).setNoHp(data.getString("no_hp"));
                    email.setText(data.getString("email"));
                    Temp_Pembeli.getInstance(getBaseContext()).setEmail(data.getString("email"));
                    alamat_pembeli.setText(data.getString("alamat_pembeli"));
                    Temp_Pembeli.getInstance(getBaseContext()).setAlamatPembeli(data.getString("alamat_pembeli"));
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
                params.put("kodepembeli",Temp_Pembeli.getInstance(getBaseContext()).getKode_pembeli());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
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
                                            Form_Tipe_Rumah.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(Form_Tipe_Rumah.this, Tipe_Rumah.class));
                                } else {
                                    Toast.makeText(
                                            Form_Tipe_Rumah.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        Form_Tipe_Rumah.this,
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
                                    Form_Tipe_Rumah.this,
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(Form_Tipe_Rumah.this).getAuthKey());
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
    private void ubah(){
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
                    ServerAccess.URL_PEMBELI+"updatecalonpembeli",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Toast.makeText(
                                            Form_Tipe_Rumah.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(Form_Tipe_Rumah.this, Tipe_Rumah.class));
                                } else {
                                    Toast.makeText(
                                            Form_Tipe_Rumah.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        Form_Tipe_Rumah.this,
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
                                    Form_Tipe_Rumah.this,
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(Form_Tipe_Rumah.this).getAuthKey());
                    params.put("nama_pembeli", nama);
                    params.put("no_ktp", no_ktp);
                    params.put("no_hp", hp);
                    params.put("email", mail);
                    params.put("kode_karyawan", AuthData.getInstance(getBaseContext()).getAksesData());
                    params.put("alamat_pembeli", alamat);
                    params.put("kode_pembeli", Temp_Pembeli.getInstance(getBaseContext()).getKode_pembeli());
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
