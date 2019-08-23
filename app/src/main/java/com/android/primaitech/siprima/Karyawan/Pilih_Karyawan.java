package com.android.primaitech.siprima.Karyawan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.primaitech.siprima.Akun_Bank.Form_Akun_Bank;
import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Karyawan.Adapter.Adapter_Pilih_Karyawan;
import com.android.primaitech.siprima.Karyawan.Model.Karyawan_Model;
import com.android.primaitech.siprima.Karyawan.Temp.Temp_Karyawan;
import com.android.primaitech.siprima.Proyek.Adapter.Adapter_Pilih_Proyek;
import com.android.primaitech.siprima.Proyek.Model.Proyek_Model;
import com.android.primaitech.siprima.Proyek.Pilih_Proyek;
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

public class Pilih_Karyawan extends AppCompatActivity {
    private Adapter_Pilih_Karyawan adapter;
    private List<Karyawan_Model> list;
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
        setContentView(R.layout.activity_pilih_karyawan);
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        not_found = (LinearLayout)findViewById(R.id.not_found);
        list = new ArrayList<>();
        close = (ImageView)findViewById(R.id.fullscreen_dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuData menuData = new MenuData();
                Intent intent = new Intent(v.getContext(), menuData.halaman_navigasi(Temp_Karyawan.getInstance(v.getContext()).getNama_menu()));
                v.getContext().startActivity(intent);
            }
        });
        adapter = new Adapter_Pilih_Karyawan(Pilih_Karyawan.this,(ArrayList<Karyawan_Model>) list, Pilih_Karyawan.this);
        mManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(Pilih_Karyawan.this);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        loadJson();
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
        String url = "";
        if (Temp_Karyawan.getInstance(getBaseContext()).getTipe().equals("1")){
            url = ServerAccess.URL_KARYAWAN+"unitbisnis";
        }else if(Temp_Karyawan.getInstance(getBaseContext()).getTipe().equals("2")){
            url = ServerAccess.URL_KARYAWAN+"proyek";
        }
        StringRequest senddata = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
                                Karyawan_Model md = new Karyawan_Model();
                                md.setKode_karyawan(data.getString("kode_karyawan"));
                                md.setTipe_karyawan(data.getString("tipe_karyawan"));
                                md.setNama_unit(data.getString("nama_unit"));
                                md.setNama_proyek(data.getString("nama_proyek"));
                                md.setNama_karyawan(data.getString("nama_karyawan"));
                                md.setFoto(ServerAccess.BASE_URL+"/"+data.getString("foto_kecil"));
                                md.setNama_divisi(data.getString("nama_divisi"));
                                md.setTanggal_gabung(data.getString("tgl_gabung"));
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

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
