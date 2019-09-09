package com.primagroup.primaitech.siprima.Kegiatan;

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
import android.widget.EditText;
import android.widget.Toast;

import com.primagroup.primaitech.siprima.Config.AppController;
import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Form_Kegiatan extends BottomSheetDialogFragment {
    EditText kegiatan;
    ProgressDialog pd;
    Button simpan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tambah_kegiatan, container, false);
        kegiatan = (EditText)v.findViewById(R.id.kegiatan);
        simpan = (Button)v.findViewById(R.id.simpan);
        pd = new ProgressDialog(getActivity());
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
        return  v;
    }
    private void simpan(){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KEGIATAN+"tambahkegiatan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject arr = res.getJSONObject("respon");

                    if(arr.getBoolean("status")){
                        Toast.makeText(getActivity(), arr.getString("pesan"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Dashboard.class);
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
                params.put("kegiatan", kegiatan.getText().toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }

}
