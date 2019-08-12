package com.android.primaitech.siprima.Penjualan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Cuti.Detail_Cuti;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Penjualan.Model.Riyawat_Pembayaran_Model;
import com.android.primaitech.siprima.Penjualan.Temp.Temp_Penjualan;
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
import java.util.Map;

public class Fragment_Info_Penjualan extends Fragment {
    ProgressDialog pd;
    LinearLayout bg;
    Button  terima,selesai, batalkan,tolak, ulang;
    public static String kode_kavling = "", diskonValue="", metode_bayar, cash_uang_booking, tgl_bayarbooking_cash, tgl_bayar_cash, lainlaincash,
            kredit_uang_muka, kredit_tgl_bayar_dp, kredit_uang_booking, tgl_bayarbooking_kredit, kredit_jml_angsur_bulanan, kredit_tgl_bayar_angsuran, lainlainkredit;
    final Detail_Penjualan detail_penjualan  = new Detail_Penjualan();
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
        ulang = (Button)v.findViewById(R.id.ulang);
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
                bundle.putString("kode", detail_penjualan.kode);
                bt.setArguments(bundle);
                bt.show(getFragmentManager(), "Cuti");
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Keluar Dari Akun Ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selesai();

                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
        batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Keluar Dari Akun Ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                batalkan();

                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
        ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Pengajuan Ulang ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ulang();

                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Keluar Dari Akun Ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tolak();

                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
        return v;
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final String nama_kav = detail_penjualan.nama_menu;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PENJUALAN+"detailpenjualan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONObject aksiData = res.getJSONObject("aksi");
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
                    kode_kavling = data.getString("kode_kavling");
                    diskonValue = data.getString("diskon");
                    metode_bayar = data.getString("cara_beli");
                    cash_uang_booking = data.getString("uang_booking");
                    tgl_bayarbooking_cash = data.getString("tanggal_bayar_booking");
                    tgl_bayar_cash = data.getString("tanggal_bayar_cash");
                    lainlaincash = data.getString("lain_lain");
                    kredit_uang_muka = data.getString("uang_dp");
                    kredit_tgl_bayar_dp = data.getString("tanggal_bayar_dp");
                    kredit_uang_booking = data.getString("uang_booking");
                    tgl_bayarbooking_kredit = data.getString("tanggal_bayar_booking");
                    kredit_jml_angsur_bulanan = data.getString("angsuran_bulanan");
                    kredit_tgl_bayar_angsuran = data.getString("tanggal_bayar_angsuran");
                    lainlainkredit = data.getString("lainlainkredit");
                    switch (data.getInt("status_penjualan")){
                        case 0:
                            batalkan.setVisibility(View.VISIBLE);
                            batalkan.setText("Penjualan Dibatalkan");
                            selesai.setVisibility(View.GONE);
                            tolak.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            ulang.setVisibility(View.GONE);
                            break;
                        case 1:
                            selesai.setVisibility(View.VISIBLE);
                            batalkan.setVisibility(View.VISIBLE);
                            tolak.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            ulang.setVisibility(View.GONE);
                            break;
                        case 2:
                            if (aksiData.getBoolean("konfirmasi"))
                                terima.setVisibility(View.VISIBLE);
                            if (aksiData.getBoolean("tolak"))
                                tolak.setVisibility(View.VISIBLE);

                            selesai.setVisibility(View.GONE);
                            batalkan.setVisibility(View.GONE);
                            ulang.setVisibility(View.GONE);
                            break;
                        case 3:
                            selesai.setVisibility(View.VISIBLE);
                            selesai.setText("Penjualan Selesai");
                            batalkan.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            tolak.setVisibility(View.GONE);
                            ulang.setVisibility(View.GONE);
                            break;
                        case 4:
                            tolak.setVisibility(View.GONE);
                            ulang.setVisibility(View.VISIBLE);
                            tolak.setText("Tolak");
                            selesai.setVisibility(View.GONE);
                            terima.setVisibility(View.GONE);
                            batalkan.setVisibility(View.GONE);
                            break;
                        default:
                            tolak.setVisibility(View.VISIBLE);
                            tolak.setText("Tidak Ada Akses");
                            terima.setVisibility(View.GONE);
                            ulang.setVisibility(View.GONE);
                            selesai.setVisibility(View.GONE);
                            batalkan.setVisibility(View.GONE);

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
    private void tolak(){}
    private void selesai(){}
    private void batalkan(){}
    private void ulang()
    {
        pd.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ServerAccess.URL_PENJUALAN+"pengajuanulangpenjualan",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject data = obj.getJSONObject("respon");
                            if (data.getBoolean("status")) {
                                Toast.makeText(
                                        getContext(),
                                        data.getString("pesan"),
                                        Toast.LENGTH_LONG
                                ).show();
//                                    Form_Konfirmasi.this.onBackPressed();
                                startActivity(new Intent(getContext(), Penjualan.class));
                            } else {
                                Toast.makeText(
                                        getContext(),
                                        data.getString("pesan"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {

                            Toast.makeText(
                                    getContext(),
                                    e.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                            e.printStackTrace();
                            Log.d("pesan", "error "+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        Toast.makeText(
                                getContext(),
                                "error",
                                Toast.LENGTH_LONG
                        ).show();
                        Log.d("pesan", "error "+error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode", AuthData.getInstance(getContext()).getAuthKey());
                params.put("kode_penjualan", detail_penjualan.kode);
                params.put("kode_kavling", kode_kavling);
                params.put("diskon", diskonValue);
                params.put("metode_bayar", metode_bayar);
                params.put("kode_karyawan", AuthData.getInstance(getContext()).getAksesData());
                params.put("cash_uang_booking", uang_booking.getText().toString());
                params.put("tgl_bayarbooking_cash", (tgl_bayarbooking_cash == null ? "" : tgl_bayarbooking_cash));
                params.put("tgl_bayar_cash", (tgl_bayar_cash == null ? "" : tgl_bayar_cash));
                params.put("lainlaincash", lainlaincash);

                params.put("kredit_uang_muka",kredit_uang_muka);
                params.put("kredit_tgl_bayar_dp", kredit_tgl_bayar_dp);
                params.put("kredit_uang_booking", kredit_uang_booking);
                params.put("kredit_jml_angsur_dp", kredit_uang_booking);
                params.put("tgl_bayarbooking_kredit", tgl_bayarbooking_kredit);
                params.put("kredit_jml_angsur_bulanan", kredit_jml_angsur_bulanan);
                params.put("kredit_tgl_bayar_angsuran", kredit_tgl_bayar_angsuran);
                params.put("lainlainkredit", lainlainkredit);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
