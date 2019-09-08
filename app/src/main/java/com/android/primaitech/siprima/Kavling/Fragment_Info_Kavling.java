package com.android.primaitech.siprima.Kavling;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_Info_Kavling extends Fragment {
    ProgressDialog pd;
    LinearLayout bg;
    TextView nama_kavling, nama_unit, nama_proyek, nama_kategori, tipe_rumah, harga_produksi, harga_jual, status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_info_kavling, container, false);
        pd = new ProgressDialog(getContext());
        nama_kavling = (TextView)v.findViewById(R.id.nama_kavling);
        nama_unit = (TextView)v.findViewById(R.id.nama_unit);
        nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
        nama_kategori = (TextView)v.findViewById(R.id.nama_kategori);
        tipe_rumah = (TextView)v.findViewById(R.id.tipe_rumah);
        harga_produksi = (TextView)v.findViewById(R.id.harga_produksi);
        harga_jual = (TextView)v.findViewById(R.id.harga_jual);
        status = (TextView)v.findViewById(R.id.status);
        bg = (LinearLayout)v.findViewById(R.id.bg);
        loadJson();
        return  v;
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        Detail_Kavling detail_kavling = new Detail_Kavling();
        final String kode = detail_kavling.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KAVLING+"detailkavling", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_kavling.setText(data.getString("nama_kavling"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    nama_kategori.setText(data.getString("nama_kategori"));
                    tipe_rumah.setText(data.getString("tipe_rumah"));
                    harga_produksi.setText(ServerAccess.numberConvert(data.getString("harga_produksi")));
                    harga_jual.setText(ServerAccess.numberConvert(data.getString("harga_jual")));
                    status.setText(ServerAccess.status_kavling[data.getInt("status")]);
                    Glide.with(getActivity())
                            .load(ServerAccess.BASE_URL+"/"+data.getString("desain_rumah"))
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        bg.setBackground(resource);
                                    }
                                }
                            });
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

        RequestHandler.getInstance(getContext()).addToRequestQueue(senddata);
    }
}
