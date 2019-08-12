package com.android.primaitech.siprima.Lahan;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Kavling.Model.Kavling_Model;
import com.android.primaitech.siprima.Proyek.Adapter.Adapter_Kavling_Proyek;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Info_Lahan extends Fragment {
    ProgressDialog pd;
    LinearLayout bg;
    TextView nama_lahan, harga_lahan, luas_lahan, tgl_mulai_bayar, tgl_akhir_bayar, telah_terbayar, kurang_pembayaran, status, nama_proyek, luas_proyek, lahan_terbangun, lahan_fasilitas, hpp_lahan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_fragment_info_lahan, container, false);
        pd = new ProgressDialog(getActivity());
        nama_lahan = (TextView)v.findViewById(R.id.nama_lahan);
        harga_lahan = (TextView)v.findViewById(R.id.harga_lahan);
        luas_lahan = (TextView)v.findViewById(R.id.luas_lahan);
        tgl_mulai_bayar = (TextView)v.findViewById(R.id.tgl_mulai_bayar);
        tgl_akhir_bayar = (TextView)v.findViewById(R.id.tgl_akhir_bayar);
        telah_terbayar = (TextView)v.findViewById(R.id.telah_terbayar);
        luas_proyek = (TextView)v.findViewById(R.id.luas_proyek);
        kurang_pembayaran = (TextView)v.findViewById(R.id.kurang_pembayaran);
        status = (TextView)v.findViewById(R.id.status);
        nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
        luas_proyek = (TextView)v.findViewById(R.id.luas_proyek);
        lahan_terbangun = (TextView)v.findViewById(R.id.lahan_terbangun);
        lahan_fasilitas = (TextView)v.findViewById(R.id.lahan_fasilitas);
        hpp_lahan = (TextView)v.findViewById(R.id.hpp_lahan);
        loadJson();
        return v;
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        Detail_Lahan detail_lahan = new Detail_Lahan();
        final String kode =detail_lahan.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_LAHAN+"detaillahanlengkap", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_lahan.setText(data.getString("nama_lahan"));
                    harga_lahan.setText(ServerAccess.numberConvert(data.getString("harga_lahan")));
                    luas_lahan.setText(data.getString("luas_lahan"));
                    tgl_mulai_bayar.setText(ServerAccess.parseDate(data.getString("tgl_mulai_bayar")));
                    tgl_akhir_bayar.setText(ServerAccess.parseDate(data.getString("tgl_akhir_bayar")));
                    telah_terbayar.setText(ServerAccess.numberConvert(data.getString("telah_terbayar")));
                    luas_proyek.setText(data.getString("luas_proyek"));
                    status.setText(data.getString("status"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    luas_proyek.setText(data.getString("luas_proyek"));
                    lahan_terbangun.setText(data.getString("lahan_terbangun")+" %");
                    lahan_fasilitas.setText(data.getString("lahan_fasilitas")+" %");
                    hpp_lahan.setText(ServerAccess.numberConvert(data.getString("hpp_lahan")));

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

}
