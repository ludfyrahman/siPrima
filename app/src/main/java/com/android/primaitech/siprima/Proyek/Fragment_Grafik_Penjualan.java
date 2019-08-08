package com.android.primaitech.siprima.Proyek;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Kavling.Model.Kavling_Model;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Fragment_Grafik_Penjualan extends Fragment {
    PieChartView pieChartView;
    List pieData;
    ProgressDialog pd;
    Detail_Proyek detail_proyek = new Detail_Proyek();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_grafik_penjualan, container, false);
        pieChartView = v.findViewById(R.id.chart);
        pd = new ProgressDialog(getActivity());
        loadJson();
        return v;
    }
    private void loadJson() {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Detail_Proyek detail_proyek = new Detail_Proyek();
        final String kode = detail_proyek.kode;
        final String warna[] = {"#71c285", "#556080", "#f0c419", "#f0785a"};
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PROYEK + "detailproyek", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                pieData = new ArrayList<>();
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("datagrafikjual");
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
                                pieData.add(new SliceValue(data.getInt("jmljual"), Color.parseColor(warna[i])).setLabel(""+ServerAccess.bulan[data.getInt("bulan")]+" "+data.getString("tahun")+" ("+data.getString("jmljual")+" Unit )"));
                                PieChartData pieChartData = new PieChartData(pieData);
                                pieChartData.setHasLabels(true).setValueLabelTextSize(15);
                                pieChartView.setPieChartData(pieChartData);
                            } catch (Exception ea) {
                                ea.printStackTrace();

                            }
                        }
                    pd.cancel();
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
                params.put("kode_proyek", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}
