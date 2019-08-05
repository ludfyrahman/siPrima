package com.android.primaitech.siprima.Penjualan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Cuti.Tambah_Cuti;
import com.android.primaitech.siprima.Pembeli.Tambah_Pembeli;
import com.android.primaitech.siprima.Pembeli.Terima_Penjualan;
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

public class Fragment_Info_Penjualan extends Fragment {
    ProgressDialog pd;
    LinearLayout bg;
    Button  terima,selesai, batalkan,tolak;
    TextView nama_kavling, nama_proyek, nama_karyawan, tanggal_penjualan, cara_beli, harga_jual_bersih, harga_jual, uang_booking, tanggal_bayar_booking,
            diskon, status,  nama_pembeli, nik, no_hp, alamat, instansi_kerja, no_telp_instansi, alamat_instansi, lain_lain;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_info_penjualan, container, false);
        pd = new ProgressDialog(getContext());
        bg = (LinearLayout)v.findViewById(R.id.bg);
        loadJson();
        terima = (Button)v.findViewById(R.id.terima);
        selesai = (Button)v.findViewById(R.id.selesai);
        batalkan = (Button)v.findViewById(R.id.batalkan);
        tolak = (Button)v.findViewById(R.id.tolak);
        nama_kavling = (TextView)v.findViewById(R.id.nama_kavling);
        status = (TextView)v.findViewById(R.id.status);
        nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
        nama_karyawan = (TextView)v.findViewById(R.id.nama_karyawan);
        cara_beli = (TextView)v.findViewById(R.id.cara_beli);
        tanggal_penjualan = (TextView)v.findViewById(R.id.tanggal_penjualan);
        harga_jual = (TextView)v.findViewById(R.id.harga_jual);
        harga_jual_bersih = (TextView)v.findViewById(R.id.harga_jual_bersih);
        diskon = (TextView)v.findViewById(R.id.diskon);
        uang_booking = (TextView)v.findViewById(R.id.uang_booking);
        tanggal_bayar_booking = (TextView)v.findViewById(R.id.tanggal_bayar_booking);

        nama_pembeli = (TextView)v.findViewById(R.id.nama_pembeli);
        nik = (TextView)v.findViewById(R.id.nik);
        no_hp = (TextView)v.findViewById(R.id.no_hp);
        alamat = (TextView)v.findViewById(R.id.alamat);

        instansi_kerja = (TextView)v.findViewById(R.id.instansi_kerja);
        no_telp_instansi = (TextView)v.findViewById(R.id.no_telp_instansi);
        alamat_instansi = (TextView)v.findViewById(R.id.alamat_instansi);
        lain_lain = (TextView)v.findViewById(R.id.lain_lain);
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Terima_Penjualan bt = new Terima_Penjualan();
                Bundle bundle = new Bundle();
                bt.setArguments(bundle);
                bt.show(getFragmentManager(), "Cuti");
            }
        });
        return v;
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Detail_Penjualan detail_penjualan  = new Detail_Penjualan();
        final String nama_kav = detail_penjualan.nama_menu;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PENJUALAN+"detailpenjualan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_kavling.setText(nama_kav);
                    nama_proyek.setText(data.getString("nama_proyek"));
                    nama_karyawan.setText(data.getString("nama_karyawan"));
                    cara_beli.setText(ServerAccess.metode[data.getInt("cara_beli")]);
                    tanggal_penjualan.setText(ServerAccess.parseDate(data.getString("create_at")));
                    harga_jual.setText(ServerAccess.numberConvert(data.getString("harga_jual")));
                    harga_jual_bersih.setText(ServerAccess.numberConvert(data.getString("harga_jual_bersih")));
                    diskon.setText(ServerAccess.numberConvert(data.getString("diskon")));
                    uang_booking.setText(ServerAccess.numberConvert(data.getString("uang_booking")));
                    tanggal_bayar_booking.setText(ServerAccess.parseDate(data.getString("tanggal_bayar_booking")));

                    nama_pembeli.setText(data.getString("nama_pembeli"));
                    nik.setText(data.getString("no_ktp"));
                    no_hp.setText(data.getString("no_hp"));
                    alamat.setText(data.getString("alamat_pembeli"));

                    instansi_kerja.setText(data.getString("instansi_kerja"));
                    no_telp_instansi.setText(data.getString("no_telp_instansi"));
                    alamat_instansi.setText(data.getString("alamat_instansi"));
                    lain_lain.setText(data.getString("lain_lain"));
                    status.setText(ServerAccess.statusPenjualan[data.getInt("status_penjualan")]);
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
                    switch (data.getInt("status_penjualan")){
                        case 0:
                            batalkan.setVisibility(View.VISIBLE);
                            batalkan.setText("Penjualan Dibatalkan");
                            selesai.setVisibility(View.GONE);
                            tolak.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            break;
                        case 1:
                            selesai.setVisibility(View.VISIBLE);
                            batalkan.setVisibility(View.VISIBLE);
                            tolak.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            break;
                        case 2:
                            tolak.setVisibility(View.VISIBLE);
                            terima.setVisibility(View.VISIBLE);
                            selesai.setVisibility(View.GONE);
                            batalkan.setVisibility(View.GONE);
                            break;
                        case 3:
                            selesai.setVisibility(View.VISIBLE);
                            selesai.setText("Penjualan Selesai");
                            break;
                        case 4:
                            tolak.setVisibility(View.VISIBLE);
                            tolak.setText("Penjualan Ditolak");
                            break;
                        default:
                            tolak.setVisibility(View.VISIBLE);
                            tolak.setText("Tidak Ada Akses");

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
                params.put("kodepenjualan", detail_penjualan.kode);
                return params;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(senddata);
    }
}
