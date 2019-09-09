package com.primagroup.primaitech.siprima.Dashboard;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.primagroup.primaitech.siprima.Config.AppController;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.MenuData;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Dashboard.Adapter.AdapterMenu;
import com.primagroup.primaitech.siprima.Dashboard.Model.MenuModel;
import com.primagroup.primaitech.siprima.Dashboard.Temp.Temp_Menu;
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

public class Detail_Menu extends AppCompatActivity {
    private AdapterMenu adapter;
    private List<MenuModel> list;
    private RecyclerView listdata;
    GridLayoutManager mManager;
    ProgressDialog pd;
    LinearLayout not_found;
    SwipeRefreshLayout swLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        toolbar.setTitle("Detail Menu "+ Temp_Menu.getInstance(getBaseContext()).getMenu());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        not_found = (LinearLayout)findViewById(R.id.not_found);
        list = new ArrayList<>();
        adapter = new AdapterMenu(Detail_Menu.this,(ArrayList<MenuModel>) list, this);
        mManager = new GridLayoutManager(Detail_Menu.this, 4);
        mManager.setOrientation(GridLayoutManager.VERTICAL);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(Detail_Menu.this);
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
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.SubMenu, new Response.Listener<String>() {
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
                                MenuModel md = new MenuModel();
                                md.setKode_menu(data.getString("kode_menu").toLowerCase());
                                md.setJudul(data.getString("nama_menu"));
                                md.setLink(data.getString("link"));
                                int id = getResources().getIdentifier(data.getString("kode_menu").toLowerCase(), "drawable", getPackageName());
                                md.setGambar(id);
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();

                            }
                        }
                        pd.cancel();
                        adapter.notifyDataSetChanged();
                    }else{
                        pd.cancel();
                        MenuData menuData = new MenuData();
                        Intent data = getIntent();
                        Log.d("pesan", "kode menunya adalah "+data.getStringExtra("kode_menu"));
                        Log.d("pesan", "nama menunya adalah "+data.getStringExtra("nama_menu"));
                        Intent intent = new Intent(getBaseContext(), menuData.halaman(data.getStringExtra("kode_menu")));
                        AuthData.getInstance(getBaseContext()).setKodeMenu(data.getStringExtra("kode_menu"));
                        AuthData.getInstance(getBaseContext()).setNamaMenu(data.getStringExtra("nama_menu"));
                        intent.putExtra("kode_menu", data.getStringExtra("kode_menu"));
                        intent.putExtra("nama_menu",data.getStringExtra("nama_menu"));
                        startActivity(intent);
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
                params.put("kode_role", AuthData.getInstance(getBaseContext()).getKode_role());
                params.put("menu", Temp_Menu.getInstance(getBaseContext()).getKode_menu());
                params.put("show", "user");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    public void checkData(final String kode){
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.Menu+"/checkmenu", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    Temp_Menu.getInstance(getBaseContext()).setJumlah("0");
                    JSONObject data = res.getJSONObject("data");
                    Temp_Menu.getInstance(getBaseContext()).setJumlah(data.getString("jumlah"));
                    Log.d("pesan", "jumlahnya adalah "+data.getInt("jumlah"));

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
                params.put("kode_role", AuthData.getInstance(getBaseContext()).getKode_role());
                params.put("menu", kode);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(senddata);
    }

}
