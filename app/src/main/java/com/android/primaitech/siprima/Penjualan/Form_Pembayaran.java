package com.android.primaitech.siprima.Penjualan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.MainActivity;
import com.android.primaitech.siprima.Penjualan.Temp.Temp_Penjualan;
import com.android.primaitech.siprima.R;
import com.githang.stepview.StepView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Form_Pembayaran extends AppCompatActivity {
    StepView mStepView;
    Button next, prev, tunai, tunai_bertahap;
    int metode = 1;
    Calendar myCalendar;
    boolean status = false;
    EditText diskon, uang_muka, uang_booking, jumlah_angsuran_dp, lama_angsuran, lain_lain,
            tanggal_bayar_dp, tanggal_bayar_angsuran;
    TextView harga_jual_bersih, sisa_dp, tanggal_bayar_booking, jumlah_dp_perbulan, tanggal_sisa_pembayaran, jumlah_angsuran_perbulan, sisa_uang_set_dp;
    ImageView calendar_bayar_booking, calendar_pembayaran;
    LinearLayout linear_diskon, linear_uang_muka,linear_uang_booking, linear_jumlah_angsuran_dp, linear_jumlah_dp_perbulan, linear_sisa_uang_set_dp, linear_lama_angsuran, linear_jumlah_angsuran_perbulan, linear_lain_lain,
    linear_harga_jual_bersih, linear_sisa_dp, linear_tanggal_bayar_angsuran, linear_tanggal_bayar_booking, linear_tanggal_bayar_dp, linear_tanggal_sisa_pembayaran;
    Date c = Calendar.getInstance().getTime();

    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    String now = df.format(c);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pembayaran);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Form_Data_Kavling.class));
            }
        });
        mStepView = (StepView) findViewById(R.id.step_view);
        List<String> steps = Arrays.asList(new String[]{"Data Pembeli", "Data Kavling", "Pembayaran", "Konfirmasi"});
        mStepView.setSteps(steps);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        int nextStep = mStepView.getCurrentStep() + 2;
        if (nextStep > mStepView.getStepCount()) {
            nextStep = 2;
        }
        mStepView.selectedStep(nextStep);
        linear_diskon = (LinearLayout)findViewById(R.id.linear_diskon);
        linear_uang_muka = (LinearLayout)findViewById(R.id.linear_uang_muka);
        linear_uang_booking = (LinearLayout)findViewById(R.id.linear_uang_booking);
        linear_jumlah_angsuran_dp = (LinearLayout)findViewById(R.id.linear_jumlah_angsuran_dp);
        linear_jumlah_dp_perbulan = (LinearLayout)findViewById(R.id.linear_jumlah_dp_perbulan);
        linear_sisa_uang_set_dp = (LinearLayout)findViewById(R.id.linear_sisa_uang_set_dp);
        linear_lama_angsuran = (LinearLayout)findViewById(R.id.linear_lama_angsuran_bulanan);
        linear_jumlah_angsuran_perbulan = (LinearLayout)findViewById(R.id.linear_jumlah_angsuran_perbulan);
        linear_harga_jual_bersih = (LinearLayout)findViewById(R.id.linear_harga_bersih);
        linear_tanggal_bayar_booking = (LinearLayout)findViewById(R.id.linear_tanggal_bayar_booking);
        linear_tanggal_bayar_angsuran = (LinearLayout)findViewById(R.id.linear_tanggal_bayar_angsuran);
        linear_tanggal_bayar_dp = (LinearLayout)findViewById(R.id.linear_tanggal_bayar_dp);
        linear_sisa_dp = (LinearLayout)findViewById(R.id.linear_sisa_dp);
        linear_tanggal_sisa_pembayaran = (LinearLayout)findViewById(R.id.linear_tanggal_sisa_pembayaran);
        linear_lain_lain = (LinearLayout)findViewById(R.id.linear_lain_lain);
        calendar_pembayaran = (ImageView)findViewById(R.id.calendar_sisa_pembayaran);
        calendar_bayar_booking = (ImageView)findViewById(R.id.calendar_bayar_booking);
        diskon = (EditText)findViewById(R.id.diskon);
        harga_jual_bersih = (TextView) findViewById(R.id.harga_jual_bersih);
        diskon.setText("0");
        diskon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setDiskon(diskon.getText().toString());
                int value = 0;
                if (diskon.getText().toString().equals("")){
                    value = 0;
                }else{
                    value = Integer.parseInt(diskon.getText().toString());
                }
                int harga_bersih = Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getHarga_jual()) -  value;
                harga_jual_bersih.setText(String.valueOf(harga_bersih));
                Temp_Penjualan.getInstance(getBaseContext()).setHarga_bersih(String.valueOf(harga_bersih));
            }
        });
        uang_muka = (EditText)findViewById(R.id.uang_muka);
        uang_muka.setText("0");
        uang_muka.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setUang_muka(uang_muka.getText().toString());
                int sisa = 0;
                if (uang_muka.getText().toString().equals("") || uang_muka.getText().toString().equals("0")){
                    sisa = 0;
                }else{
                    sisa = Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getHarga_bersih()) - Integer.parseInt(uang_muka.getText().toString());
                }
                sisa_uang_set_dp.setText(String.valueOf(sisa));
                Temp_Penjualan.getInstance(getBaseContext()).setSisa_pembayaran_setelah_dp(String.valueOf(sisa));
            }
        });
        tanggal_sisa_pembayaran = (TextView)findViewById(R.id.tanggal_sisa_pembayaran);
        tanggal_sisa_pembayaran.setText(now);
        uang_booking = (EditText)findViewById(R.id.uang_booking);
        uang_booking.setText("0");
        uang_booking.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setUang_booking(uang_booking.getText().toString());
                int value = 0;
                if (uang_booking.getText().toString().equals("")){
                    value = 0;
                }else{
                    value = Integer.parseInt(uang_booking.getText().toString());
                }
                int sisa_uang_muka = Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getUang_muka()) - value;
                sisa_dp.setText(String.valueOf(sisa_uang_muka));
                Temp_Penjualan.getInstance(getBaseContext()).setSisa_dp(String.valueOf(sisa_uang_muka));
