package com.android.primaitech.siprima.Kegiatan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Kunjungan_Pembeli;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Detail_Kegiatan extends AppCompatActivity {
    ProgressDialog pd;
    TextView nama_unit, nama_proyek, nama_karyawan, status, kendala, solusi, kegiatan, tanggal;
    ImageView gambar;
    String kode_pembeli = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Kegiatan.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        nama_unit = (TextView)findViewById(R.id.nama_unit);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        nama_karyawan = (TextView)findViewById(R.id.nama_karyawan);
        status = (TextView)findViewById(R.id.status);
        kendala = (TextView)findViewById(R.id.kendala);
        solusi = (TextView)findViewById(R.id.solusi);
        kegiatan = (TextView) findViewById(R.id.kegiatan);
        tanggal = (TextView) findViewById(R.id.tanggal);
        gambar = (ImageView) findViewById(R.id.gambar);
        loadJson();
        Intent data = getIntent();
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Detail Kegiatan " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Pembeli.class));
                Detail_Kegiatan.this.onBackPressed();
            }
        });
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KEGIATAN+"detailkegiatan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    kode_pembeli = data.getString("kode_kegiatan");
                    nama_unit.setText(data.getString("nama_unit"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    nama_karyawan.setText(data.getString("nama_karyawan"));
                    status.setText(data.getString("status"));
                    kendala.setText(data.getString("kendala"));
                    solusi.setText(data.getString("solusi"));
                    kegiatan.setText(data.getString("kegiatan"));
                    tanggal.setText(ServerAccess.parseDate(data.getString("create_at")));
//                    Glide.with(getBaseContext())
//                            .load(data.getString("foto"))
//                            .into(gambar);
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
                params.put("kodekegiatan", data.getStringExtra("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
