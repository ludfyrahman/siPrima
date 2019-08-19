package com.android.primaitech.siprima.Lahan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.Divisi.Form_Divisi;
import com.android.primaitech.siprima.Divisi.Temp.Temp_Divisi;
import com.android.primaitech.siprima.Lahan.Temp.Temp_Lahan;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.Unit_Bisnis.Pilih_Unit_Bisnis;
import com.android.primaitech.siprima.Unit_Bisnis.Temp.Temp_Unit_Bisnis;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Form_Lahan extends AppCompatActivity {
    EditText nama_lahan, harga_lahan, alamat, luas_lahan;
    Button simpan;
    ImageView start_date, end_date;
    TextView tgl_mulai_bayar, tgl_akhir_bayar, title;
    boolean statusTanggalSelesai = false, statusTanggalMulai = false;
    //    Date c = Calendar.getInstance().getTime();
    Calendar cal = Calendar.getInstance();
    Date c = cal.getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String now = df.format(c);
    Calendar calendar_mulai, calendar_selesai;
    public static String kode_lahan = "";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lahan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Form_Lahan.this, Lahan.class));
            }
        });
        pd = new ProgressDialog(Form_Lahan.this);
        tgl_mulai_bayar = (TextView)findViewById(R.id.tgl_mulai_bayar);
        tgl_akhir_bayar = (TextView)findViewById(R.id.tgl_akhir_bayar);
        tgl_mulai_bayar.setText(now);
        tgl_akhir_bayar.setText(now);
        nama_lahan = (EditText) findViewById(R.id.nama_lahan);
        harga_lahan = (EditText) findViewById(R.id.harga_lahan);
        alamat = (EditText) findViewById(R.id.alamat);
        luas_lahan = (EditText) findViewById(R.id.luas_lahan);
        nama_lahan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Temp_Lahan.getInstance(getBaseContext()).setNamaLahan(nama_lahan.getText().toString());
            }
        });
        simpan = (Button) findViewById(R.id.simpan);

        calendar_mulai = Calendar.getInstance();
        calendar_selesai = Calendar.getInstance();
        start_date = (ImageView) findViewById(R.id.start_date);
        end_date = (ImageView) findViewById(R.id.end_date);
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Form_Lahan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String getbulan, gettanggal;
                        String myFormat = "dd-MM-yyyy";
                        calendar_mulai.set(Calendar.YEAR, year);
                        calendar_mulai.set(Calendar.MONTH, monthOfYear);
                        calendar_mulai.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        try{
                            Date strDate = sdf.parse(sdf.format(calendar_mulai.getTime()));
                            if (System.currentTimeMillis() > strDate.getTime()) {
                                Log.d("pesan", "tanggal tidak valid");
                                Toast.makeText(getBaseContext(), "Tanggal Mulai Tidak valid", Toast.LENGTH_SHORT).show();
                                statusTanggalMulai = false;
                            }else{
                                Log.d("pesan", "tanggal valid");
                                statusTanggalMulai = true;

                            }
                        }catch (Exception e){
                            Log.d("pesan", "error "+e.getMessage());
                        }
                        if((monthOfYear + 1) >= 10){
                            getbulan = String.valueOf((monthOfYear + 1));
                        }else{
                            getbulan = String.valueOf("0"+(monthOfYear + 1));
                        }

                        if(dayOfMonth >= 10){
                            gettanggal = String.valueOf(dayOfMonth);
                        }else{
                            gettanggal = String.valueOf("0"+dayOfMonth);
                        }
                        tgl_mulai_bayar.setText( gettanggal+ "-" + getbulan + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Form_Lahan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String  getbulan, gettanggal;
                        calendar_selesai.set(Calendar.YEAR, year);
                        calendar_selesai.set(Calendar.MONTH, monthOfYear);
                        calendar_selesai.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd-MM-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        try{
                            Date strDate = sdf.parse(sdf.format(calendar_selesai.getTime()));
                            if (System.currentTimeMillis() > strDate.getTime()) {
                                Log.d("pesan", "tanggal tidak valid");
                                Toast.makeText(getBaseContext(), "Tanggal Selesai Tidak valid", Toast.LENGTH_SHORT).show();
                                statusTanggalSelesai = false;
                            }else{
                                Log.d("pesan", "tanggal valid");
                                statusTanggalSelesai = true;

                            }
                        }catch (Exception e){
                            Log.d("pesan", "error "+e.getMessage());
                        }
                        if((monthOfYear + 1) >= 10){
                            getbulan = String.valueOf((monthOfYear + 1));
                        }else{
                            getbulan = String.valueOf("0"+(monthOfYear + 1));
                        }

                        if(dayOfMonth >= 10){
                            gettanggal = String.valueOf(dayOfMonth);
                        }else{
                            gettanggal = String.valueOf("0"+dayOfMonth);
                        }
                        tgl_akhir_bayar.setText( gettanggal+ "-" + getbulan + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        if (Temp_Lahan.getInstance(getBaseContext()).isExist()) {
            loadData();
        }
        if (Temp_Lahan.getInstance(getBaseContext()).getTipe_form().equals("edit")) {
            loadJson();
            Log.d("pesan", "loadjson fungsi");
            toolbar.setTitle("Ubah Lahan");
        } else {
            toolbar.setTitle("Tambah Lahan");
        }

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Temp_Lahan.getInstance(getBaseContext()).getTipe_form().equals("edit")) {

                    ubah();
                } else {
                    simpan();
                }
            }
        });
    }

    private void loadData() {
        nama_lahan.setText(Temp_Lahan.getInstance(getBaseContext()).getNama_lahan());
        harga_lahan.setText(Temp_Lahan.getInstance(getBaseContext()).getHarga_lahan());
        alamat.setText(Temp_Lahan.getInstance(getBaseContext()).getAlamat());
        tgl_mulai_bayar.setText(Temp_Lahan.getInstance(getBaseContext()).getTgl_mulai_bayar());
        tgl_akhir_bayar.setText(Temp_Lahan.getInstance(getBaseContext()).getTgl_mulai_bayar());
        luas_lahan.setText(Temp_Lahan.getInstance(getBaseContext()).getLuas_lahan());

    }

    private void loadJson() {
        Log.d("pesan", "loadjson fungsi load");
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_LAHAN + "detaillahanlengkap", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_lahan.setText(data.getString("nama_lahan"));
                    Temp_Lahan.getInstance(getBaseContext()).setNamaLahan(data.getString("nama_lahan"));
                    harga_lahan.setText(data.getString("harga_lahan"));
                    Temp_Lahan.getInstance(getBaseContext()).setHargaLahan(data.getString("harga_lahan"));
                    alamat.setText(data.getString("alamat"));
                    Temp_Lahan.getInstance(getBaseContext()).setAlamat(data.getString("alamat"));
                    tgl_mulai_bayar.setText(data.getString("tgl_mulai_bayar"));
                    Temp_Lahan.getInstance(getBaseContext()).setTanggalMulai(data.getString("tgl_mulai_bayar"));
                    tgl_akhir_bayar.setText(data.getString("tgl_akhir_bayar"));
                    Temp_Lahan.getInstance(getBaseContext()).setTanggalAkhir(data.getString("tgl_akhir_bayar"));
                    luas_lahan.setText(data.getString("luas_lahan"));
                    Temp_Lahan.getInstance(getBaseContext()).setLuasLahan(data.getString("luas_lahan"));
                    kode_lahan = data.getString("kode_lahan");
                    Temp_Lahan.getInstance(getBaseContext()).setKodeLahan(data.getString("kode_lahan"));
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
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("kode_lahan", Temp_Lahan.getInstance(getBaseContext()).getKode_lahan());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }


    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Lahan.class));
    }

    private void simpan() {
        final String nm_lahan = nama_lahan.getText().toString().trim();
        final String lahan = harga_lahan.getText().toString().trim();
        final String almat = alamat.getText().toString().trim();
        final String tanggal_mulai = tgl_mulai_bayar.getText().toString().trim();
        final String tanggal_akhir = tgl_akhir_bayar.getText().toString().trim();
        final String luas = luas_lahan.getText().toString().trim();
        if (nm_lahan.equals("")) {
            Toast.makeText(this, "Nama Lahan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_lahan.setFocusable(true);
        } else if (lahan.equals("")) {
            Toast.makeText(this, "Harga Lahan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            harga_lahan.setFocusable(true);
        }else if (almat.equals("")) {
            Toast.makeText(this, "Alamat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            alamat.setFocusable(true);
        }else if (tanggal_mulai.equals("")) {
            Toast.makeText(this, "Tanggal Mulai Bayar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            tgl_mulai_bayar.setFocusable(true);
        }else if (tanggal_akhir.equals("")) {
            Toast.makeText(this, "Tanggal Akhir Bayar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            tgl_akhir_bayar.setFocusable(true);
        }else if (luas.equals("")) {
            Toast.makeText(this, "Luas Lahan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            luas_lahan.setFocusable(true);
        } else {
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_LAHAN + "prosestambahlahan",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Lahan.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Lahan.class));
                                } else {
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        getBaseContext(),
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
                                    getBaseContext(),
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                    params.put("nama_lahan", nm_lahan);
                    params.put("harga_lahan", lahan);
                    params.put("alamat", almat);
                    params.put("tgl_mulai_bayar", tanggal_mulai);
                    params.put("tgl_akhir_bayar", tanggal_akhir);
                    params.put("luas_lahan", luas);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }

    private void ubah() {

        final String nm_lahan = nama_lahan.getText().toString().trim();
        final String lahan = harga_lahan.getText().toString().trim();
        final String almat = alamat.getText().toString().trim();
        final String tanggal_mulai = tgl_mulai_bayar.getText().toString().trim();
        final String tanggal_akhir = tgl_akhir_bayar.getText().toString().trim();
        final String luas = luas_lahan.getText().toString().trim();
        if (nm_lahan.equals("")) {
            Toast.makeText(this, "Nama Lahan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            nama_lahan.setFocusable(true);
        } else if (lahan.equals("")) {
            Toast.makeText(this, "Harga Lahan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            harga_lahan.setFocusable(true);
        }else if (almat.equals("")) {
            Toast.makeText(this, "Alamat Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            alamat.setFocusable(true);
        }else if (tanggal_mulai.equals("")) {
            Toast.makeText(this, "Tanggal Mulai Bayar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            tgl_mulai_bayar.setFocusable(true);
        }else if (tanggal_akhir.equals("")) {
            Toast.makeText(this, "Tanggal Akhir Bayar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            tgl_akhir_bayar.setFocusable(true);
        }else if (luas.equals("")) {
            Toast.makeText(this, "Luas Lahan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            luas_lahan.setFocusable(true);
        } else {
            pd.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    ServerAccess.URL_LAHAN + "prosespengajuanulang",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                JSONObject data = obj.getJSONObject("respon");
                                if (data.getBoolean("status")) {
                                    Temp_Lahan.getInstance(getBaseContext()).clear();
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    startActivity(new Intent(getBaseContext(), Lahan.class));
                                } else {
                                    Toast.makeText(
                                            getBaseContext(),
                                            data.getString("pesan"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {

                                Toast.makeText(
                                        getBaseContext(),
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
                                    getBaseContext(),
                                    "error",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                    params.put("nama_lahan", nm_lahan);
                    params.put("harga_lahan", lahan);
                    params.put("alamat", almat);
                    params.put("tgl_mulai_bayar", tanggal_mulai);
                    params.put("tgl_akhir_bayar", tanggal_akhir);
                    params.put("luas_lahan", luas);
                    params.put("kode_lahan", Temp_Lahan.getInstance(getBaseContext()).getKode_lahan());
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}