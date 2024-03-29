package com.primagroup.primaitech.siprima.Penjualan;

import android.content.Intent;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.primagroup.primaitech.siprima.Config.AuthData;
import com.primagroup.primaitech.siprima.Config.RequestHandler;
import com.primagroup.primaitech.siprima.Config.SectionsPagerAdapter;
import com.primagroup.primaitech.siprima.Config.ServerAccess;
import com.primagroup.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Detail_Penjualan extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    LinearLayout not_found;
    public static String kode = "", nama_proyek = "", nama_menu="";
    public static ArrayList<String> datanya, datakaryawan, datapenjualan, datakavling, datalegalitas, datastruktur, datagrafikjual;
    public static boolean listkyw= false,listkavling= false,listdok= false,listjual = false,adddok= false,editdok= false,hapusdok= false,addkavling= false,editkavling= false,hapuskavling= false,
            detailkavling= false,addkaryawan= false,editkaryawan= false,hapuskaryawan= false,detailkaryawan= false,detailjual= false,addjual= false,editstruktur= false,liststruktur= false,edit= false;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        nama_menu = data.getStringExtra("nama_menu");
        kode = data.getStringExtra("kode");
        Log.d("pesan", "kode penjualan adalah "+data.getStringExtra("kode"));
        if(data.hasExtra("nama_menu")){
            toolbar.setTitle(data.getStringExtra("nama_menu"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Penjualan.class));
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
//        loadData();
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
                params.put("kode", AuthData.getInstance(Detail_Penjualan.this).getAuthKey());

                return params;
            }
        };
        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void setupViewPager(final ViewPager viewPager) {
        mSectionsPagerAdapter.addFragment(new Fragment_Info_Penjualan(), "Info Penjualan", 0);
        mSectionsPagerAdapter.addFragment(new Fragment_Info_Pembayaran(), "Info Pembayaran", 1);
        mSectionsPagerAdapter.addFragment(new Fragment_Riwayat_Pembayaran(), "Riwayat Pembayaran", 2);
        mSectionsPagerAdapter.addFragment(new Fragment_Dokumen_Penjualan(), "Cetak Dokumen", 3);

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
        startActivity(new Intent(getBaseContext(), Dashboard.class));
    }
}
