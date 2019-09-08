package com.android.primaitech.siprima.Follow_Up;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
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

import java.util.HashMap;
import java.util.Map;

public class Detail_Follow_Up extends AppCompatActivity {
    ProgressDialog pd;
    LinearLayout bg;
    Button follow_up;
    String kode_kunjungan = "";
    TextView alamat_temu, tanggal_pertemuan, tanggal_selesai, pembahasan, status, prospek, nama_karyawan, kendala, nama_pembeli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_follow_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Follow_Up.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        alamat_temu = (TextView)findViewById(R.id.alamat_temu);
        tanggal_pertemuan = (TextView)findViewById(R.id.tanggal_pertemuan);
        tanggal_selesai = (TextView)findViewById(R.id.tanggal_selesai);
        pembahasan = (TextView)findViewById(R.id.pembahasan);
        prospek = (TextView)findViewById(R.id.prospek);
        nama_karyawan = (TextView)findViewById(R.id.nama_karyawan);
        kendala = (TextView)findViewById(R.id.kendala);
        status = (TextView)findViewById(R.id.status);
        nama_pembeli = (TextView)findViewById(R.id.nama_pembeli);
        follow_up = (Button)findViewById(R.id.follow_up);
        loadJson();
        follow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Form_Follow_Up.class);
                intent.putExtra("nama_pembeli", nama_pembeli.getText().toString());
                intent.putExtra("kode_kunjungan", kode_kunjungan);
                startActivity(intent);
            }
        });
        bg = (LinearLayout)findViewById(R.id.bg);
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Detail " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Kavling.class));
                Detail_Follow_Up.this.onBackPressed();
            }
        });
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_pembeli.setText(data.getString("nama_pembeli"));
                    nama_karyawan.setText(data.getString("nama_karyawan"));
                    kode_kunjungan = data.getString("kode_kunjungan");
                    tanggal_pertemuan.setText(ServerAccess.parseDate(data.getString("tanggal_pertemuan")));
                    tanggal_selesai.setText(ServerAccess.parseDate(data.getString("tanggal_selesai")));
                    pembahasan.setText(data.getString("pembahasan"));
                    kendala.setText(data.getString("kendala"));
                    prospek.setText(ServerAccess.prospek[data.getInt("prospek")]);
                    alamat_temu.setText(data.getString("alamat_temu"));
                    if(data.getString("status").equals("1")){
                        follow_up.setVisibility(View.GONE);
                    }
                    if (!data.getString("foto").equals("")){
                        Glide.with(Detail_Follow_Up.this)
                                .load(ServerAccess.BASE_URL+"/"+data.getString("foto"))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            bg.setBackground(resource);
                                        }
                                    }
                                });
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
                params.put("tipedata", "detailKunjungan");
                params.put("kode_kunjungan", data.getStringExtra("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
