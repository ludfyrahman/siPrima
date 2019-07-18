package com.android.primaitech.siprima.Akun;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.MainActivity;
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

public class Login extends AppCompatActivity {
    EditText email, password;
    Button signin;
    static final int PERMISSION_READ_STATE = 0;
    String imei = "";
    ProgressDialog pd;
    public String xdatauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signin = (Button) findViewById(R.id.btnSigin);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        pd = new ProgressDialog(Login.this);
        pd.setMessage("Please Wait...");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        getImei();
        onLogin();
    }
    public void getImei() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            telephoneManager();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_READ_STATE);
        }
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo_app)
                .setTitle("Keluar Aplikasi")
                .setMessage("Apakah Anda Yakin Ingin Keluar Dari Aplikasi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(Login.this);
                        finish();


                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    telephoneManager();
                } else {
                    Toast.makeText(this, "tidak memiliki ijin", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void telephoneManager() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "di dalam", Toast.LENGTH_SHORT).show();
            return;
        }
        String phoneType = telephonyManager.getDeviceId();
        imei = phoneType;
//        etUsername.setText(phoneType);
//        Toast.makeText(this, "di dalam "+phoneType, Toast.LENGTH_LONG).show();
    }
    private void onLogin(){
        if(AuthData.getInstance(this).isLoggedIn()){
            Login.this.finish();
            startActivity(new Intent(getBaseContext(), Dashboard.class));
        }
    }
    private void userLogin(){
        pd.setMessage("Authenticating...");
        pd.setCancelable(false);
        pd.show();
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
            pd.dismiss();

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

            Toast.makeText(getBaseContext(), "Email Tidak Valid", Toast.LENGTH_LONG).show();
            pd.dismiss();
        } else if (password.getText().toString().isEmpty()) {
            Toast.makeText(getBaseContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
            pd.dismiss();

        } else{
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pd.cancel();
                    try {
                        JSONObject res = new JSONObject(response);

                        JSONObject respon = res.getJSONObject("respon");
                        if(respon.getBoolean("status")){
                            Intent intent = new Intent(getBaseContext(), Dashboard.class);
                            startActivity(intent);
                            JSONObject datalogin = res.getJSONObject("datauser");
                            JSONObject datauth = res.getJSONObject("dataauth");
                            AuthData.getInstance(getApplicationContext()).setdataauth(
                                    datauth.getString("kode_auth"),
                                    datauth.getString("kode_user"),
                                    datauth.getString("expired_date"),
                                    "y"

                            );
                            xdatauth = datauth.getString("kode_auth");
//                            KirimTOken();
                            AuthData.getInstance(getApplicationContext()).setdatauser(
                                    datauth.getString("kode_auth"),
                                    datalogin.getString("kode_role"),
                                    datalogin.getString("kode_user"),
                                    datalogin.getString("nama_user"),
                                    datalogin.getString("kode_unit"),
                                    datalogin.getString("kode_proyek"),
                                    datalogin.getString("email"),
                                    datalogin.getString("akses_data")
                            );
                        }else{
                            Toast.makeText(getBaseContext(), respon.getString("pesan"), Toast.LENGTH_SHORT).show();

                        }

                        pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.cancel();

                    Log.e("errornyaa ", "" + error);
                    Toast.makeText(getBaseContext(), "Gagal Login, "+error, Toast.LENGTH_SHORT).show();


                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());

                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(senddata);
        }
    }
    public void KirimTOken(){
        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAccess.UPDATE_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    JSONObject respon = res.getJSONObject("respon");
                    if (respon.getBoolean("savedata")) {
//                        Toast.makeText(MainActivity.this, respon.getString("pesan"), Toast.LENGTH_SHORT).show();

                    } else {
//                        Toast.makeText(MainActivity.this, respon.getString("pesan"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Niih errornya", "" + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Gagal mengambil token, "+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Context datauth = null;
                params.put("kode", xdatauth);
//                params.put("token", FirebaseInstanceId.getInstance().getToken());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(sendData);
    }
}
