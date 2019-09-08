package com.android.primaitech.siprima.Cuti;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.primaitech.siprima.Cuti.Temp.Temp_Cuti;
import com.android.primaitech.siprima.R;
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

public class Form_Cuti extends BottomSheetDialogFragment {
    EditText kegiatan;
    ProgressDialog pd;
    Button simpan;
    ImageView calendar_tanggal_mulai, calendar_tanggal_selesai;
    TextView tanggal_mulai, tanggal_selesai, title;
    boolean statusTanggalSelesai = false, statusTanggalMulai = false;
//    Date c = Calendar.getInstance().getTime();
    Calendar cal = Calendar.getInstance();
    Date c = cal.getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String now = df.format(c);
    Calendar calendar_mulai, calendar_selesai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_form_cuti, container, false);
        kegiatan = (EditText)v.findViewById(R.id.kegiatan);
        title = (TextView)v.findViewById(R.id.title);
        tanggal_mulai = (TextView)v.findViewById(R.id.tanggal_mulai);
        tanggal_selesai = (TextView)v.findViewById(R.id.tanggal_selesai);
        tanggal_mulai.setText(now);
        tanggal_selesai.setText(now);

        calendar_mulai = Calendar.getInstance();
        calendar_selesai = Calendar.getInstance();
        calendar_tanggal_mulai = (ImageView) v.findViewById(R.id.calendar_tanggal_mulai);
        calendar_tanggal_selesai = (ImageView) v.findViewById(R.id.calendar_tanggal_selesai);
        calendar_tanggal_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                                Toast.makeText(getContext(), "Tanggal Mulai Tidak valid", Toast.LENGTH_SHORT).show();
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
                        tanggal_mulai.setText( gettanggal+ "-" + getbulan + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        calendar_tanggal_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                                Toast.makeText(getContext(), "Tanggal Selesai Tidak valid", Toast.LENGTH_SHORT).show();
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
                        tanggal_selesai.setText( gettanggal+ "-" + getbulan + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        simpan = (Button)v.findViewById(R.id.simpan);
        pd = new ProgressDialog(getActivity());
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Temp_Cuti.getInstance(getContext()).getTipe_form().equals("edit")){
                    ubah();
                }else{
                    simpan();
                }
            }
        });
        if (Temp_Cuti.getInstance(getContext()).getTipe_form().equals("edit")){
            loadJson();
            title.setText("Ubah Akun Bank ");
        }else{
            title.setText("Tambah Akun Bank ");
        }
        return  v;
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CUTI+"detailcuti", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    tanggal_mulai.setText(ServerAccess.Inddate(data.getString("tgl_awal")));
                    Temp_Cuti.getInstance(getContext()).setTanggalAwal(data.getString("tgl_awal"));
                    tanggal_selesai.setText(ServerAccess.Inddate(data.getString("tgl_akhir")));
                    Temp_Cuti.getInstance(getContext()).setTanggalSelesai(data.getString("tgl_akhir"));
                    kegiatan.setText(data.getString("keterangan"));
                    Temp_Cuti.getInstance(getContext()).setKegiatan(data.getString("keterangan"));
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
                params.put("kodedetailcuti", getArguments().getString("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(senddata);
    }
    private void simpan(){
//        if (statusTanggalMulai == false){
//            Toast.makeText(getContext(), "Tanggal Mulai Tidak valid", Toast.LENGTH_SHORT).show();
//        }else if (statusTanggalSelesai == false){
//            Toast.makeText(getContext(), "Tanggal Selesai Tidak valid", Toast.LENGTH_SHORT).show();
//        }else{
            pd.setMessage("Menampilkan Data");
            pd.setCancelable(false);
            pd.show();
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CUTI+"tambahcuti", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        JSONObject arr = res.getJSONObject("respon");

                        if(arr.getBoolean("status")){
                            Toast.makeText(getActivity(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Cuti.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                        }
                        pd.cancel();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pd.cancel();
                        Log.d("pesan", "error "+e.getMessage());
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
                    params.put("kode", AuthData.getInstance(getActivity()).getAuthKey());
                    params.put("kode_karyawan", AuthData.getInstance(getActivity()).getAksesData());
                    params.put("keterangan", kegiatan.getText().toString());
                    params.put("tgl_awal", ServerAccess.dateFormat(tanggal_mulai.getText().toString()));
                    params.put("tgl_akhir", ServerAccess.dateFormat(tanggal_selesai.getText().toString()));
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(senddata);
//        }
    }
    private void ubah(){
//        if (statusTanggalMulai == false){
//            Toast.makeText(getContext(), "Tanggal Mulai Tidak valid", Toast.LENGTH_SHORT).show();
//        }else if (statusTanggalSelesai == false){
//            Toast.makeText(getContext(), "Tanggal Selesai Tidak valid", Toast.LENGTH_SHORT).show();
//        }else{
            pd.setMessage("Menampilkan Data");
            pd.setCancelable(false);
            pd.show();
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_CUTI+"updatecuti", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        JSONObject arr = res.getJSONObject("respon");

                        if(arr.getBoolean("status")){
                            Toast.makeText(getActivity(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Cuti.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                        }
                        pd.cancel();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pd.cancel();
                        Log.d("pesan", "error "+e.getMessage());
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
                    params.put("kode", AuthData.getInstance(getActivity()).getAuthKey());
                    params.put("kode_karyawan", AuthData.getInstance(getActivity()).getAksesData());
                    params.put("tgl_awal", ServerAccess.dateFormat(tanggal_mulai.getText().toString()));
                    params.put("tgl_akhir", ServerAccess.dateFormat(tanggal_selesai.getText().toString()));
                    params.put("keterangan", kegiatan.getText().toString());
                    params.put("kodecuti", Temp_Cuti.getInstance(getContext()).getKode_cuti());
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(senddata);
//        }
    }

}
