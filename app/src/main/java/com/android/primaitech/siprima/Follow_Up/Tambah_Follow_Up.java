package com.android.primaitech.siprima.Follow_Up;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AlertReceiver;
import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.MainActivity;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Tambah_Follow_Up extends AppCompatActivity {
    ImageView btn_calendar;
    ProgressDialog pd;
    Calendar myCalendar;
    Button simpan;
    TextView nama_pembeli, tanggal_pertemuan, alamat;
    DatePickerDialog.OnDateSetListener date;
    boolean status = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_follow_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tambah_Follow_Up.this.onBackPressed();
            }
        });
        simpan = (Button)findViewById(R.id.simpan);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
        nama_pembeli = (TextView) findViewById(R.id.nama_pembeli);
        nama_pembeli.setText(data.getStringExtra("nama_pembeli"));
        tanggal_pertemuan = (TextView)findViewById(R.id.tanggal_pertemuan);
        alamat = (TextView)findViewById(R.id.alamat);
        btn_calendar = (ImageView)findViewById(R.id.btn_calendar);
        myCalendar = Calendar.getInstance();
        pd = new ProgressDialog(Tambah_Follow_Up.this);
        btn_calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Tambah_Follow_Up.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Tambah_Follow_Up.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        updateLabel(selectedHour, selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        };
    }


    private void updateLabel(int selectedHour, int selectedMinute) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tanggal_pertemuan.setText(sdf.format(myCalendar.getTime())+" "+selectedHour + ":" + selectedMinute);
        try{
            Date strDate = sdf.parse(sdf.format(myCalendar.getTime()));
            if (System.currentTimeMillis() > strDate.getTime()) {
                Log.d("pesan", "tanggal tida valid");
                status = false;
            }else{
                status = true;
            }
        }catch (Exception e){
            Log.d("pesan", "error "+e.getMessage());
        }
    }
    private void simpan(){
        Intent data = getIntent();
        final String kode_pembeliFinal= data.getStringExtra("kode_pembeli");
        final String tanggal_temu = tanggal_pertemuan.getText().toString().trim();
        final String alamat_pertemuan = alamat.getText().toString().trim();
        Log.d("pesan", "nilai value status adalah "+status);
        if(status == false){
            Toast.makeText(this, "Tanggal Pertemuan Tidak Valid", Toast.LENGTH_SHORT).show();
        }else if(kode_pembeliFinal.equals("")){
            Toast.makeText(this, "Nama pembeli masih kosong", Toast.LENGTH_SHORT).show();
        }else if(tanggal_temu.equals("")){
            Toast.makeText(this, "Tanggal pertemuan masih kosong", Toast.LENGTH_SHORT).show();
            tanggal_pertemuan.setFocusable(true);
        }else if(alamat_pertemuan.equals("")){
            Toast.makeText(this, "Alamat masih kosong", Toast.LENGTH_SHORT).show();
            alamat.setFocusable(true);
        }else{
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_KUNJUNGAN+"tambahkunjungan",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Toast.makeText(
                                            Tambah_Follow_Up.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(Tambah_Follow_Up.this, Dashboard.class));

                                } else {
                                    Toast.makeText(
                                            Tambah_Follow_Up.this,
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        Tambah_Follow_Up.this,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(
                                    Tambah_Follow_Up.this,
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(Tambah_Follow_Up.this).getAuthKey());
                    params.put("kode_pembeli", kode_pembeliFinal);
                    params.put("tanggal_pertemuan", tanggal_temu);
                    params.put("kode_karyawan", "2");
                    params.put("alamat_temu", alamat_pertemuan);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }

}
