package com.android.primaitech.siprima.Pembeli;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AlertReceiver;
import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Divisi.Adapter.Adapter_Divisi;
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.Divisi.Model.Divisi_Model;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.Pembeli.Adapter.Adapter_Kunjungan_Pembeli;
import com.android.primaitech.siprima.Pembeli.Model.Kunjungan_Pembeli_Model;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kunjungan_Pembeli extends AppCompatActivity {
    public static String buat, edit, hapus, detail;
    FloatingActionButton tambah;
    private Adapter_Kunjungan_Pembeli adapter;
    private List<Kunjungan_Pembeli_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    LinearLayout not_found;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    TextView textnya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunjungan_pembeli);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        final Intent data = getIntent();
        toolbar.setTitle("Follow Up "+data.getStringExtra("nama_pembeli"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kunjungan_Pembeli.this.onBackPressed();
            }
        });
//        textnya = (TextView)findViewById(R.id.textnya);
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        tambah = (FloatingActionButton)findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Tambah_Follow_Up.class);
                intent.putExtra("nama_pembeli", data.getStringExtra("nama_pembeli"));
                intent.putExtra("kode_pembeli", data.getStringExtra("kode_pembeli"));
                intent.putExtra("code", "1");
                startActivity(intent);
            }
        });
        not_found = (LinearLayout)findViewById(R.id.not_found);
        list = new ArrayList<>();
        adapter = new Adapter_Kunjungan_Pembeli(Kunjungan_Pembeli.this,(ArrayList<Kunjungan_Pembeli_Model>) list);
        mManager = new LinearLayoutManager(Kunjungan_Pembeli.this,LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        pd = new ProgressDialog(Kunjungan_Pembeli.this);
        loadJson();
        refresh = (FrameLayout) findViewById(R.id.refresh);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
    }

    public void reload(){
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }

    public  void delete(final String kode){

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KUNJUNGAN+"deletekunjungan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "errornya : " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("kodekunjungan", kode);
                return params;
            }
        };
        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
//        startActivity(new Intent(getBaseContext(), Akun_bank.class));
    }
    public void timeSet(int hourOfDay, int minute, int day, int month, int year, String kode) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.SECOND, 0);
        Log.d("pesan", "datanya adalah "+year+"-"+month+"-"+day+" "+hourOfDay+":"+minute);
        updateTimeText(c);
        startAlarm(c, kode);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTimeInMillis());

//        textnya.setText(timeText);
    }

    private void startAlarm(Calendar c, String kode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("kode", kode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 22);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        Intent data = getIntent();
        final String kode_pembeli = data.getStringExtra("kode_pembeli");
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
                    if(arr.length() > 0) {
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject data = arr.getJSONObject(i);
                                Kunjungan_Pembeli_Model md = new Kunjungan_Pembeli_Model();
                                md.setKode_kunjungan(data.getString("kode_kunjungan"));
                                md.setNama_karyawan(data.getString("nama_karyawan"));
                                md.setAlamat_temu(data.getString("alamat_temu"));
                                md.setTanggal_pertemuan(ServerAccess.parseDate(data.getString("tanggal_pertemuan")));
                                md.setStatus(ServerAccess.statusFollowUp[data.getInt("status")]);
                                md.setProspek(ServerAccess.prospek[data.getInt("prospek")]);
                                md.setNama_pembeli(data.getString("nama_pembeli"));
                                String date = data.getString("tanggal_pertemuan");
                                String[] datelist = date.split(" ");
                                String tanggal = datelist[0];
                                String[] tanggalList = tanggal.split("-");
                                String time = datelist[1];
                                String[] timeList = time.split(":");
                                int tahun = Integer.parseInt(tanggalList[0]);
                                int bulan = Integer.parseInt(tanggalList[1]);
                                int tglhari = Integer.parseInt(tanggalList[2]);
                                int jam = Integer.parseInt(timeList[0]);
                                int menit = Integer.parseInt(timeList[1]);

                                timeSet(jam, menit, tglhari, bulan, tahun, data.getString("kode_kunjungan"));
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();

                            }
                        }
                        pd.cancel();
                        adapter.notifyDataSetChanged();
                    }else{
                        pd.cancel();
                        not_found.setVisibility(View.VISIBLE);
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
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("tipedata", "kunjungan_pembeli");
                params.put("kode_pembeli", kode_pembeli);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}
