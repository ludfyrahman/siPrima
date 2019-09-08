package com.android.primaitech.siprima.Proyek;

import android.content.Intent;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.SectionsPagerAdapter;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Hpp.Temp.Temp_Hpp;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Detail_Proyek extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    LinearLayout not_found;
    public static String kode = "", nama_proyek = "";
    public static ArrayList<String> datanya, datakaryawan, datapenjualan, datakavling, datalegalitas, datastruktur, datagrafikjual;
    public static boolean listkyw= false,listkavling= false,listdok= false,listjual = false,adddok= false,editdok= false,hapusdok= false,addkavling= false,editkavling= false,hapuskavling= false,
            detailkavling= false,addkaryawan= false,editkaryawan= false,hapuskaryawan= false,detailkaryawan= false,detailjual= false,addjual= false,editstruktur= false,liststruktur= false,edit= false;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_proyek);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        kode = data.getStringExtra("kode");
        if(data.hasExtra("nama_menu")){
            toolbar.setTitle(data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Proyek.class));
            }
        });
//        datanya = new ArrayList<String>();
        not_found = (LinearLayout)findViewById(R.id.not_found);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout= (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setupViewPager(mViewPager);
        loadData();
    }
    private void loadData(){
        Intent data = getIntent();
        datapenjualan = new ArrayList<>();
        final String kode = data.getStringExtra("kode");
        Temp_Hpp.getInstance(getBaseContext()).setKode_Produk(kode);
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PROYEK + "detailproyek", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("aksi");
//                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                listkyw = data.getBoolean("listkyw");
                                listkavling = data.getBoolean("listkavling");
                                listdok = data.getBoolean("listdok");
                                listjual = data.getBoolean("listjual");
                                adddok = data.getBoolean("adddok");
                                editdok = data.getBoolean("editdok");
                                hapusdok = data.getBoolean("hapusdok");
                                addkavling = data.getBoolean("addkavling");
                                editkavling = data.getBoolean("editkavling");
                                hapuskavling = data.getBoolean("hapuskavling");
                                detailkavling = data.getBoolean("detailkavling");
                                addkaryawan = data.getBoolean("addkaryawan");
                                editkaryawan = data.getBoolean("editkaryawan");
                                hapuskaryawan = data.getBoolean("hapuskaryawan");
                                detailkaryawan = data.getBoolean("detailkaryawan");
                                detailjual = data.getBoolean("detailjual");
                                addjual = data.getBoolean("addjual");
                                editstruktur = data.getBoolean("editstruktur");
                                liststruktur = data.getBoolean("liststruktur");
                                edit = data.getBoolean("edit");
                            } catch (Exception ea) {
                                ea.printStackTrace();

                            }
//                        }

                } catch (JSONException e) {
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
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("kode_proyek", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    public  void delete(final String kode){

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_PEMBELI+"deletepembeli", new Response.Listener<String>() {
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
                params.put("kodepembeli", kode);
                params.put("kode", AuthData.getInstance(Detail_Proyek.this).getAuthKey());

                return params;
            }
        };
        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void setupViewPager(final ViewPager viewPager) {
        mSectionsPagerAdapter.addFragment(new Fragment_Info_Proyek(), "Info Proyek", 0);
        mSectionsPagerAdapter.addFragment(new Fragment_Data_Penjualan(), "Data Penjualan", 1);
        mSectionsPagerAdapter.addFragment(new Fragment_Grafik_Penjualan(), "Grafik Penjualan", 2);
        mSectionsPagerAdapter.addFragment(new Fragment_Data_Kavling(), "Data Kavling", 3);
        mSectionsPagerAdapter.addFragment(new Fragment_Data_Karyawan_Proyek(), "Data Karyawan", 4);
        mSectionsPagerAdapter.addFragment(new Fragment_Data_Legalitas(), "Data Legalitas", 5);
        mSectionsPagerAdapter.addFragment(new Fragment_Hpp_Proyek(), "Hpp Proyek", 6);

        viewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
//        spv_dev_list_komplain.this.finish();
        startActivity(new Intent(getBaseContext(), Proyek.class));
    }
}
