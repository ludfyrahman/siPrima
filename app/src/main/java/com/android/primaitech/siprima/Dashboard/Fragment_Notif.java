package com.android.primaitech.siprima.Dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Adapter.AdapterMenu;
import com.android.primaitech.siprima.Dashboard.Adapter.Adapter_Notif;
import com.android.primaitech.siprima.Dashboard.Model.MenuModel;
import com.android.primaitech.siprima.Dashboard.Model.Notif_Model;
import com.android.primaitech.siprima.Pembeli.Adapter.Adapter_Pembeli;
import com.android.primaitech.siprima.Pembeli.Model.Pembeli_Model;
import com.android.primaitech.siprima.Pembeli.Tambah_Pembeli;
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

public class Fragment_Notif extends Fragment {
    FloatingActionButton tambah;
    private Adapter_Notif adapter;
    private List<Notif_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    LinearLayout not_found;
    SwipeRefreshLayout swLayout;
    ProgressDialog pd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_fragment_notif, container, false);
        listdata = (RecyclerView)v.findViewById(R.id.listdata);

        listdata.setHasFixedSize(true);

        not_found = (LinearLayout)v.findViewById(R.id.not_found);
        list = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        adapter = new Adapter_Notif(getActivity(),(ArrayList<Notif_Model>) list, getContext());
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
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

        return v;
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
                                Notif_Model md = new Notif_Model();
                                md.setKode_bantu(data.getString("kode_bantu"));
                                md.setKode_menu(data.getString("kode_menu").toLowerCase());
                                md.setDeskripsi(data.getString("deskripsi"));
                                md.setCreate_at(ServerAccess.parseDate(data.getString("create_at")));
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
                params.put("tipedata", "notifikasi");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
    }
}
