package com.android.primaitech.siprima.Pembeli;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.primaitech.siprima.Config.AlertReceiver;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Follow_Up.Detail_Follow_Up;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.Follow_Up.Temp_Follow_Up;
import com.android.primaitech.siprima.Pembeli.Adapter.Adapter_Kunjungan_Pembeli;
import com.android.primaitech.siprima.Pembeli.Adapter.Adapter_Pembeli;
import com.android.primaitech.siprima.Pembeli.Model.Kunjungan_Pembeli_Model;
import com.android.primaitech.siprima.Pembeli.Model.Pembeli_Model;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_Follow_Up extends Fragment {
    public static String buat, edit, hapus, detail;
    FloatingActionButton tambah;
    private Adapter_Kunjungan_Pembeli adapter;
    private List<Kunjungan_Pembeli_Model> list;
    private RecyclerView listdata;
    FrameLayout refresh;
    RecyclerView.LayoutManager mManager;
    LinearLayout not_found;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    ProgressDialog pd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_follow_up, container, false);
        listdata = (RecyclerView)v.findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        tambah = (FloatingActionButton)v.findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Tambah_Follow_Up.class);
                Temp_Follow_Up.getInstance(getContext()).setCode("2");
//                intent.putExtra("kode", kode.getText().toString());
                getContext().startActivity(intent);
            }
        });
        not_found = (LinearLayout)v.findViewById(R.id.not_found);
        list = new ArrayList<>();
        pd = new ProgressDialog(getActivity());
        adapter = new Adapter_Kunjungan_Pembeli(getActivity(),(ArrayList<Kunjungan_Pembeli_Model>) list, getContext());
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        loadJson();
        refresh = (FrameLayout) v.findViewById(R.id.refresh);
        swLayout = (SwipeRefreshLayout) v.findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });
        validate();
        return v;
    }
    public void reload(){
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }
    private void validate(){
        Bundle bundle = getArguments();
        if(bundle.getString("buat").equals("1"))
            tambah.show();
        buat = bundle.getString("buat");
        edit = bundle.getString("edit");
        hapus = bundle.getString("hapus");
        detail = bundle.getString("detail");
        kode_menu = bundle.getString("kode_menu");

    }
    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KUNJUNGAN+"datakunjungan", new Response.Listener<String>() {
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
                                md.setTanggal_pertemuan(ServerAccess.parseDate(data.getString("tp")));
                                md.setStatus(data.getString("status"));
                                md.setProspek(ServerAccess.prospek[data.getInt("prospek")]);
                                String date = data.getString("tp");
                                md.setNama_pembeli(data.getString("nama_pembeli"));
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
                params.put("kode", AuthData.getInstance(getActivity()).getAuthKey());
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
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
        startAlarm(c, kode);
    }
    private void startAlarm(Calendar c, String kode) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        intent.putExtra("kode", kode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 22);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }
}
