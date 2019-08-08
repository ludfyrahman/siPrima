package com.android.primaitech.siprima.Penjualan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Terima_Penjualan extends BottomSheetDialogFragment {
    EditText presentase_komisi;
    ProgressDialog pd;
    Button simpan;
    String kode_akunbank = "";
    Button btn_akun_bank;
    ArrayList<String> dataAkunBank=new ArrayList<String>();CharSequence[] itemkavling = {};
    ArrayList<String> indexdatabank=new ArrayList<String>();
    TextView akun_bank;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_terima_penjualan, container, false);
        presentase_komisi = (EditText)v.findViewById(R.id.presentase_komisi);
        akun_bank = (TextView) v.findViewById(R.id.akun_bank);

        simpan = (Button)v.findViewById(R.id.simpan);
        btn_akun_bank = (Button)v.findViewById(R.id.btn_akun_bank);
        loadAkunBank();
        btn_akun_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
                pictureDialog.setTitle("Pilih Akun Bank");
                pictureDialog.setItems(dataAkunBank.toArray(new String[0]),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                kode_akunbank = indexdatabank.get(which);
                                Log.e("kodenya",""+kode_akunbank);
                                akun_bank.setText(dataAkunBank.get(which));

                            }
                        });
                pictureDialog.show();
            }
        });
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
        final String kode = getArguments().getString("kode");
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PENJUALAN + "detailpenjualan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    if(res.getString("databank") != "null") {
                        JSONArray arr = res.getJSONArray("databank");
                        if(arr.length() > 0) {
                            for (int i = 0; i < arr.length(); i++) {
                                try {
                                    JSONObject data = arr.getJSONObject(i);
                                    Log.d("pesan", "nama rekening "+data.getString("nama_rekening"));
                                    String ku = data.getString("nama_rekening") + " / " + data.getString("nama_bank")+ " / " + data.getString("no_rekening");
                                    String koded = data.getString("kode_akunbank");
                                    dataAkunBank.add(ku);
                                    indexdatabank.add(koded);
                                } catch (Exception ea) {
                                    ea.printStackTrace();
                                    Log.d("error" ,ea.getMessage());
                                }
                            }
                        }else{
                            Toast.makeText(getContext(), "Data Akun Bank Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Data Akun Bank Kosong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Data Akun Bank Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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
                    params.put("kodepenjualan", getArguments().getString("kode"));
                    params.put("persen_komisi", presentase_komisi.getText().toString());
                    params.put("kode_akunbank", kode_akunbank);
                    return params;
                }
            };

            RequestHandler.getInstance(getContext()).addToRequestQueue(senddata);
        }
    }

}
