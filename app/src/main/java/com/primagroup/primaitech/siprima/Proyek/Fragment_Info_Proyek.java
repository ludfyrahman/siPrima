package com.primagroup.primaitech.siprima.Proyek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
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
import com.primagroup.primaitech.siprima.Tipe_Rumah.Tipe_Rumah;
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

public class Fragment_Info_Proyek extends Fragment {
    ProgressDialog pd;
    LinearLayout bg;
    Button tipe_rumah;
    TextView nama_proyek, alamat, nama_dokumen, saldo_cash, kecamatan, kelurahan, tanggal_mulai, luas_proyek, link_web, nama_lahan, nama_unit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_info_proyek, container, false);
        pd = new ProgressDialog(getActivity());
        nama_unit = (TextView)v.findViewById(R.id.nama_unit);
        nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
        alamat = (TextView)v.findViewById(R.id.alamat);
        saldo_cash = (TextView)v.findViewById(R.id.saldo_cash);
        nama_dokumen = (TextView)v.findViewById(R.id.nama_dokumen);
        tanggal_mulai = (TextView)v.findViewById(R.id.tanggal_mulai);
        luas_proyek = (TextView)v.findViewById(R.id.luas_proyek);
        link_web = (TextView)v.findViewById(R.id.link_web);
        nama_lahan = (TextView)v.findViewById(R.id.nama_lahan);
        tipe_rumah = (Button) v.findViewById(R.id.tipe_rumah);
        bg = (LinearLayout)v.findViewById(R.id.bg);
        tipe_rumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Detail_Proyek detail_proyek = new Detail_Proyek();
                final String kode =detail_proyek.kode;
                Intent intent = new Intent(v.getContext(), Tipe_Rumah.class);
                intent.putExtra("kode_proyek", kode);
                v.getContext().startActivity(intent);
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
        Detail_Proyek detail_proyek = new Detail_Proyek();
        final String kode =detail_proyek.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PROYEK+"detailproyek", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_unit.setText(data.getString("nama_unit"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    alamat.setText("Provinsi: "+data.getString("prov")+"\nKabupaten/Kota: "+data.getString("kota")+"\nKecamatan: "+data.getString("kecamatan")+
                            "\nKelurahan : " +data.getString("kelurahan")+
                            "\nKode Pos : "+data.getString("kode_pos")+
                            "\nRT/RW : "+data.getString("rtrw")+
                            "\nAlamat : "+data.getString("alamat")
                    );
//                    nama_dokumen.setText(data.getString("nama_dokumen"));
                    saldo_cash.setText(data.getString("saldo_cash"));
                    tanggal_mulai.setText(ServerAccess.parseDate(data.getString("tanggal_mulai")));
                    luas_proyek.setText(data.getString("luas_proyek"));
                    link_web.setText(data.getString("link_web"));
                    nama_lahan.setText(data.getString("nama_lahan"));
                    Glide.with(getActivity())
                            .load(ServerAccess.BASE_URL+"/"+data.getString("logo_proyek_kecil"))
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
                params.put("kode_proyek", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}
