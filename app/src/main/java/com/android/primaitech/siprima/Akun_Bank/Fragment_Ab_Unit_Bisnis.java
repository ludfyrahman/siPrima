package com.android.primaitech.siprima.Akun_Bank;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.primaitech.siprima.Akun_Bank.Adapter.Adapter_Akun_Bank;
import com.android.primaitech.siprima.Akun_Bank.Model.Akun_Bank_Model;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Adapter.AdapterMenu;
import com.android.primaitech.siprima.Dashboard.Model.MenuModel;
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

public class Fragment_Ab_Unit_Bisnis extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_ab_unit_bisnis, container, false);
        listdata = (RecyclerView)v.findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        tambah = (FloatingActionButton)v.findViewById(R.id.tambah);
        not_found = (LinearLayout)v.findViewById(R.id.not_found);
        list = new ArrayList<>();
        adapter = new Adapter_Akun_Bank(getActivity(),(ArrayList<Akun_Bank_Model>) list);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(getActivity());
        loadJson();
        refresh = (FrameLayout) v.findViewById(R.id.refresh);
        swLayout = (SwipeRefreshLayout) v.findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        validate();
        Log.d("pesan", "fragment edit "+edit+" hapus "+hapus+" detail "+detail);
        return v;
    }
    public void reload(){
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
    private void validate(){
        Bundle bundle = getArguments();
        if(bundle.getString("buat").equals("1"))
            tambah.show();
        buat = bundle.getString("buat");
        edit = bundle.getString("edit");
        hapus = bundle.getString("hapus");
        detail = bundle.getString("detail");
        kode_menu = bundle.getString("kode_menu");

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
                                        Akun_Bank_Model md = new Akun_Bank_Model();
                                        md.setKode_akunbank(data.getString("kode_akunbank"));
                                        md.setNama_rekening(data.getString("nama_rekening"));
                                        md.setNama_unit(data.getString("nama_unit"));
                                        md.setNama_bank(data.getString("nama_bank"));
                                        md.setNo_rekening(data.getString("no_rekening"));
                                        md.setSaldo("Rp "+ServerAccess.numberFormat(data.getInt("saldo")));
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
                params.put("kode", "2");
                params.put("tipedata", "akunbank");
                params.put("tipe_akun", "1");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
    }

}
