package com.primagroup.primaitech.siprima.Akun_Bank;

import android.app.ProgressDialog;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.primagroup.primaitech.siprima.Akun_Bank.Adapter.Adapter_Akun_Bank;
import com.primagroup.primaitech.siprima.Akun_Bank.Model.Akun_Bank_Model;
import com.primagroup.primaitech.siprima.Akun_Bank.Temp.Temp_Akun_Bank;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.RequestHandler;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Dashboard.Dashboard;
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

public class Fragment_Ab_Unit_Bisnis extends AppCompatActivity {
    FloatingActionButton tambah;
    public static String buat, edit, hapus, detail;
    private Adapter_Akun_Bank adapter;
    private List<Akun_Bank_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    LinearLayout not_found;
    ProgressDialog pd;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_ab_unit_bisnis);
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
        adapter = new Adapter_Akun_Bank(Fragment_Ab_Unit_Bisnis.this,(ArrayList<Akun_Bank_Model>) list, this);
        mManager = new LinearLayoutManager(Fragment_Ab_Unit_Bisnis.this,LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(Fragment_Ab_Unit_Bisnis.this);
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
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Akun_bank m = new Akun_bank();
                m.tipe_akun="1";
                Intent intent = new Intent(getBaseContext(), Form_Akun_Bank.class);
                Temp_Akun_Bank.getInstance(getBaseContext()).setTipeForm("add");
                startActivity(intent);
            }
        });
//        return v;
    }
    public void reload(){
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }
//    @Override
//    public void onAttach(Context context){
//        super.onAttach(context);
//    }
    private void validate(){
//        Bundle bundle = getArguments();
//        if(bundle.getString("buat").equals("1"))
//            tambah.show();
//        buat = bundle.getString("buat");
//        edit = bundle.getString("edit");
//        hapus = bundle.getString("hapus");
//        detail = bundle.getString("detail");
//        kode_menu = bundle.getString("kode_menu");
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();

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
                params.put("tipedata", "subMenuAkses");
                params.put("kode_menu", kode_menu);
                params.put("kode_role", AuthData.getInstance(getBaseContext()).getKode_role());
                return params;
            }
        };

        RequestHandler.getInstance(Fragment_Ab_Unit_Bisnis.this).addToRequestQueue(senddata);

    }

    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_AKUN_BANK+"data", new Response.Listener<String>() {
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
                                        Akun_Bank_Model md = new Akun_Bank_Model();
                                        md.setKode_akunbank(data.getString("kode_akunbank"));
                                        md.setNama_rekening(data.getString("nama_rekening"));
                                        md.setNama_unit(data.getString("nama_unit"));
                                        md.setNama_bank(data.getString("nama_bank"));
                                        md.setNo_rekening(data.getString("no_rekening"));
                                        md.setSaldo(ServerAccess.numberConvert(data.getString("saldo")));
                                        md.setTipe_akun(data.getString("tipe_akun"));
                                        list.add(md);
                                    } catch (Exception ea) {
                                        ea.printStackTrace();

                                    }
                                }

                                adapter.notifyDataSetChanged();
                                pd.cancel();
                            }else{
                                not_found.setVisibility(View.VISIBLE);
                                pd.cancel();
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
                        Log.d("volley", "errornya : " + error.getMessage());
                        pd.cancel();
                    }
                }) {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("tipe", "1");
                params.put("kode_usaha", AuthData.getInstance(getBaseContext()).getKode_unit());
                return params;
            }
        };

        RequestHandler.getInstance(Fragment_Ab_Unit_Bisnis.this).addToRequestQueue(senddata);
    }

}
