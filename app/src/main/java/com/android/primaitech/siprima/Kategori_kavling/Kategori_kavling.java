package com.android.primaitech.siprima.Kategori_kavling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Kategori_kavling.Adapter.Adapter_Kategori_Kavling;
import com.android.primaitech.siprima.Kategori_kavling.Model.Kategori_Kavling_Model;
import com.android.primaitech.siprima.Pembeli.Kunjungan_Pembeli;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.Proyek.Adapter.Adapter_Proyek;
import com.android.primaitech.siprima.Proyek.Model.Proyek_Model;
import com.android.primaitech.siprima.Proyek.Proyek;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kategori_kavling extends AppCompatActivity {
    public static String buat, edit, hapus, detail;
    FloatingActionButton tambah;
    private Adapter_Kategori_Kavling adapter;
    private List<Kategori_Kavling_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    LinearLayout not_found;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_kavling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        toolbar.setTitle(AuthData.getInstance(getBaseContext()).getNama_menu());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        tambah = (FloatingActionButton)findViewById(R.id.tambah);
        not_found = (LinearLayout)findViewById(R.id.not_found);
        list = new ArrayList<>();
        adapter = new Adapter_Kategori_Kavling(Kategori_kavling.this,(ArrayList<Kategori_Kavling_Model>) list, this);
        mManager = new LinearLayoutManager(Kategori_kavling.this,LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(Kategori_kavling.this);
        loadJson();
        refresh = (FrameLayout) findViewById(R.id.refresh);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        validate();
    }
    @Override
    public void onBackPressed() {
//        spv_dev_list_komplain.this.finish();
        startActivity(new Intent(getBaseContext(), Dashboard.class));
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("pesan ", "Onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("pesan", "on Pause");
    }

    public void reload(){
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }
    private void validate(){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        Intent data = getIntent();

        final String kode_menu = AuthData.getInstance(getBaseContext()).getKode_menu();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
                    if(arr.length() > 0) {
                        try {
                            JSONObject data = arr.getJSONObject(0);
                            buat = data.getString("buat");
                            edit = data.getString("edit");
                            hapus = data.getString("hapus");
                            detail = data.getString("detail");
                            if(data.getString("buat").equals("1"))
                                tambah.show();
                        } catch (Exception ea) {
                            ea.printStackTrace();

                        }
                    }else{
                        pd.cancel();
                        Log.d("erro", "onResponse: kosong");
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
                params.put("tipedata", "menuAkses");
                params.put("kode_menu", kode_menu);
                params.put("kode_role", AuthData.getInstance(getBaseContext()).getKode_role());
                return params;
            }
        };

        RequestHandler.getInstance(Kategori_kavling.this).addToRequestQueue(senddata);
    }
    public  void delete(final String kode){

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject data = obj.getJSONObject("respon");
                    if (data.getBoolean("status")) {
                        Toast.makeText(
                                Kategori_kavling.this,
                                data.getString("pesan"),
                                Toast.LENGTH_LONG
                        ).show();
                        startActivity(new Intent(Kategori_kavling.this, Kategori_kavling.class));
                    } else {
                        Toast.makeText(
                                Kategori_kavling.this,
                                data.getString("pesan"),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                } catch (JSONException e) {

                    Toast.makeText(
                            Kategori_kavling.this,
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
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kode", kode);
                params.put("tipedata", "kategori_kavling");
                params.put("tipe_akun", "1");
                return params;
            }
        };
        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
//        startActivity(new Intent(getBaseContext(), Akun_bank.class));
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
                    if(arr.length() > 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
                                Kategori_Kavling_Model md = new Kategori_Kavling_Model();
                                md.setKode_kategori(data.getString("kode_kategori"));
                                md.setNama_karyawan(data.getString("nama_karyawan"));
                                md.setNama_kategori(data.getString("nama_kategori"));
                                md.setNama_proyek(data.getString("nama_proyek"));
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();

                            }
                        }
                        pd.cancel();
                        adapter.notifyDataSetChanged();
                    }else{
                        pd.cancel();
                        not_found.setVisibility(View.VISIBLE);
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
                params.put("tipedata", "kategori_kavling");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}
