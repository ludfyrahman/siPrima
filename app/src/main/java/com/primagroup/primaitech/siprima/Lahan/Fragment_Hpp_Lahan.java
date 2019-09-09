package com.primagroup.primaitech.siprima.Lahan;

import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Config.AppController;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Hpp.Adapter.Adapter_Hpp_Lahan;
import com.primagroup.primaitech.siprima.Hpp.Model.Hpp_Lahan_Model;
import com.primagroup.primaitech.siprima.Proyek.Detail_Proyek;
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

public class Fragment_Hpp_Lahan extends Fragment {
    public static String buat, edit, hapus, detail;
    FloatingActionButton tambah;
    private Adapter_Hpp_Lahan adapter;
    private List<Hpp_Lahan_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    LinearLayout not_found;
    TextView hpp_lahan_kotor,hpp_lahan_efektif;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    ProgressDialog pd;
    Detail_Proyek detail_proyek = new Detail_Proyek();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_fragment_hpp_lahan, container, false);
        listdata = (RecyclerView) v.findViewById(R.id.listdata);
        hpp_lahan_kotor = (TextView)v.findViewById(R.id.hpp_lahan_kotor);
        hpp_lahan_efektif = (TextView)v.findViewById(R.id.hpp_lahan_efektif);
        listdata.setHasFixedSize(true);
        tambah = (FloatingActionButton) v.findViewById(R.id.tambah);
        not_found = (LinearLayout) v.findViewById(R.id.not_found);
        list = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        adapter = new Adapter_Hpp_Lahan(getActivity(),(ArrayList<Hpp_Lahan_Model>) list, getContext());
        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        loadJson();
        loadData();
        validate();
        refresh = (FrameLayout) v.findViewById(R.id.refresh);
        swLayout = (SwipeRefreshLayout) v.findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        return v;
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
    public void reload() {
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }

    private void loadJson() {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Detail_Lahan detail_lahan = new Detail_Lahan();
        final String kode = detail_lahan.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_LAHAN + "detaillahanlengkap", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("datahpp");
                    if(arr.length() > 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
                                Hpp_Lahan_Model md = new Hpp_Lahan_Model();
//                                md.setDesain_rumah(R.drawable.menu8);
                                md.setKode_hpp(data.getString("kode_hpp"));
                                md.setNama_biaya(data.getString("nama_biaya"));
                                md.setJumlah_biaya(data.getString("jumlah_biaya"));
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
                params.put("kode", AuthData.getInstance(getContext()).getAuthKey());
                params.put("kode_lahan", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    private void loadData() {
        final Detail_Lahan detail_lahan = new Detail_Lahan();
        final String kode = detail_lahan.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_LAHAN+"detaillahanlengkap", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    hpp_lahan_kotor.setText(ServerAccess.numberConvert(data.getString("hpp_lahan")));
                    int luasefektif = 0;
                    if (data.getString("status").equals("2")){
                        luasefektif = Integer.parseInt(data.getString("lahan_terbangun")) / 100 * Integer.parseInt(data.getString("luas_proyek"));
                    }
                    Log.d("pesan", "loadata berjalan ");
                    Log.d("pesan", "hpp lahan adalah "+data.getString("hpp_lahan"));
                    String hpp_lahan = data.getString("hpp_lahan");
                    Log.d("pesan", "hpp lahan adalah "+Long.parseLong(hpp_lahan));
                    Log.d("pesan", "luas efektif adalah "+luasefektif);
//                    Log.d("pesan", "hpp_lahan_efektif adalah "+Long.parseLong(hpp_lahan)/ luasefektif);
//                    hpp_lahan_efektif.setText(ServerAccess.numberFormat( Long.parseInt(hpp_lahan)/ luasefektif));
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
                params.put("kode", AuthData.getInstance(getContext()).getAuthKey());
                params.put("kode_lahan", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}
