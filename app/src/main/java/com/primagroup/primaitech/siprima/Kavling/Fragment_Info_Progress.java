package com.primagroup.primaitech.siprima.Kavling;

import android.app.ProgressDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.primagroup.primaitech.siprima.Config.AppController;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Kavling.Adapter.Adapter_Progress_Kavling;
import com.primagroup.primaitech.siprima.Kavling.Model.Progress_Kavling_Model;
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

public class Fragment_Info_Progress extends Fragment {
    public static String buat, edit, hapus, detail;
    FloatingActionButton tambah;
    private Adapter_Progress_Kavling adapter;
    private List<Progress_Kavling_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    LinearLayout not_found;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    ProgressDialog pd;
    Detail_Kavling detail_kavling = new Detail_Kavling();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_info_progress, container, false);
        listdata = (RecyclerView) v.findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        tambah = (FloatingActionButton) v.findViewById(R.id.tambah);
        not_found = (LinearLayout) v.findViewById(R.id.not_found);
        list = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        adapter = new Adapter_Progress_Kavling(getActivity(),(ArrayList<Progress_Kavling_Model>) list);
        mManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        if (detail_kavling.addprogres){
            tambah.show();
        }else{
            tambah.hide();
        }
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
        Detail_Kavling detail_kavling = new Detail_Kavling();
        final String kode = detail_kavling.kode;

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KAVLING + "detailkavling", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("dataprogreskavling");
                    if(arr.length() > 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
                                Progress_Kavling_Model md = new Progress_Kavling_Model();
                                md.setKode_kavling(data.getString("kode_kavling"));
                                md.setKode_progres(data.getString("kode_progres"));
                                md.setJudul(data.getString("judul"));
                                md.setDeskripsi(data.getString("deskripsi"));
                                md.setFoto_kecil(ServerAccess.BASE_URL+""+data.getString("foto_kecil"));
                                md.setCreate_at(data.getString("create_at"));
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
                params.put("kodekavling", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}