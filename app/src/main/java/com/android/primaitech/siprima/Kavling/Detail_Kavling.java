package com.android.primaitech.siprima.Kavling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.SectionsPagerAdapter;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Kategori_kavling.Kategori_kavling;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;

public class Detail_Kavling extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    LinearLayout not_found;
    TabLayout tabLayout;
    public static String kode = "";
    public static ArrayList<String> datanya, datakaryawan, datapenjualan, datakavling, datalegalitas, datastruktur, datagrafikjual;
    public static boolean listprogres= false,listdok= false,addprogres= false,adddok = false,editprogres= false,editdok= false,hapusdok= false,hapusprogres= false,edit= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kavling);
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
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });
        not_found = (LinearLayout)findViewById(R.id.not_found);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout= (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        setupViewPager(mViewPager);
        loadData();
    }
    private void loadData(){

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_KAVLING + "detailkavling", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("aksi");
                    try {
                        listprogres = data.getBoolean("listprogres");
                        listdok = data.getBoolean("listdok");
                        addprogres = data.getBoolean("addprogres");
                        adddok = data.getBoolean("adddok");
                        editdok = data.getBoolean("editdok");
                        hapusdok = data.getBoolean("hapusdok");
                        editprogres = data.getBoolean("editprogres");
                        hapusprogres = data.getBoolean("hapusprogres");
                        edit = data.getBoolean("edit");
                        Log.d("pesan", "auth key "+AuthData.getInstance(getBaseContext()).getAuthKey());
                        Log.d("pesan", "kodekavling  "+kode);
                    } catch (Exception ea) {
                        ea.printStackTrace();

                    }

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
                params.put("kodekavling", kode);
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
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());

                return params;
            }
        };
        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void setupViewPager(final ViewPager viewPager) {
        Intent data = getIntent();
        MenuData menuData = new MenuData();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                int count=0;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
                    mSectionsPagerAdapter.addFragment(new Fragment_Info_Kavling(), "Info Kavling", count);
                    count++;
                    for (int i = 0; i < arr.length(); i++) {
                        try {
                            JSONObject data = arr.getJSONObject(i);
                            if(data.getString("aksesnya").equals("1")){
                                MenuData menuData = new MenuData();
                                Fragment frag = menuData.frag(data.getString("kode_menu").toLowerCase());
                                Bundle bundle = new Bundle();
                                bundle.putString("buat", data.getString("buat"));
                                bundle.putString("edit", data.getString("edit"));
                                bundle.putString("hapus", data.getString("hapus"));
                                bundle.putString("detail", data.getString("detail"));
                                bundle.putString("kode_menu", data.getString("kode_menu").toLowerCase());
                                frag.setArguments(bundle);
                                mSectionsPagerAdapter.addFragment(frag, data.getString("nama_menu").toLowerCase(), count);
                                count++;
                            }
                        } catch (Exception ea) {
                            ea.printStackTrace();

                        }
                    }
                    if(count == 1 || count < 1){
                        tabLayout.setVisibility(View.GONE);
                        if(count < 1)
                            not_found.setVisibility(View.VISIBLE);
                    }
                    viewPager.setAdapter(mSectionsPagerAdapter);
                    mSectionsPagerAdapter.notifyDataSetChanged();
                    tabLayout.setupWithViewPager(viewPager);

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
                params.put("tipedata", "submenu");
                params.put("kode_menu", "menu8");
                params.put("kode", AuthData.getInstance(getBaseContext()).getAuthKey());
                params.put("kode_role", AuthData.getInstance(getBaseContext()).getKode_role());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
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
