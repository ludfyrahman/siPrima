package com.primagroup.primaitech.siprima.Akun_Bank;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.MenuData;
import com.primagroup.primaitech.siprima.Config.RequestHandler;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.primagroup.primaitech.siprima.Config.Stack_Menu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Detail_Akun_Bank extends AppCompatActivity {
    ProgressDialog pd;
    TextView nama_rekening, nama_bank, no_rekening, saldo, status, tanggal;
    Button follow_up;
    String kode_pembeli = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_akun_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Detail Akun Bank " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Akun_bank.class));
                MenuData menuData = new MenuData();
                Intent intent = new Intent(v.getContext(), menuData.halaman(Stack_Menu.TampilkanKodeMenuTeratas()));
                intent.putExtra("kode_menu", Stack_Menu.TampilkanKodeMenuTeratas());
                intent.putExtra("nama_menu",Stack_Menu.TampilkanNamaMenuTeratas());
                v.getContext().startActivity(intent);
            }
        });
        pd = new ProgressDialog(Detail_Akun_Bank.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        nama_rekening = (TextView)findViewById(R.id.nama_rekening);
        nama_bank = (TextView)findViewById(R.id.nama_bank);
        no_rekening = (TextView)findViewById(R.id.no_rekening);
        saldo = (TextView)findViewById(R.id.saldo);
        status = (TextView)findViewById(R.id.status);
        tanggal = (TextView)findViewById(R.id.tanggal);
        loadJson();
    }

    @Override
    public void onBackPressed() {
//        spv_dev_list_komplain.this.finish();
        startActivity(new Intent(getBaseContext(), Akun_bank.class));
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_AKUN_BANK+"detail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_rekening.setText(data.getString("nama_rekening"));
                    nama_bank.setText(data.getString("nama_bank"));
                    no_rekening.setText(data.getString("no_rekening"));
                    saldo.setText(ServerAccess.numberConvert(data.getString("saldo")));
                    status.setText(data.getString("status"));
                    tanggal.setText(ServerAccess.parseDate(data.getString("create_at")));
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
                params.put("kode_akun", data.getStringExtra("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
