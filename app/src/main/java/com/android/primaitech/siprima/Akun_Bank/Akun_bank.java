package com.android.primaitech.siprima.Akun_Bank;

import android.content.Intent;
import android.nfc.Tag;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.primaitech.siprima.Akun_Bank.Model.Akun_Bank_Model;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.SectionsPagerAdapter;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Dashboard.Model.MenuModel;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.RAB.Fragment_Rab_Unit_Bisnis;
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
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;

public class Akun_bank extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    LinearLayout not_found;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backward);
        Intent data = getIntent();
        toolbar.setTitle(data.getStringExtra("nama_menu"));
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

    }
    public  void delete(final String kode){

        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.delete, new Response.Listener<String>() {
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
                params.put("kode", kode);
                params.put("tipedata", "akunBank");
//                params.put("tipe_akun", "1");
                return params;
            }
        };
        RequestHandler.getInstance(getBaseContext()).addToRequestQueue(senddata);
//        startActivity(new Intent(getBaseContext(), Akun_bank.class));
    }
    private void setupViewPager(final ViewPager viewPager) {
        Intent data = getIntent();

        final String kode_menu = data.getStringExtra("kode_menu");
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                int count=0;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
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
                params.put("kode_menu", kode_menu);
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
