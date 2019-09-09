package com.primagroup.primaitech.siprima.Lahan;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Config.AppController;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_Info_Lahan extends Fragment {
    ProgressDialog pd;
    LinearLayout bg;
    TextView nama_lahan, harga_lahan, luas_lahan, tgl_mulai_bayar, tgl_akhir_bayar, telah_terbayar, kurang_pembayaran, status, nama_proyek, luas_proyek, lahan_terbangun, lahan_fasilitas, hpp_lahan;
    Button terima;
    final Detail_Lahan detail_lahan = new Detail_Lahan();
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
        terima = (Button) v.findViewById(R.id.terima);
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Terima_Lahan bt = new Terima_Lahan();
                Bundle bundle = new Bundle();
                bundle.putString("kode", detail_lahan.kode);
                bt.setArguments(bundle);
                bt.show(getFragmentManager(), "Tanggal");
            }
        });
        loadJson();
        return v;
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
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
                    harga_lahan.setText("harga_lahan");
                    luas_lahan.setText(data.getString("luas_lahan"));
                    tgl_mulai_bayar.setText(ServerAccess.parseDate(data.getString("tgl_mulai_bayar")));
                    tgl_akhir_bayar.setText(ServerAccess.parseDate(data.getString("tgl_akhir_bayar")));
                    telah_terbayar.setText(data.getString("telah_terbayar"));
                    luas_proyek.setText(data.getString("luas_proyek"));
                    status.setText(data.getString("status"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    luas_proyek.setText(data.getString("luas_proyek"));
                    lahan_terbangun.setText(data.getString("lahan_terbangun")+" %");
                    lahan_fasilitas.setText(data.getString("lahan_fasilitas")+" %");
                    hpp_lahan.setText(data.getString("hpp_lahan"));
                    if (data.getString("status").equals("3")){
                        if (detail_lahan.konfirmasi)
                            terima.setVisibility(View.VISIBLE);
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

}
