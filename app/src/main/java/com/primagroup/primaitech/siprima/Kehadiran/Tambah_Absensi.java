package com.primagroup.primaitech.siprima.Kehadiran;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.core.app.ActivityCompat;

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

public class Tambah_Absensi extends BottomSheetDialogFragment {
    private LocationManager locationManager;
    private LocationListener listener;
    double longitude = 0, latitude = 0;
    EditText keterangan;
    ProgressDialog pd;
    Button simpan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tambah_absensi, container, false);
        keterangan = (EditText)v.findViewById(R.id.keterangan);
        simpan = (Button)v.findViewById(R.id.simpan);
        pd = new ProgressDialog(getActivity());
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Pesan", "\n " + location.getLongitude() + " " + location.getLatitude());
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });
        configure_button();
        return  v;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.

        //noinspection MissingPermission
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);

    }
    private void simpan(){
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KEGIATAN+"absenharian", new Response.Listener<String>() {
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
                params.put("keterangan", keterangan.getText().toString());
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }

}
