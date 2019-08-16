package com.android.primaitech.siprima.Lahan;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.SectionsPagerAdapter;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.Proyek.Fragment_Info_Proyek;
import com.android.primaitech.siprima.Proyek.Proyek;
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
import java.util.HashMap;
import java.util.Map;

public class Detail_Lahan extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    LinearLayout not_found;
    TabLayout tabLayout;
    public static String kode = "", nama_proyek = "";
    public static boolean edit= false, konfirmasi=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lahan);
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
                startActivity(new Intent(getApplicationContext(), Lahan.class));
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
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setupViewPager(mViewPager);
        loadAksi();
    }
    private void setupViewPager(final ViewPager viewPager) {
        Intent data = getIntent();
        MenuData menuData = new MenuData();
        String kd_menu = "";
        if(data.hasExtra("kode_menu")){
            kd_menu = AuthData.getInstance(getBaseContext()).getKode_menu();
        }else{
            kd_menu = AuthData.getInstance(getBaseContext()).getKode_menu();
        }
        final String kode_menu = kd_menu;

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                int count=1;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
                    mSectionsPagerAdapter.addFragment(new Fragment_Info_Lahan(), "Info Proyek", 0);
                    for (int i = 0; i < arr.length(); i++) {
                        try {
                            JSONObject data = arr.getJSONObject(i);
                            if(data.getString("aksesnya").equals("1")){
                                if (data.getString("show_menu").equals("1")){
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
                params.put("kode_menu", kode_menu);
                params.put("kode", AuthData.getInstance(Detail_Lahan.this).getAuthKey());
                params.put("kode_role", AuthData.getInstance(getBaseContext()).getKode_role());
                return params;
            }
        };

        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
    }
    private void loadAksi(){
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_LAHAN+"detaillahanlengkap", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("aksi");
                    konfirmasi = data.getBoolean("konfirmasi");
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
                params.put("kode_lahan", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
//        spv_dev_list_komplain.this.finish();
        startActivity(new Intent(getBaseContext(), Lahan.class));
    }

}
