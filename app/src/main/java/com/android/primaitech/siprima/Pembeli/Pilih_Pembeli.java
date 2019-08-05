package com.android.primaitech.siprima.Pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Divisi.Adapter.Adapter_Divisi;
import com.android.primaitech.siprima.Divisi.Model.Divisi_Model;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.Pembeli.Adapter.Adapter_Pilih_Pembeli;
import com.android.primaitech.siprima.Pembeli.Model.Pembeli_Model;
import com.android.primaitech.siprima.Penjualan.Tambah_Penjualan;
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

public class Pilih_Pembeli extends AppCompatActivity {
    private Adapter_Pilih_Pembeli adapter;
    private List<Pembeli_Model> list;
    private RecyclerView listdata;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    LinearLayout not_found;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    ImageView close;
    public static String code="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_pembeli);
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        not_found = (LinearLayout)findViewById(R.id.not_found);
        list = new ArrayList<>();
        close = (ImageView)findViewById(R.id.fullscreen_dialog_close);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startActivity(new Intent(getBaseContext(), Tambah_Penjualan.class));
//                Pilih_Pembeli.this.onBackPressed();
//            }
//        });
        Intent data = getIntent();
        if(data.hasExtra("code")) {
            code = data.getStringExtra("code");
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(code.equals("1")){
                        startActivity(new Intent(getBaseContext(), Tambah_Penjualan.class));
                    }else{
                        startActivity(new Intent(getBaseContext(), Tambah_Follow_Up.class));
                    }
//                    Pilih_Pembeli.this.onBackPressed();
                }
            });
        }
        adapter = new Adapter_Pilih_Pembeli(Pilih_Pembeli.this,(ArrayList<Pembeli_Model>) list);
        mManager = new LinearLayoutManager(Pilih_Pembeli.this,LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(Pilih_Pembeli.this);
        loadJson();
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
    }

    public void reload(){
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PEMBELI+"calonpembeli", new Response.Listener<String>() {
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
                                Pembeli_Model md = new Pembeli_Model();
                                md.setKode_pembeli(data.getString("kode_pembeli"));
                                md.setNama_pembeli(data.getString("nama_pembeli"));
                                md.setNo_hp(data.getString("no_hp"));
                                md.setNo_ktp(data.getString("no_ktp"));
                                md.setStatus(data.getInt("status"));
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
                return params;
            }
        };

        RequestHandler.getInstance(Pilih_Pembeli.this).addToRequestQueue(senddata);
    }
}
