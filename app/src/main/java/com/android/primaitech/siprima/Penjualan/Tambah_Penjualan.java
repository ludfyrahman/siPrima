package com.android.primaitech.siprima.Penjualan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Pilih_Pembeli;
import com.android.primaitech.siprima.Penjualan.Temp.Temp_Penjualan;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.githang.stepview.StepView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tambah_Penjualan extends AppCompatActivity {
    StepView mStepView;
    Button next;
    ProgressDialog pd;
    TextView nama_pembeli_text;
    EditText nama_pembeli, nik, no_hp, email, alamat_pembeli, instansi_kerja, no_telp_instansi, alamat_instansi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penjualan);
        mStepView = (StepView) findViewById(R.id.step_view);
        List<String> steps = Arrays.asList(new String[]{"Data Pembeli", "Data Kavling", "Pembayaran", "Konfirmasi"});
        mStepView.setSteps(steps);
        pd = new ProgressDialog(Tambah_Penjualan.this);
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
        nama_pembeli_text = (TextView)findViewById(R.id.nama_pembeli_text);
        nama_pembeli = (EditText)findViewById(R.id.nama_pembeli);
        nik = (EditText)findViewById(R.id.nik);
        no_hp = (EditText)findViewById(R.id.no_hp);
        email = (EditText)findViewById(R.id.email);
        alamat_pembeli = (EditText)findViewById(R.id.alamat_pembeli);
        instansi_kerja = (EditText)findViewById(R.id.instansi_kerja);
        no_telp_instansi = (EditText)findViewById(R.id.no_telp_instansi);
        alamat_instansi = (EditText)findViewById(R.id.alamat_instansi);
        nama_pembeli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setNama_pembeli(nama_pembeli.getText().toString());
            }
        });
        nik.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setNik(nik.getText().toString());
                Log.d("pesan", "data "+nik.getText().toString());
            }
        });
        no_hp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setNo_hp(no_hp.getText().toString());
                Log.d("pesan", "data "+no_hp.getText().toString());
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setEmail(email.getText().toString());
                Log.d("pesan", "data "+email.getText().toString());
            }
        });
        alamat_pembeli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setAlamat_pembeli(alamat_pembeli.getText().toString());
                Log.d("pesan", "data "+alamat_pembeli.getText().toString());
            }
        });
        instansi_kerja.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setInstansi_kerja(instansi_kerja.getText().toString());
                Log.d("pesan", "data "+instansi_kerja.getText().toString());
            }
        });
        no_telp_instansi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setNo_telp_instansi(no_telp_instansi.getText().toString());
                Log.d("pesan", "data "+no_telp_instansi.getText().toString());
            }
        });
        alamat_instansi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setAlamat_instansi(alamat_instansi.getText().toString());
                Log.d("pesan", "data "+alamat_instansi.getText().toString());
            }
        });

        nama_pembeli_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Pilih_Pembeli.class);
                intent.putExtra("code", "1");
                startActivity(intent);
//                startActivity(new Intent(Tambah_Penjualan.this, Pilih_Pembeli.class));
            }
        });
        Intent data = getIntent();
        if(!data.hasExtra("kode_pembeli")){
            Log.d("pesan", "kode pembeli kosong");
        }else{
            nama_pembeli_text.setText(data.getStringExtra("nama_pembeli"));
            Temp_Penjualan.getInstance(getBaseContext()).setKode_pembeli(data.getStringExtra("kode_pembeli"));
            loadJson(data.getStringExtra("kode_pembeli"));
        }
        loadJson(Temp_Penjualan.getInstance(getBaseContext()).getKode_pembeli());
    }
    private void simpan(){
        Intent data = getIntent();
        if(instansi_kerja.getText().toString().equals("")){
            Toast.makeText(this, "Instansi Kerja Harus Diisi", Toast.LENGTH_SHORT).show();
        }else if(Temp_Penjualan.getInstance(getBaseContext()).getKode_pembeli().equals("")){
            Toast.makeText(this, "Nama Pembeli Belum Dipilih", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(new Intent(getBaseContext(), Form_Data_Kavling.class));
        }
    }
    public void onBackPressed() {
        startActivity(new Intent(Tambah_Penjualan.this, Penjualan.class));
    }
    private void loadJson(final String kode_pembeli){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PEMBELI+"detailpembeli", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_pembeli.setText(data.getString("nama_pembeli"));
                    Temp_Penjualan.getInstance(getBaseContext()).setNama_pembeli(data.getString("nama_pembeli"));
                    nik.setText(data.getString("no_ktp"));
                    Temp_Penjualan.getInstance(getBaseContext()).setNik(data.getString("no_ktp"));
                    no_hp.setText(data.getString("no_hp"));
                    Temp_Penjualan.getInstance(getBaseContext()).setNo_hp(data.getString("no_hp"));
                    alamat_pembeli.setText(data.getString("alamat_pembeli"));
                    Temp_Penjualan.getInstance(getBaseContext()).setAlamat_pembeli(data.getString("alamat_pembeli"));
                    email.setText(data.getString("email"));
                    Temp_Penjualan.getInstance(getBaseContext()).setEmail(data.getString("email"));
                    instansi_kerja.setText(data.getString("instansi_kerja"));
                    Temp_Penjualan.getInstance(getBaseContext()).setInstansi_kerja(data.getString("instansi_kerja"));
                    no_telp_instansi.setText(data.getString("no_telp_instansi"));
                    Temp_Penjualan.getInstance(getBaseContext()).setNo_telp_instansi(data.getString("no_telp_instansi"));
                    alamat_instansi.setText(data.getString("alamat_instansi"));
                    Temp_Penjualan.getInstance(getBaseContext()).setAlamat_instansi(data.getString("alamat_instansi"));
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
                params.put("kodepembeli", kode_pembeli);
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }

}
