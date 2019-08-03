package com.android.primaitech.siprima.Pembeli;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Penjualan.Detail_Penjualan;
import com.android.primaitech.siprima.Penjualan.Model.Riyawat_Pembayaran_Model;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Terima_Penjualan extends BottomSheetDialogFragment {
    EditText presentase_komisi;
    Spinner akun_bank;
    ProgressDialog pd;
    Button simpan;
    String kode_akunbank = "";
    Detail_Penjualan detail_penjualan = new Detail_Penjualan();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_terima_penjualan, container, false);
        presentase_komisi = (EditText)v.findViewById(R.id.presentase_komisi);

        simpan = (Button)v.findViewById(R.id.simpan);
        pd = new ProgressDialog(getActivity());
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terimaPenjualan();
            }
        });
        return  v;
    }
    private void loadAkunBank(){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final String kode = detail_penjualan.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PENJUALAN + "detailpenjualan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    if(res.getString("databank") != "null") {
                        JSONArray arr = res.getJSONArray("databank");
                        if(arr.length() > 0) {
                            for (int i = 0; i < arr.length(); i++) {
                                try {
                                    JSONObject data = arr.getJSONObject(i);

                                } catch (Exception ea) {
                                    ea.printStackTrace();
                                    pd.cancel();
                                    Log.d("error" ,ea.getMessage());
                                }
                            }
                            pd.cancel();
                        }else{
                            pd.cancel();
                        }
                    }else{
                        pd.cancel();
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
                params.put("kodepenjualan", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    private void terimaPenjualan(){
        pd.setMessage("Proses...");
        pd.setCancelable(false);
        pd.show();
        if (presentase_komisi.getText().toString().equals("")){
            Toast.makeText(getContext(), "Presentase Komisi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PENJUALAN+"terimapenjualan", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject res = null;
                    try {
                        pd.cancel();
                        res = new JSONObject(response);
                        JSONObject data = res.getJSONObject("respon");
                        if (data.getBoolean("status")) {
                            Toast.makeText(
                                    getContext(),
                                    data.getString("pesan"),
                                    Toast.LENGTH_LONG
                            ).show();
                            getActivity().onBackPressed();
//                                    startActivity(new Intent(Tambah_Follow_Up.this, Dashboard.class));

                        } else {
                            Toast.makeText(
                                    getContext(),
                                    data.getString("pesan"),
                                    Toast.LENGTH_LONG
                            ).show();
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
                    params.put("persen_komisi", presentase_komisi.getText().toString());
                    params.put("kode_akunbank", "YEP73IIC");
                    return params;
                }
            };

            RequestHandler.getInstance(getContext()).addToRequestQueue(senddata);
        }
    }

}
