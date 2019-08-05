package com.android.primaitech.siprima.Kegiatan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Cuti.Detail_Cuti;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Kegiatan.Adapter.Adapter_Galeri_Kegiatan;
import com.android.primaitech.siprima.Kegiatan.Adapter.Adapter_Kegiatan;
import com.android.primaitech.siprima.Kegiatan.Model.Galeri_Kegiatan_Model;
import com.android.primaitech.siprima.Kegiatan.Model.Kegiatan_Model;
import com.android.primaitech.siprima.MainActivity;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Kunjungan_Pembeli;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.github.tntkhang.fullscreenimageview.library.FullScreenImageViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Detail_Kegiatan extends AppCompatActivity {
    ProgressDialog pd;
    TextView nama_unit, nama_proyek, nama_karyawan, status, kendala, solusi, kegiatan, tanggal, tambah;
    ImageView gambar;
    String kode_pembeli = "";
    private Adapter_Galeri_Kegiatan adapter;
    private List<Galeri_Kegiatan_Model> list;
    LinearLayout not_found;
    private RecyclerView listdata;
    Button selesai;
    Context context;
    RecyclerView.LayoutManager mManager;
    private static final String[] IMAGE_PATH_LIST = {
            "https://asset-a.grid.id/crop/0x0:0x0/700x465/photo/2019/01/14/3862055033.png", "https://asset-a.grid.id/crop/0x0:0x0/700x465/photo/2019/01/14/3862055033.png",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Kegiatan.this);
        not_found = (LinearLayout)findViewById(R.id.not_found);
        toolbar.setNavigationIcon(R.drawable.backward);
        selesai = (Button)findViewById(R.id.selesai);
        nama_unit = (TextView)findViewById(R.id.nama_unit);
        tambah = (TextView)findViewById(R.id.tambah);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        nama_karyawan = (TextView)findViewById(R.id.nama_karyawan);
        status = (TextView)findViewById(R.id.status);
        kendala = (TextView)findViewById(R.id.kendala);
        solusi = (TextView)findViewById(R.id.solusi);
        kegiatan = (TextView) findViewById(R.id.kegiatan);
        tanggal = (TextView) findViewById(R.id.tanggal);
        gambar = (ImageView) findViewById(R.id.gambar);
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new Adapter_Galeri_Kegiatan(Detail_Kegiatan.this,(ArrayList<Galeri_Kegiatan_Model>) list, this);
        mManager = new LinearLayoutManager(Detail_Kegiatan.this,LinearLayoutManager.HORIZONTAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        loadJson();
        final Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/android1");
        final Uri uri2 = Uri.parse("android.resource://" + getPackageName() + "/drawable/android3");

        final ArrayList<String> uriString = new ArrayList<>();
        uriString.add(uri.toString());
        uriString.add(uri2.toString());
        uriString.add(uri.toString());
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClickAction(uriString, 0);
            }
        });
        final Intent data = getIntent();
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Ubah_Kegiatan.class);
                intent.putExtra("kode", data.getStringExtra("kode"));
                startActivity(intent);
            }
        });
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tambah_Galeri bt = new Tambah_Galeri();
                Bundle bundle = new Bundle();
                bundle.putString("kode", data.getStringExtra("kode"));
                bt.setArguments(bundle);
                bt.show(getSupportFragmentManager(), "Tanggal");
            }
        });
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
    private void onImageClickAction(ArrayList<String> uriString, int pos) {
        Intent fullImageIntent = new Intent(Detail_Kegiatan.this, FullScreenImageViewActivity.class);
        fullImageIntent.putExtra(FullScreenImageViewActivity.URI_LIST_DATA, uriString);
        fullImageIntent.putExtra(FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS, pos);
        startActivity(fullImageIntent);

    }
    public void previewImage(){
//        Intent fullImageIntent = new Intent(Detail_Kegiatan.this, FullScreenImageViewActivity.class);
//        fullImageIntent.putExtra(FullScreenImageViewActivity.URI_LIST_DATA, uriString);
//        fullImageIntent.putExtra(FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS, 0);
//        startActivity(fullImageIntent);
//        ArrayList<String> imageList = new ArrayList<>();
//        Collections.addAll(imageList, IMAGE_PATH_LIST);
//
//        Album.gallery(this)
//                .checkedList(imageList)
//                .checkable(true)
//                .widget(
//                        Widget.newDarkBuilder(this)
//                                .title("Galeri")
//                                .build()
//                )
//                .onResult(new Action<ArrayList<String>>() {
//                    @Override
//                    public void onAction(@NonNull ArrayList<String> result) {
//                        // TODO If it is optional, here you can accept the results of user selection.
//                    }
//                })
//                .start();
    }
    private void loadKegiatan(){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KEGIATAN+"detailkegiatan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    if(res.getString("datagaleri") != "null") {
                        JSONArray arr = res.getJSONArray("datagaleri");
                        if(arr.length() > 0) {
                            for (int i = 0; i < arr.length(); i++) {
                                try {
                                    JSONObject data = arr.getJSONObject(i);
                                    Galeri_Kegiatan_Model md = new Galeri_Kegiatan_Model();
                                    md.setKode_galery(data.getString("kode_galeri"));
                                    md.setFoto_kecil(ServerAccess.BASE_URL+""+data.getString("foto_kecil"));
                                    md.setFoto(ServerAccess.BASE_URL+""+data.getString("foto"));
                                    Log.d("pesan", data.getString("keterangan"));
                                    md.setTanggal(ServerAccess.parseDate(data.getString("create_at")));
                                    md.setKeterangan(data.getString("keterangan"));
                                    list.add(md);
                                } catch (Exception ea) {
                                    ea.printStackTrace();
                                    Log.d("pesan", "error "+ ea.getMessage());
                                }
                            }
                            pd.cancel();
                            adapter.notifyDataSetChanged();
                        }else{
                            pd.cancel();
                            not_found.setVisibility(View.VISIBLE);
                        }
                    }else{
                        pd.cancel();
                        not_found.setVisibility(View.VISIBLE);
                    }
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
                params.put("kodekegiatan", data.getStringExtra("kode"));
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    public void reload(){
        loadJson();
    }

    public void delete(final String kode){

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KEGIATAN+"deletegalerikegiatan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject respon = res.getJSONObject("respon");
                    if(respon.getBoolean("status")){
                        Toast.makeText(Detail_Kegiatan.this, respon.getString("pesan"), Toast.LENGTH_SHORT).show();
                        reload();
                    }else{
                        Toast.makeText(Detail_Kegiatan.this, respon.getString("pesan"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("pesan", "error "+e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("kodegaleri", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    private void loadJson()
    {

        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KEGIATAN+"detailkegiatan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
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
                    Glide.with(getBaseContext())
                            .load(data.getString("foto"))
                            .into(gambar);
                    loadKegiatan();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
