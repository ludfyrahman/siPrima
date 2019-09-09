package com.primagroup.primaitech.siprima.Penjualan;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.primagroup.primaitech.siprima.Config.AppController;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Penjualan.Temp.Temp_Penjualan;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.githang.stepview.StepView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form_Konfirmasi extends AppCompatActivity {
    StepView mStepView;
    ProgressDialog pd;
    Button prev,finish;
    TextView nama_pembeli, nama_kavling, harga_kavling, harga_bersih, metode_bayar, diskon, total_dibayar, jumlah_uang_dp, lama_angsuran_dp, jumlah_angsuran_dp, tanggal_pembayaran_dp, jumlah_uang_booking,
            tanggal_uang_booking, lama_angsuran, jumlah_angsuran, tanggal_angsuran, status, tanggal_sisa_pembayaran;
    LinearLayout linear_nama_pembeli, linear_nama_kavling, linear_harga_kavling, linear_harga_bersih, linear_metode_bayar, linear_diskon, linear_total_dibayar, linear_jumlah_uang_dp, linear_lama_angsuran_dp, linear_jumlah_angsuran_dp, linear_tanggal_pembayaran_dp, linear_jumlah_uang_booking,
            linear_tanggal_uang_booking, linear_lama_angsuran, linear_jumlah_angsuran, linear_tanggal_angsuran, linear_status, linear_tanggal_sisa_pembayaran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_konfirmasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Form_Pembayaran.class));
            }
        });
        linear_nama_pembeli = (LinearLayout)findViewById(R.id.linear_nama_pembeli);
        linear_nama_kavling = (LinearLayout)findViewById(R.id.linear_nama_kavling);
        linear_harga_kavling = (LinearLayout)findViewById(R.id.linear_harga_kavling);
        linear_harga_bersih = (LinearLayout)findViewById(R.id.linear_harga_bersih);
        linear_metode_bayar = (LinearLayout)findViewById(R.id.linear_metode_bayar);
        linear_diskon = (LinearLayout)findViewById(R.id.linear_diskon);
        linear_total_dibayar = (LinearLayout)findViewById(R.id.linear_total_dibayar);
        linear_jumlah_uang_dp = (LinearLayout)findViewById(R.id.linear_jumlah_uang_dp);
        linear_lama_angsuran_dp = (LinearLayout)findViewById(R.id.linear_lama_angsuran_dp);
        linear_jumlah_angsuran_dp = (LinearLayout)findViewById(R.id.linear_jumlah_angsuran_dp);
        linear_tanggal_pembayaran_dp = (LinearLayout)findViewById(R.id.linear_tanggal_pembayaran_dp);
        linear_jumlah_uang_booking = (LinearLayout)findViewById(R.id.linear_jumlah_uang_booking);
        linear_tanggal_uang_booking = (LinearLayout)findViewById(R.id.linear_tanggal_uang_booking);
        linear_lama_angsuran = (LinearLayout)findViewById(R.id.linear_lama_angsuran);
        linear_jumlah_angsuran = (LinearLayout)findViewById(R.id.linear_jumlah_angsuran);
        linear_tanggal_angsuran = (LinearLayout)findViewById(R.id.linear_tanggal_angsuran);
        linear_status = (LinearLayout)findViewById(R.id.linear_status);
        linear_tanggal_sisa_pembayaran = (LinearLayout)findViewById(R.id.linear_tanggal_sisa_pembayaran);
        mStepView = (StepView) findViewById(R.id.step_view);
        nama_pembeli = (TextView)findViewById(R.id.nama_pembeli);
        pd = new ProgressDialog(Form_Konfirmasi.this);
        nama_pembeli.setText(Temp_Penjualan.getInstance(getBaseContext()).getNama_pembeli());
        nama_kavling = (TextView)findViewById(R.id.nama_kavling);
        nama_kavling.setText(Temp_Penjualan.getInstance(getBaseContext()).getNama_kavling());
        harga_kavling = (TextView)findViewById(R.id.harga_kavling);
        harga_kavling.setText(ServerAccess.numberConvert(Temp_Penjualan.getInstance(getBaseContext()).getHarga_jual()));
        harga_bersih = (TextView)findViewById(R.id.harga_bersih);
        harga_bersih.setText(ServerAccess.numberConvert(Temp_Penjualan.getInstance(getBaseContext()).getHarga_bersih()));
        metode_bayar = (TextView)findViewById(R.id.metode_bayar);
        metode_bayar.setText(Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar());
        diskon = (TextView)findViewById(R.id.diskon);
        diskon.setText(ServerAccess.numberConvert(Temp_Penjualan.getInstance(getBaseContext()).getDiskon()));
        total_dibayar = (TextView)findViewById(R.id.total_dibayar);
        total_dibayar.setText(Temp_Penjualan.getInstance(getBaseContext()).getHarga_bersih());
        tanggal_sisa_pembayaran = (TextView)findViewById(R.id.tanggal_sisa_pembayaran);
        tanggal_sisa_pembayaran.setText(ServerAccess.parseDate(Temp_Penjualan.getInstance(getBaseContext()).getTanggal_sisa_pembayaran()));
        jumlah_uang_dp = (TextView)findViewById(R.id.jumlah_uang_dp);
        if ("null".equalsIgnoreCase(Temp_Penjualan.getInstance(getBaseContext()).getUang_muka())){
            jumlah_uang_dp.setText(ServerAccess.numberConvert(Temp_Penjualan.getInstance(getBaseContext()).getUang_muka()));
        }
        lama_angsuran_dp = (TextView)findViewById(R.id.lama_angsuran_dp);
        lama_angsuran_dp.setText(Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_dp()+" Bulan");
        jumlah_angsuran_dp = (TextView)findViewById(R.id.jumlah_angsuran_dp);
        jumlah_angsuran_dp.setText(Temp_Penjualan.getInstance(getBaseContext()).getJumlah_uang_dp_perbulan());
        tanggal_pembayaran_dp = (TextView)findViewById(R.id.tanggal_pembayaran_dp);
        tanggal_pembayaran_dp.setText("Tanggal "+Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_dp()+" Setiap Bulan");
        jumlah_uang_booking = (TextView)findViewById(R.id.jumlah_uang_booking);
        jumlah_uang_booking.setText(ServerAccess.numberConvert((Temp_Penjualan.getInstance(getBaseContext()).getUang_booking())));
        tanggal_uang_booking = (TextView)findViewById(R.id.tanggal_uang_booking);
        tanggal_uang_booking.setText(Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking());
        lama_angsuran = (TextView)findViewById(R.id.lama_angsuran);
        lama_angsuran.setText(Temp_Penjualan.getInstance(getBaseContext()).getLama_angsuran_bulanan()+"x");
        jumlah_angsuran = (TextView)findViewById(R.id.jumlah_angsuran);
        if ("null".equalsIgnoreCase(Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_perbulan())) {
            jumlah_angsuran.setText(ServerAccess.numberConvert(Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_perbulan()));
        }
        tanggal_angsuran = (TextView)findViewById(R.id.tanggal_angsuran);
        tanggal_angsuran.setText("Tanggal "+Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_angsuran()+" Setiap Bulan");
        status = (TextView)findViewById(R.id.status);
        List<String> steps = Arrays.asList(new String[]{"Data Pembeli", "Data Kavling", "Pembayaran", "Konfirmasi"});
        mStepView.setSteps(steps);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        int nextStep = mStepView.getCurrentStep() + 3;
        if (nextStep > mStepView.getStepCount()) {
            nextStep = 3;
        }
        mStepView.selectedStep(nextStep);
        prev = (Button)findViewById(R.id.prev);
        finish = (Button)findViewById(R.id.finish);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Konfirmasi.this, Form_Pembayaran.class));
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Form_Konfirmasi.this, Dashboard.class));
                simpan();
            }
        });
        validate();
    }
    private void validate(){
        Log.d("pesan", "metode bayarnya adalah "+Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar());
        Log.d("pesan", "nilai boolean "+Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar().equals("1"));
        if (Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar().equals("1")){
            linear_nama_pembeli.setVisibility(View.VISIBLE);
            linear_nama_kavling.setVisibility(View.VISIBLE);
            linear_harga_kavling.setVisibility(View.VISIBLE);
            linear_harga_bersih.setVisibility(View.VISIBLE);
            linear_metode_bayar.setVisibility(View.VISIBLE);
            linear_diskon.setVisibility(View.VISIBLE);
            linear_total_dibayar.setVisibility(View.VISIBLE);
            linear_tanggal_sisa_pembayaran.setVisibility(View.VISIBLE);
            linear_jumlah_uang_booking.setVisibility(View.VISIBLE);
            linear_tanggal_uang_booking.setVisibility(View.VISIBLE);
            linear_status.setVisibility(View.VISIBLE);

            linear_jumlah_uang_dp.setVisibility(View.GONE);
            linear_lama_angsuran_dp.setVisibility(View.GONE);
            linear_jumlah_angsuran_dp.setVisibility(View.GONE);
            linear_tanggal_pembayaran_dp.setVisibility(View.GONE);
            linear_tanggal_uang_booking.setVisibility(View.GONE);
            linear_lama_angsuran.setVisibility(View.GONE);
            linear_jumlah_angsuran.setVisibility(View.GONE);
            linear_tanggal_angsuran.setVisibility(View.GONE);
        }else if (Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar().equals("2")){
            linear_nama_pembeli.setVisibility(View.VISIBLE);
            linear_nama_kavling.setVisibility(View.VISIBLE);
            linear_harga_kavling.setVisibility(View.VISIBLE);
            linear_harga_bersih.setVisibility(View.VISIBLE);
            linear_metode_bayar.setVisibility(View.VISIBLE);
            linear_diskon.setVisibility(View.VISIBLE);
            linear_total_dibayar.setVisibility(View.VISIBLE);
            linear_jumlah_uang_dp.setVisibility(View.VISIBLE);
            linear_lama_angsuran_dp.setVisibility(View.VISIBLE);
            linear_jumlah_angsuran_dp.setVisibility(View.VISIBLE);
            linear_tanggal_pembayaran_dp.setVisibility(View.VISIBLE);
            linear_jumlah_uang_booking.setVisibility(View.VISIBLE);
            linear_tanggal_uang_booking.setVisibility(View.VISIBLE);
            linear_lama_angsuran.setVisibility(View.VISIBLE);
            linear_jumlah_angsuran.setVisibility(View.VISIBLE);
            linear_tanggal_angsuran.setVisibility(View.VISIBLE);
            linear_status.setVisibility(View.VISIBLE);

            linear_tanggal_sisa_pembayaran.setVisibility(View.GONE);

        }
    }

    public void onBackPressed() {
        startActivity(new Intent(Form_Konfirmasi.this, Form_Pembayaran.class));
    }
    private void simpan(){
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_PENJUALAN+"prosespenjualan",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Toast.makeText(
                                            Form_Konfirmasi.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    Temp_Penjualan.getInstance(getBaseContext()).clear();
                                    startActivity(new Intent(Form_Konfirmasi.this, Penjualan.class));
                                } else {
                                    Toast.makeText(
                                            Form_Konfirmasi.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(
                                        Form_Konfirmasi.this,
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
                                    Form_Konfirmasi.this,
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
                    params.put("kode", AuthData.getInstance(Form_Konfirmasi.this).getAuthKey());
                    params.put("kode_pembeli", Temp_Penjualan.getInstance(getBaseContext()).getKode_pembeli());
                    params.put("diskon", Temp_Penjualan.getInstance(getBaseContext()).getDiskon());
                    params.put("kode_kavling", Temp_Penjualan.getInstance(getBaseContext()).getKode_kavling());
                    params.put("metode_bayar", Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar());
                    params.put("kode_karyawan", AuthData.getInstance(getBaseContext()).getAksesData());
                    if (Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar().equals("1")) {
                        params.put("cash_uang_booking", Temp_Penjualan.getInstance(getBaseContext()).getUang_booking());
                        params.put("tgl_bayarbooking_cash", (Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking() == null ? "" : Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking()));
                        params.put("tgl_bayar_cash", (Temp_Penjualan.getInstance(getBaseContext()).getTanggal_sisa_pembayaran() == null ? "" : Temp_Penjualan.getInstance(getBaseContext()).getTanggal_sisa_pembayaran()));
                        params.put("lainlaincash", Temp_Penjualan.getInstance(getBaseContext()).getLain_lain());
                    }else if (Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar().equals("2")){
                        params.put("kredit_uang_muka", Temp_Penjualan.getInstance(getBaseContext()).getUang_muka());
                        params.put("kredit_jml_angsur_dp", Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_dp());
                        params.put("kredit_tgl_bayar_dp", Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_dp());
                        params.put("kredit_uang_booking", Temp_Penjualan.getInstance(getBaseContext()).getUang_booking());
                        params.put("tgl_bayarbooking_kredit", Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking());
                        params.put("kredit_jml_angsur_bulanan", Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_perbulan());
                        params.put("kredit_tgl_bayar_angsuran", Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_angsuran());
                        params.put("lainlainkredit", Temp_Penjualan.getInstance(getBaseContext()).getLain_lain());
                    }
                    params.put("nama_pembeli", Temp_Penjualan.getInstance(getBaseContext()).getNama_pembeli());
                    params.put("nik", Temp_Penjualan.getInstance(getBaseContext()).getNik());
                    params.put("email",  Temp_Penjualan.getInstance(getBaseContext()).getEmail());
                    params.put("instansi_kerja", Temp_Penjualan.getInstance(getBaseContext()).getInstansi_kerja());
                    params.put("alamat_instansi", Temp_Penjualan.getInstance(getBaseContext()).getAlamat_instansi());
                    params.put("no_instansi", Temp_Penjualan.getInstance(getBaseContext()).getNo_telp_instansi());
                    params.put("alamat", Temp_Penjualan.getInstance(getBaseContext()).getAlamat_pembeli());
                    params.put("no_hp", Temp_Penjualan.getInstance(getBaseContext()).getNo_hp());
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
