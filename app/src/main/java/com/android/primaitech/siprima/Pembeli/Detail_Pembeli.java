package com.android.primaitech.siprima.Pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.primaitech.siprima.Akun_Bank.Akun_bank;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.Pembeli.Model.Pembeli_Model;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Detail_Pembeli extends AppCompatActivity {

    ProgressDialog pd;
    TextView nama_pembeli, no_ktp, no_hp, alamat, email, ttl;
    Button follow_up;
    String kode_pembeli = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Pembeli.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        nama_pembeli = (TextView)findViewById(R.id.nama_pembeli);
        no_ktp = (TextView)findViewById(R.id.no_ktp);
        email = (TextView)findViewById(R.id.email);
        ttl = (TextView)findViewById(R.id.ttl);
        no_hp = (TextView)findViewById(R.id.no_hp);
        alamat = (TextView)findViewById(R.id.alamat);
        follow_up = (Button)findViewById(R.id.follow_up);
        loadJson();
        follow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Kunjungan_Pembeli.class);
                intent.putExtra("nama_pembeli", nama_pembeli.getText().toString());
                intent.putExtra("kode_pembeli", kode_pembeli);
                startActivity(intent);
            }
        });
        Intent data = getIntent();
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Detail Pembeli " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Pembeli.class));
                Detail_Pembeli.this.onBackPressed();
            }
        });
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
                    no_ktp.setText(data.getString("no_ktp"));
                    no_hp.setText(data.getString("no_hp"));
                    alamat.setText(data.getString("alamat_pembeli"));
                    email.setText(data.getString("email"));
                    kode_pembeli = data.getString("kode_pembeli");
                    ttl.setText(data.getString("tempat_lahir")+", "+ServerAccess.parseDate(data.getString("tgl_lahir")));
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
                params.put("kodepembeli", data.getStringExtra("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
