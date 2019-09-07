package com.android.primaitech.siprima.Hpp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
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
import com.android.primaitech.siprima.Cuti.Cuti;
import com.android.primaitech.siprima.Cuti.Temp.Temp_Cuti;
import com.android.primaitech.siprima.Hpp.Temp.Temp_Hpp;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.Tipe_Rumah.Tipe_Rumah;
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

public class Form_Hpp_Proyek extends BottomSheetDialogFragment {
    EditText nama_biaya, jumlah_biaya;
    ProgressDialog pd;
    Button simpan;
    TextView tanggal_mulai, tanggal_selesai, title;
    //    Date c = Calendar.getInstance().getTime();
    Calendar cal = Calendar.getInstance();
    Date c = cal.getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String now = df.format(c);
    Calendar calendar_mulai, calendar_selesai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_form_hpp_proyek, container, false);
        title = (TextView)v.findViewById(R.id.title);
        nama_biaya = (EditText)v.findViewById(R.id.nama_biaya);
        jumlah_biaya = (EditText)v.findViewById(R.id.jumlah_biaya);
        simpan = (Button)v.findViewById(R.id.simpan);
        pd = new ProgressDialog(getActivity());
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Temp_Hpp.getInstance(getContext()).getTipe_form().equals("edit")){
                    ubah();
                }else{
                    simpan();
                }
            }
        });
        if (Temp_Hpp.getInstance(getContext()).getTipe_form().equals("edit")){
            loadJson();
            title.setText("Ubah Hpp Proyek");
        }else{
            title.setText("Tambah Hpp Proyek ");
        }
        return  v;
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_HPP+"detailtipe", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_biaya.setText(ServerAccess.Inddate(data.getString("nama_biaya")));
//                    Temp_Cuti.getInstance(getContext()).setTanggalAwal(data.getString("tgl_awal"));
                    jumlah_biaya.setText(ServerAccess.Inddate(data.getString("jumlah_biaya")));
//                    Temp_Cuti.getInstance(getContext()).setTanggalSelesai(data.getString("tgl_akhir"));
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
        final String nm_biaya = nama_biaya.getText().toString();
        final String jml_biaya = jumlah_biaya.getText().toString();
        if (nm_biaya.equals("")){
            Toast.makeText(getContext(), "Nama Biaya boleh kosong", Toast.LENGTH_SHORT).show();
            nama_biaya.setFocusable(true);
        }else if (jml_biaya.equals("")){
            Toast.makeText(getContext(), "Jumlah Biaya Tidak boleh kosong", Toast.LENGTH_SHORT).show();
            jumlah_biaya.setFocusable(true);
        }else{
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_HPP+"savehpp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject arr = res.getJSONObject("respon");

                    if(arr.getBoolean("status")){
                        Toast.makeText(getActivity(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Tipe_Rumah.class);
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
                params.put("tipe", "1");
                params.put("kode_produk", Temp_Hpp.getInstance(getContext()).getKode_produk());
                params.put("nama_biaya", nm_biaya);
                params.put("jumlah_biaya", jml_biaya);
                return params;
            }
        };

            AppController.getInstance().addToRequestQueue(senddata);
        }
    }
    private void ubah(){
        final String nm_biaya = nama_biaya.getText().toString();
        final String jml_biaya = jumlah_biaya.getText().toString();
        if (nm_biaya.equals("")){
            Toast.makeText(getContext(), "Nama Biaya boleh kosong", Toast.LENGTH_SHORT).show();
            nama_biaya.setFocusable(true);
        }else if (jml_biaya.equals("")){
            Toast.makeText(getContext(), "Jumlah Biaya Tidak boleh kosong", Toast.LENGTH_SHORT).show();
            jumlah_biaya.setFocusable(true);
        }else{
            pd.setMessage("Menampilkan Data");
            pd.setCancelable(false);
            pd.show();
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_HPP+"updatehpp", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        JSONObject arr = res.getJSONObject("respon");

                        if(arr.getBoolean("status")){
                            Toast.makeText(getActivity(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Tipe_Rumah.class);
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
                    params.put("kode_hpp", Temp_Hpp.getInstance(getContext()).getKode_hpp());
                    params.put("nama_biaya", nm_biaya);
                    params.put("jumlah_biaya", jml_biaya);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(senddata);
        }
    }

}