//                Log.d("pesan", "uang muka "+Temp_Penjualan.getInstance(getBaseContext()).getUang_muka()+" uang booking "+uang_booking.getText().toString());
            }
        });
        sisa_dp = (TextView) findViewById(R.id.sisa_dp);
        tanggal_bayar_dp = (EditText)findViewById(R.id.tanggal_bayar_dp);
        tanggal_bayar_dp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setTanggal_bayar_dp(tanggal_bayar_dp.getText().toString());
            }
        });
        tanggal_bayar_angsuran = (EditText)findViewById(R.id.tanggal_bayar_angsuran);
        tanggal_bayar_angsuran.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setTanggal_bayar_angsuran(tanggal_bayar_angsuran.getText().toString());
            }
        });
        tanggal_bayar_booking = (TextView)findViewById(R.id.tanggal_bayar_booking);
        tanggal_bayar_booking.setText(now);
        jumlah_angsuran_dp = (EditText)findViewById(R.id.jumlah_angsuran_dp);
        jumlah_angsuran_dp.setText("0");
        jumlah_angsuran_dp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setJumlah_angsuran_dp(jumlah_angsuran_dp.getText().toString());
                int jumlah_uang = 0;
                if (jumlah_angsuran_dp.getText().toString().equals("") || jumlah_angsuran_dp.getText().toString().equals("0")){
                    jumlah_uang = 0;
                }else{
                    jumlah_uang = Integer.parseInt(Temp_Penjualan.getInstance(getBaseContext()).getSisa_dp()) / Integer.parseInt(jumlah_angsuran_dp.getText().toString());
                }
                jumlah_dp_perbulan.setText(String.valueOf(jumlah_uang));
                Temp_Penjualan.getInstance(getBaseContext()).setJumlah_uang_dp_perbulan(String.valueOf(jumlah_uang));
            }
        });
        jumlah_dp_perbulan = (TextView) findViewById(R.id.jumlah_dp_perbulan);
        sisa_uang_set_dp = (TextView) findViewById(R.id.sisa_uang_set_dp);
        lama_angsuran = (EditText)findViewById(R.id.lama_angsuran);
        lama_angsuran.setText("0");
        lama_angsuran.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setLama_angsuran_bulanan(lama_angsuran.getText().toString());
                int uang_angsuran = 0;
                if (lama_angsuran.getText().toString().equals("") || lama_angsuran.getText().toString().equals("0")){
                    uang_angsuran = 0;
                }else{
                    uang_angsuran = Integer.parseInt(sisa_uang_set_dp.getText().toString()) / Integer.parseInt(lama_angsuran.getText().toString());
                }
                jumlah_angsuran_perbulan.setText(String.valueOf(uang_angsuran));
                Temp_Penjualan.getInstance(getBaseContext()).setJumlah_angsuran_perbulan(String.valueOf(uang_angsuran));
            }
        });
        jumlah_angsuran_perbulan = (TextView)findViewById(R.id.jumlah_angsuran_perbulan);
        calendar_bayar_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(Form_Pembayaran.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        tanggal_bayar_booking.setText(dateFormatter.format(newDate.getTime()));
                        Temp_Penjualan.getInstance(getBaseContext()).setTanggal_bayar_booking(dateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        calendar_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(Form_Pembayaran.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        try{
                            Date strDate = dateFormatter.parse(dateFormatter.format(myCalendar.getTime()));
                            if (System.currentTimeMillis() > strDate.getTime()) {
                                status = false;
                                Toast.makeText(Form_Pembayaran.this, "Tanggal Sisa Pembayaran Tidak Valid", Toast.LENGTH_SHORT).show();
                            }else{
                                status = true;
                            }
                        }catch (Exception e){
                            Log.d("pesan", "error "+e.getMessage());
                        }
                        tanggal_sisa_pembayaran.setText(dateFormatter.format(newDate.getTime()));
                        Temp_Penjualan.getInstance(getBaseContext()).setTanggal_sisa_pembayaran(dateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        lain_lain = (EditText)findViewById(R.id.lain_lain);
        lain_lain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Penjualan.getInstance(getBaseContext()).setLain_lain(lain_lain.getText().toString());
            }
        });
        next = (Button)findViewById(R.id.next);
        prev = (Button)findViewById(R.id.prev);
        tunai = (Button)findViewById(R.id.tunai);
        tunai_bertahap = (Button)findViewById(R.id.tunai_bertahap);
        loadForm(metode);
        tunai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tunai.setBackgroundResource(R.drawable.box_shadow);
                tunai_bertahap.setBackgroundResource(R.drawable.border_green);
                metode = 1;
                loadForm(1);
                Temp_Penjualan.getInstance(getBaseContext()).setMetode_bayar("1");
            }
        });
        tunai_bertahap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metode = 2;
                tunai_bertahap.setBackgroundResource(R.drawable.box_shadow);
                tunai.setBackgroundResource(R.drawable.border_green);
                loadForm(2);
                Temp_Penjualan.getInstance(getBaseContext()).setMetode_bayar("2");
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Pembayaran.this, Form_Konfirmasi.class));
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Form_Pembayaran.this, Form_Data_Kavling.class));
            }
        });
    }
    private void loadForm(int met){
        linear_uang_muka.setVisibility(View.GONE);
        linear_uang_booking.setVisibility(View.GONE);
        linear_jumlah_angsuran_dp.setVisibility(View.GONE);
        linear_jumlah_dp_perbulan.setVisibility(View.GONE);
        linear_sisa_uang_set_dp.setVisibility(View.GONE);
        linear_lama_angsuran.setVisibility(View.GONE);
        linear_jumlah_angsuran_perbulan.setVisibility(View.GONE);
        linear_harga_jual_bersih.setVisibility(View.GONE);
        linear_tanggal_bayar_booking.setVisibility(View.GONE);
        linear_tanggal_bayar_dp.setVisibility(View.GONE);
        linear_tanggal_bayar_angsuran.setVisibility(View.GONE);
        linear_tanggal_sisa_pembayaran.setVisibility(View.GONE);
        linear_sisa_dp.setVisibility(View.GONE);
        if(met == 1){
            linear_diskon.setVisibility(View.VISIBLE);
            linear_harga_jual_bersih.setVisibility(View.VISIBLE);
            linear_uang_booking.setVisibility(View.VISIBLE);
            linear_harga_jual_bersih.setVisibility(View.VISIBLE);
            linear_tanggal_bayar_booking.setVisibility(View.VISIBLE);
            linear_tanggal_sisa_pembayaran.setVisibility(View.VISIBLE);
        }else if(met == 2){
            linear_diskon.setVisibility(View.VISIBLE);
            linear_harga_jual_bersih.setVisibility(View.VISIBLE);
            linear_uang_muka.setVisibility(View.VISIBLE);
            linear_uang_booking.setVisibility(View.VISIBLE);
            linear_sisa_dp.setVisibility(View.VISIBLE);
            linear_jumlah_angsuran_dp.setVisibility(View.VISIBLE);
            linear_jumlah_dp_perbulan.setVisibility(View.VISIBLE);
            linear_tanggal_bayar_dp.setVisibility(View.VISIBLE);
            linear_tanggal_bayar_booking.setVisibility(View.VISIBLE);
            linear_sisa_uang_set_dp.setVisibility(View.VISIBLE);
            linear_lama_angsuran.setVisibility(View.VISIBLE);
            linear_jumlah_angsuran_perbulan.setVisibility(View.VISIBLE);
            linear_tanggal_bayar_angsuran.setVisibility(View.VISIBLE);
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(Form_Pembayaran.this, Form_Data_Kavling.class));
    }
}
