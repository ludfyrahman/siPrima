package com.primagroup.primaitech.siprima.Karyawan;

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

public class Detail_Karyawan extends AppCompatActivity {
    ProgressDialog pd;
    TextView nama_karyawan, no_karyawan, nik, nohp , nohp_darurat, jml_tanggungan, gender, ttl, st_menikah, nama_pasangan,
            jml_anak, alamat, jabatan, gaji, gaji_pokok, gaji_tunjangan, gaji_lainnya, gaji_potongan, no_rek, nama_rek, nama_bank, jatah_cuti, sisa_cuti,
            status, catatan, tipe_karyawan, tgl_gabung, nama_divisi, nama_proyek, nama_unit;
    Button follow_up;
    String kode_pembeli = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karyawan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Detail_Karyawan.this);
        toolbar.setNavigationIcon(R.drawable.backward);
        nama_karyawan = (TextView)findViewById(R.id.nama_karyawan);
        no_karyawan = (TextView)findViewById(R.id.no_karyawan);
        nik = (TextView)findViewById(R.id.nik);
        nohp = (TextView)findViewById(R.id.nohp);
        nohp_darurat = (TextView)findViewById(R.id.nohp_darurat);
        jml_tanggungan = (TextView)findViewById(R.id.jml_tanggungan);
        gender = (TextView)findViewById(R.id.gender);
        ttl = (TextView)findViewById(R.id.ttl);
        st_menikah = (TextView)findViewById(R.id.st_menikah);
        nama_pasangan = (TextView)findViewById(R.id.nama_pasangan);
        jml_anak = (TextView)findViewById(R.id.jml_anak);
        alamat = (TextView)findViewById(R.id.alamat);
        jabatan = (TextView)findViewById(R.id.jabatan);
        gaji = (TextView)findViewById(R.id.gaji);
        gaji_pokok = (TextView)findViewById(R.id.gaji_pokok);
        gaji_tunjangan = (TextView)findViewById(R.id.gaji_tunjangan);
        gaji_lainnya = (TextView)findViewById(R.id.gaji_lainnya);
        gaji_potongan = (TextView)findViewById(R.id.gaji_potongan);
        no_rek = (TextView)findViewById(R.id.no_rek);
        nama_rek = (TextView)findViewById(R.id.nama_rek);
        nama_bank = (TextView)findViewById(R.id.nama_bank);
        jatah_cuti = (TextView)findViewById(R.id.jatah_cuti);
        sisa_cuti = (TextView)findViewById(R.id.sisa_cuti);
        status = (TextView)findViewById(R.id.status);
        catatan = (TextView)findViewById(R.id.catatan);
        tipe_karyawan = (TextView)findViewById(R.id.tipe_karyawan);
        tgl_gabung = (TextView)findViewById(R.id.tgl_gabung);
        nama_divisi = (TextView)findViewById(R.id.nama_divisi);
        nama_proyek = (TextView)findViewById(R.id.nama_proyek);
        nama_unit = (TextView)findViewById(R.id.nama_unit);
        loadJson();

        Intent data = getIntent();
        if(data.hasExtra("nama_menu")) {
            toolbar.setTitle("Detail Karyawan " + data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Karyawan.class));
                MenuData menuData = new MenuData();
                Intent intent = new Intent(v.getContext(), menuData.halaman(Stack_Menu.TampilkanKodeMenuTeratas()));
                intent.putExtra("kode_menu", Stack_Menu.TampilkanKodeMenuTeratas());
                intent.putExtra("nama_menu",Stack_Menu.TampilkanNamaMenuTeratas());
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
//        spv_dev_list_komplain.this.finish();
        startActivity(new Intent(getBaseContext(), Karyawan.class));
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        final Intent data = getIntent();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KARYAWAN+"detailkaryawan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    JSONObject d = data.getJSONObject("list_gaji");
                    nama_karyawan.setText(data.getString("nama_karyawan"));
                    no_karyawan.setText(data.getString("no_karyawan"));
                    nik.setText(data.getString("nik"));
                    nohp.setText(data.getString("nohp"));
                    nohp_darurat.setText(data.getString("nohp_darurat"));
                    jml_tanggungan.setText(data.getString("jml_tanggungan"));
                    gender.setText(ServerAccess.gender[data.getInt("gender")]);
                    ttl.setText(data.getString("tmp_lahir")+", "+data.getString("tgl_lahir"));
                    st_menikah.setText(data.getString("st_menikah"));
                    nama_pasangan.setText(data.getString("nama_pasangan"));
                    jml_anak.setText(data.getString("jml_anak"));
                    alamat.setText(data.getString("alamat"));
                    jabatan.setText(data.getString("jabatan"));
                    gaji.setText(ServerAccess.numberConvert(data.getString("gaji")));
                    gaji_pokok.setText(d.getString("gaji_pokok"));
                    gaji_tunjangan.setText(ServerAccess.numberConvert(d.getString("gaji_tunjangan")));
                    gaji_lainnya.setText(ServerAccess.numberConvert(d.getString("gaji_lainnya")));
                    gaji_potongan.setText(ServerAccess.numberConvert(d.getString("gaji_potongan")));
                    no_rek.setText(data.getString("no_rek"));
                    nama_rek.setText(data.getString("nama_rek"));
                    nama_bank.setText(data.getString("nama_bank"));
                    jatah_cuti.setText(data.getString("jatah_cuti"));
                    sisa_cuti.setText(data.getString("sisa_cuti"));
                    status.setText(ServerAccess.status_karyawan[data.getInt("status")]);
                    catatan.setText(data.getString("catatan"));
                    tipe_karyawan.setText(ServerAccess.tipe_karyawan[data.getInt("tipe_karyawan")]);
                    tgl_gabung.setText(ServerAccess.parseDate(data.getString("tgl_gabung")));
                    nama_divisi.setText(data.getString("nama_divisi"));
                    nama_proyek.setText(data.getString("nama_proyek"));
                    nama_unit.setText(data.getString("nama_unit"));
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
                params.put("kode_karyawan", data.getStringExtra("kode"));
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
}
