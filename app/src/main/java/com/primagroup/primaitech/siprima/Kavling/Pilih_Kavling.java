package com.primagroup.primaitech.siprima.Kavling;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.RequestHandler;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Kavling.Adapter.Adapter_Pilih_Kavling;
import com.primagroup.primaitech.siprima.Kavling.Model.Kavling_Model;
import com.primagroup.primaitech.siprima.Penjualan.Form_Data_Kavling;
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

public class Pilih_Kavling extends AppCompatActivity {
    private Adapter_Pilih_Kavling adapter;
    private List<Kavling_Model> list;
    private RecyclerView listdata;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    LinearLayout not_found;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    ImageView close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kavling);
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        not_found = (LinearLayout)findViewById(R.id.not_found);
        list = new ArrayList<>();
        close = (ImageView)findViewById(R.id.fullscreen_dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), Form_Data_Kavling.class));
            }
        });
        adapter = new Adapter_Pilih_Kavling(Pilih_Kavling.this,(ArrayList<Kavling_Model>) list);
        mManager = new LinearLayoutManager(Pilih_Kavling.this,LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(Pilih_Kavling.this);
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
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KAVLING+"datakavling", new Response.Listener<String>() {
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
                                Kavling_Model md = new Kavling_Model();
                                md.setDesain_rumah(ServerAccess.BASE_URL+data.getString("desain_rumah"));
                                md.setKode_kavling(data.getString("kode_kavling"));
                                md.setNama_kavling(data.getString("nama_kavling"));
                                md.setNama_proyek(data.getString("nama_proyek"));
                                md.setNama_kategori(data.getString("nama_kategori"));
                                md.setHarga_jual(ServerAccess.numberConvert(data.getString("harga_jual")));
                                md.setTipe_rumah(data.getString("tipe_rumah"));
                                md.setCreate_at(data.getString("create_at"));
                                md.setStatus(data.getString("status"));
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();

                            }
                        }
                        pd.cancel();
                        adapter.notifyDataSetChanged();
                    }else{
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
                params.put("kodeproyek", AuthData.getInstance(getBaseContext()).getKode_proyek());
                return params;
            }
        };

        RequestHandler.getInstance(Pilih_Kavling.this).addToRequestQueue(senddata);
    }
}
