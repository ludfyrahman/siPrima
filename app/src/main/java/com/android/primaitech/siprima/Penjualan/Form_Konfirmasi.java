package com.android.primaitech.siprima.Penjualan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.Penjualan.Temp.Temp_Penjualan;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.githang.stepview.StepView;
import com.google.android.gms.maps.model.Dash;

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
            tanggal_uang_booking, lama_angsuran, jumlah_angsuran, tanggal_angsuran, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_konfirmasi);
        mStepView = (StepView) findViewById(R.id.step_view);
        nama_pembeli = (TextView)findViewById(R.id.nama_pembeli);
        pd = new ProgressDialog(Form_Konfirmasi.this);
        nama_pembeli.setText(Temp_Penjualan.getInstance(getBaseContext()).getNama_pembeli());
        nama_kavling = (TextView)findViewById(R.id.nama_kavling);
        nama_kavling.setText(Temp_Penjualan.getInstance(getBaseContext()).getNama_pembeli());
        harga_kavling = (TextView)findViewById(R.id.harga_kavling);
        harga_kavling.setText(ServerAccess.numberFormat(Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getHarga_jual())));
        harga_bersih = (TextView)findViewById(R.id.harga_bersih);
        harga_bersih.setText(ServerAccess.numberFormat(Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getHarga_bersih())));
        metode_bayar = (TextView)findViewById(R.id.metode_bayar);
        metode_bayar.setText(Temp_Penjualan.getInstance(getBaseContext()).getMetode_bayar());
        diskon = (TextView)findViewById(R.id.diskon);
        diskon.setText(Temp_Penjualan.getInstance(getBaseContext()).getDiskon());
        total_dibayar = (TextView)findViewById(R.id.total_dibayar);
        total_dibayar.setText(ServerAccess.numberFormat(Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getHarga_bersih())));
        jumlah_uang_dp = (TextView)findViewById(R.id.jumlah_uang_dp);
        jumlah_uang_dp.setText(ServerAccess.numberFormat(Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getUang_muka())));
        lama_angsuran_dp = (TextView)findViewById(R.id.lama_angsuran_dp);
        lama_angsuran_dp.setText(Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_dp()+" Bulan");
        jumlah_angsuran_dp = (TextView)findViewById(R.id.jumlah_angsuran_dp);
        jumlah_angsuran_dp.setText(Temp_Penjualan.getInstance(getBaseContext()).getJumlah_uang_dp_perbulan());
        tanggal_pembayaran_dp = (TextView)findViewById(R.id.tanggal_pembayaran_dp);
        tanggal_pembayaran_dp.setText("Tanggal "+Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_dp()+" Setiap Bulan");
        jumlah_uang_booking = (TextView)findViewById(R.id.jumlah_uang_booking);
        jumlah_uang_booking.setText(ServerAccess.numberFormat(Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getUang_booking())));
        tanggal_uang_booking = (TextView)findViewById(R.id.tanggal_uang_booking);
        tanggal_uang_booking.setText(Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking());
        lama_angsuran = (TextView)findViewById(R.id.lama_angsuran);
        lama_angsuran.setText(Temp_Penjualan.getInstance(getBaseContext()).getLama_angsuran_bulanan()+"x");
        jumlah_angsuran = (TextView)findViewById(R.id.jumlah_angsuran);
        jumlah_angsuran.setText(Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_perbulan());
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
//                                    Form_Konfirmasi.this.onBackPressed();
                                    startActivity(new Intent(Form_Konfirmasi.this, Dashboard.class));
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
                    params.put("cash_uang_booking", Temp_Penjualan.getInstance(getBaseContext()).getUang_booking());
                    params.put("tgl_bayarbooking_cash", (Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking() == null ? "" : Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking()));
                    params.put("tgl_bayar_cash", (Temp_Penjualan.getInstance(getBaseContext()).getTanggal_sisa_pembayaran() == null ? "" : Temp_Penjualan.getInstance(getBaseContext()).getTanggal_sisa_pembayaran()));
                    params.put("lainlaincash", Temp_Penjualan.getInstance(getBaseContext()).getLain_lain());

                    params.put("kredit_uang_muka", Temp_Penjualan.getInstance(getBaseContext()).getUang_muka());
                    params.put("kredit_jml_angsur_dp", Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_dp());
                    params.put("kredit_tgl_bayar_dp", Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_dp());
                    params.put("kredit_uang_booking", Temp_Penjualan.getInstance(getBaseContext()).getUang_booking());
                    params.put("tgl_bayarbooking_kredit", Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_booking());
                    params.put("kredit_jml_angsur_bulanan", Temp_Penjualan.getInstance(getBaseContext()).getJumlah_angsuran_perbulan());
                    params.put("kredit_tgl_bayar_angsuran", Temp_Penjualan.getInstance(getBaseContext()).getTanggal_bayar_angsuran());
                    params.put("lainlainkredit", Temp_Penjualan.getInstance(getBaseContext()).getLain_lain());
                    params.put("nama_pembeli", Temp_Penjualan.getInstance(getBaseContext()).getNama_pembeli());
                    params.put("nik", "12");
                    params.put("email", "abi@gmail.com");
                    params.put("instansi_kerja", "pit");
                    params.put("alamat_instansi", "dabasah");
                    params.put("no_instansi", "032938298");
                    params.put("alamat", "kembang");
                    params.put("no_hp", "03921839218");
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
