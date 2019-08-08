package com.android.primaitech.siprima.Penjualan;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Karyawan.Model.Karyawan_Model;
import com.android.primaitech.siprima.Penjualan.Adapter.Adapter_Info_Pembayaran;
import com.android.primaitech.siprima.Penjualan.Adapter.Adapter_Riwayat_Pembayaran;
import com.android.primaitech.siprima.Penjualan.Model.Info_Pembayaran_Model;
import com.android.primaitech.siprima.Penjualan.Model.Riyawat_Pembayaran_Model;
import com.android.primaitech.siprima.Proyek.Adapter.Adapter_Karyawan_Proyek;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
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

public class Fragment_Info_Pembayaran extends Fragment {
    public static String buat, edit, hapus, detail;
    FloatingActionButton tambah;
    private Adapter_Info_Pembayaran adapter;
    private List<Info_Pembayaran_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    LinearLayout not_found;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    ProgressDialog pd;
    Detail_Penjualan detail_penjualan = new Detail_Penjualan();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_info_pembayaran, container, false);
        listdata = (RecyclerView) v.findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        tambah = (FloatingActionButton) v.findViewById(R.id.tambah);
        not_found = (LinearLayout) v.findViewById(R.id.not_found);
        list = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        adapter = new Adapter_Info_Pembayaran(getActivity(),(ArrayList<Info_Pembayaran_Model>) list);
        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        loadJson();
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
        final String kode = detail_penjualan.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PENJUALAN + "detailpenjualan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    Log.d("pesan", "datanya "+AuthData.getInstance(getContext()).getAuthKey());
                    Log.d("pesan", "kode "+kode);
                    res = new JSONObject(response);
                    if(res.getString("datajadwal") != "null") {
                        JSONArray arr = res.getJSONArray("datajadwal");
                        Log.d("pesan", "jumlah data "+arr.length());
                        Log.d("pesan", "data "+arr.toString());
                        if(arr.length() > 0) {
                            for (int i = 0; i < arr.length(); i++) {
                                try {
                                    JSONObject data = arr.getJSONObject(i);
                                    Info_Pembayaran_Model md = new Info_Pembayaran_Model();
                                    md.setKode_jadwal(data.getString("kode_jadwal"));
                                    md.setKode_penjualan(data.getString("kode_penjualan"));
                                    md.setNama_pembayaran(ServerAccess.jenis_pembayaran[data.getInt("nama_pembayaran")]);
                                    md.setJumlah_bayar(ServerAccess.numberConvert(data.getString("jumlah_bayar")));
                                    md.setTanggal_bayar(ServerAccess.parseDate(data.getString("tanggal_bayar")));
                                    md.setAngsuran_ke(data.getString("angsuran_ke"));
                                    md.setStatus_bayar(ServerAccess.status_pembayaran[data.getInt("status_bayar")]);
                                    list.add(md);
                                } catch (Exception ea) {
                                    ea.printStackTrace();
                                    pd.cancel();
                                    not_found.setVisibility(View.VISIBLE);
                                    Log.d("error" ,ea.getMessage());
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
                    Log.d("volley", "errornya : " + e.getMessage());
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
                params.put("kodepenjualan", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}