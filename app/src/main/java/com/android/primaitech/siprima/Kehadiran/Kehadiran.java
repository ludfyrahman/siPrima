package com.android.primaitech.siprima.Kehadiran;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.primaitech.siprima.Akun_Bank.Akun_bank;
import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Kavling.Adapter.Adapter_Kavling;
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Kavling.Model.Kavling_Model;
import com.android.primaitech.siprima.Kehadiran.Adapter.Adapter_Kehadiran;
import com.android.primaitech.siprima.Kehadiran.Model.Kehadiran_Model;
import com.android.primaitech.siprima.MainActivity;
import com.android.primaitech.siprima.Pembeli.Pembeli;
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
import java.util.List;
import java.util.Map;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Kehadiran extends AppCompatActivity {
    private HorizontalCalendar horizontalCalendar;
    private Adapter_Kehadiran adapter;
    private List<Kehadiran_Model> list;
    private RecyclerView listdata;
    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    LinearLayout not_found;
    FloatingActionButton tambah;
    public static String kode_menu = "";
    SwipeRefreshLayout swLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        pd = new ProgressDialog(Kehadiran.this);
        toolbar.setTitle(AuthData.getInstance(getBaseContext()).getNama_menu());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kehadiran.this.onBackPressed();
            }
        });
        /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);
        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);
        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.parseColor("#73B650"))
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .addEvents(new CalendarEventsPredicate() {
                    Random rnd = new Random();
                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();
                        int count = rnd.nextInt(6);
//                        for (int i = 0; i <= count; i++){
//                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
//                        }

                        return events;
                    }
                })
                .build();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd ");
        final String strDate = mdformat.format(calendar.getTime());
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("yyyy-MM-dd", date).toString();
                if(selectedDateStr.equals(strDate)) {
                    Log.d("pesan", "I'm Here if");
                }else{
                    tambah.hide();
                    Log.d("pesan", "I'm Here else");
                }
                Toast.makeText(Kehadiran.this, selectedDateStr + " selected!", Toast.LENGTH_SHORT).show();
                reload(selectedDateStr);
            }

        });

//        FloatingActionButton fab = findViewById(R.id.tambah);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                horizontalCalendar.goToday(false);
//            }
//        });
        listdata = (RecyclerView)findViewById(R.id.listdata);
        listdata.setHasFixedSize(true);
        not_found = (LinearLayout)findViewById(R.id.not_found);
        list = new ArrayList<>();
        adapter = new Adapter_Kehadiran(Kehadiran.this,(ArrayList<Kehadiran_Model>) list, this);
        mManager = new LinearLayoutManager(Kehadiran.this,LinearLayoutManager.VERTICAL,false);
        listdata.setLayoutManager(mManager);
        listdata.setAdapter(adapter);
        tambah = (FloatingActionButton)findViewById(R.id.tambah);
        pd = new ProgressDialog(Kehadiran.this);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                reload(strDate);
            }
        });
        loadJson(strDate);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tambah_Absensi bt = new Tambah_Absensi();
                Bundle bundle = new Bundle();
                bundle.putString("tanggal", strDate);
                bt.setArguments(bundle);
                bt.show(getSupportFragmentManager(), "Tanggal");
//                }else{
//                    Toast.makeText(Kehadiran.this, "bukan tanggal absen", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }
    @Override
    public void onBackPressed() {
//        spv_dev_list_komplain.this.finish();
        startActivity(new Intent(getBaseContext(), Dashboard.class));
    }
    public void reload(String tanggal){
        not_found.setVisibility(View.GONE);
        list.clear();
        loadJson(tanggal); // your code
        listdata.getAdapter().notifyDataSetChanged();
        swLayout.setRefreshing(false);
    }
    private void loadJson(final String tanggal)
    {
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KEGIATAN+"dataabsen", new Response.Listener<String>() {
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
                                Kehadiran_Model md = new Kehadiran_Model();
                                md.setKode_absensi(data.getString("kode_absensi"));
                                md.setKeterangan(data.getString("keterangan"));
                                md.setCreate_at(ServerAccess.parseDate(data.getString("create_at")));
                                md.setNama_karyawan(data.getString("nama_karyawan"));
                                md.setNama_proyek(data.getString("nama_proyek"));
                                md.setNama_unit(data.getString("nama_unit"));
                                md.setStatus(data.getString("status"));
                                list.add(md);
                            } catch (Exception ea) {
                                ea.printStackTrace();
                                Log.d("pesan", "error "+ ea.getMessage());
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        not_found.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("pesan", "error "+e.getMessage());
                }
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
                params.put("tglmulai", tanggal);
                params.put("tglakhir", tanggal);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    public  void delete(final String kode){

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONObject data = obj.getJSONObject("respon");
                    if (data.getBoolean("status")) {
                        Toast.makeText(
                                Kehadiran.this,
                                data.getString("pesan"),
                                Toast.LENGTH_LONG
                        ).show();
                        startActivity(new Intent(Kehadiran.this, Kehadiran.class));
                    } else {
                        Toast.makeText(
                                Kehadiran.this,
                                data.getString("pesan"),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                } catch (JSONException e) {

                    Toast.makeText(
                            Kehadiran.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                    e.printStackTrace();
                }
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
                params.put("kode", kode);
                params.put("tipedata", "kavling");
                params.put("tipe_akun", "1");
                return params;
            }
        };
        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
//        startActivity(new Intent(getBaseContext(), Akun_bank.class));
    }
}
