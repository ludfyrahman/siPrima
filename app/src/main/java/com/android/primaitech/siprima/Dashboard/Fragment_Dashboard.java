package com.android.primaitech.siprima.Dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.primaitech.siprima.Akun.Login;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Adapter.AdapterMenu;
import com.android.primaitech.siprima.Dashboard.Model.MenuModel;
import com.android.primaitech.siprima.Database.Database_Helper;
import com.android.primaitech.siprima.Database.Model.Master_SQlite;
import com.android.primaitech.siprima.Database.Model.Menu_Table;
import com.android.primaitech.siprima.Database.Model.Role_User;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;

public class Fragment_Dashboard extends Fragment {
    private AdapterMenu adapter;
    private ArrayList<MenuModel> listdata;
    private GridLayoutManager layoutManager;
    private RecyclerView dashboard_menu;
    ImageView reload, img_loading;
    TextView tanggal, tahun;
    FrameLayout refresh;
    SwipeRefreshLayout swLayout;
    LinearLayout banner, loading;
    //Sqlite
    Database_Helper db;
    protected Cursor cursor;
    String[] daftar;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat tgl = new SimpleDateFormat("dd MMMM");
    SimpleDateFormat th = new SimpleDateFormat("yyyy");
    String tglnow = tgl.format(c);
    String tahunnow = th.format(c);
    TextView nama_pengguna, nama_usaha, level_akun;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String now = df.format(c);
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_fragment_dashboard, container, false);
        dashboard_menu = (RecyclerView)v.findViewById(R.id.dashboard_menu);
        dashboard_menu.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        dashboard_menu.setLayoutManager(layoutManager);
        listdata = new ArrayList<>();
        db = new Database_Helper(getContext());
//        Toast.makeText(getActivity(), getActivity().getPackageName(), Toast.LENGTH_SHORT).show();
        nama_usaha = (TextView)v.findViewById(R.id.nama_usaha);
        level_akun = (TextView)v.findViewById(R.id.level_akun);
        tanggal = (TextView)v.findViewById(R.id.tanggal);
        tahun = (TextView)v.findViewById(R.id.tahun);
        banner = (LinearLayout)v.findViewById(R.id.banner);
        loading = (LinearLayout)v.findViewById(R.id.loading);
        img_loading = (ImageView)v.findViewById(R.id.img_loading);
        Glide.with(getActivity())
                .load(R.drawable.loading)
                .into(img_loading);
        nama_pengguna = (TextView)v.findViewById(R.id.nama_pengguna);
        adapter = new AdapterMenu(getActivity(),listdata);
        dashboard_menu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refresh = (FrameLayout) v.findViewById(R.id.refresh);
        swLayout = (SwipeRefreshLayout) v.findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        reload = (ImageView)v.findViewById(R.id.reload);
        tanggal.setText(tglnow);
        tahun.setText(tahunnow);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listdata.clear();
//                loadJson(); // your code
                loadDataFromSQlite();
            }
        });
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listdata.clear();
//                loadJson(); // your code
                loadDataFromSQlite();
                swLayout.setRefreshing(false);
            }
        });

        checkConnection();
        return v;
    }
    private void loadDataFromSQlite(){
        SQLiteDatabase d = db.getReadableDatabase();
        cursor = d.rawQuery("SELECT kode_menu, nama_menu, link, gambar FROM menu",null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        loading.setVisibility(View.VISIBLE);
        if (cursor.getCount() > 0){

            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                daftar[cc] = cursor.getString(1).toString();
                MenuModel md = new MenuModel();
                md.setKode_menu(cursor.getString(0).toLowerCase());
                md.setJudul(cursor.getString(1));
                md.setLink(cursor.getString(2));
                int id = getResources().getIdentifier(cursor.getString(0).toLowerCase(), "drawable", getActivity().getPackageName());
                md.setGambar(id);
                listdata.add(md);
            }
            adapter.notifyDataSetChanged();
            loading.setVisibility(View.GONE);
        }else{
            Toast.makeText(getContext(), "Data anda kosong", Toast.LENGTH_SHORT).show();
            Log.d("pesan", "Data Anda Kosong");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("pesan", "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("pesan", "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("pesan", "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("pesan", "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("pesan", "onDestroy() called");
    }

    private void checkData() {
        // you can check notesList.size() > 0
        SQLiteDatabase d = db.getReadableDatabase();
        cursor = d.rawQuery("SELECT * FROM menu",null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        Log.d("pesan", "jumlah menu data "+cursor.getCount());
        if (cursor.getCount() > 0){
            Log.d("pesan", "Sqlite Ada Data");
            //jika online mengambil data dari api terlebih dahulu
//            loadDataFromSQlite();
            validasi();
        } else {
            Log.d("pesan", "Sqlite Tidak Ada Data");
            dataDashboard(1);
            db.truncateMenu();
            loadJson();
            loadDataFromSQlite();
        }
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    private void validasi(){
        SQLiteDatabase d = db.getReadableDatabase();
        cursor = d.rawQuery("SELECT `key` from master",null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        Log.d("pesan", "berada di fungsi validasi");
        Log.d("pesan", "jumlah data master "+db.getMasterCount());
        if (db.getMasterCount() > 0) {
            cursor.moveToPosition(0);
            final String kode_revisi = cursor.getString(0).toString();
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.Menu + "/cekdata", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        if (res.getString("hasil").equals("null")){
                            loadDataFromSQlite();
                        }else{
                            JSONObject data = res.getJSONObject("hasil");

                            if (data.getBoolean("status") == false){
                                loadJson();
                                Log.d("pesan", "ada di percabangan line 253");
                            }else {
                                loadDataFromSQlite();
                            }
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
                    params.put("kuncinya", ServerAccess.menu_key);
                    params.put("usernamenya", now);
                    params.put("tipe", "1");
                    params.put("tabel", "menu");
                    return params;
                }
            };

            RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
        }else{
            db.truncateKey();
            StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.Menu + "/cekdata", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject res = null;
                    try {
                        res = new JSONObject(response);
                        JSONObject data = res.getJSONObject("hasil");
                        db.insertKey(data.getString("last_kode_server"));
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
                    params.put("kuncinya", ServerAccess.menu_key);
                    params.put("usernamenya", now);
                    params.put("tipe", "1");
                    params.put("tabel", "menu");
                    return params;
                }
            };

            RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
            loadDataFromSQlite();
        }

    }
    public void checkConnection(){
        if(isOnline()){
            checkData();
            Log.d("pesan", "anda sedang online");
            Toast.makeText(getContext(), "You are connected to Internet", Toast.LENGTH_SHORT).show();
        }else{
            loadDataFromSQlite();
            Log.d("pesan", "anda sedang offline");
            Toast.makeText(getContext(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }
    private void dataDashboard(final int status){
        nama_pengguna.setText(AuthData.getInstance(getContext()).getUsername());
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
                    JSONArray arrRole = res.getJSONArray("role");
                        try {
                            JSONObject data = arr.getJSONObject(0);
                            JSONObject dataRole = arrRole.getJSONObject(0);
                            nama_usaha.setText((data.getString("nama_usaha").equals("null") ? "PrimaGroup" : data.getString("nama_usaha")));
                            level_akun.setText(data.getString("nama_role"));
                            ServerAccess serverAccess = new ServerAccess();
//                            if(status == 1){
//                                db.insertRole_User(dataRole.getString("kode_role"), dataRole.getString("nama_role"), dataRole.getString("revisi_code"));
//                            }else if(status == 2){
//                                db.getRole_UserDetail(dataRole.getString("kode_role"));


//                            }
                            String dir = serverAccess.bannerProyek;
                            Glide.with(getActivity())
                                    .load(ServerAccess.BASE_URL+"/"+data.getString("banner"))
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                banner.setBackground(resource);
                                            }
                                        }
                                    });
                        } catch (Exception ea) {
                            ea.printStackTrace();

                        }

                    adapter.notifyDataSetChanged();
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
                params.put("kode", AuthData.getInstance(getContext()).getAuthKey());
                params.put("kode_role", AuthData.getInstance(getContext()).getKode_role());
                params.put("kode_user", AuthData.getInstance(getContext()).getKodeUser());
                params.put("tipedata", "dataAkun");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.logo_app)
                .setTitle("Keluar Aplikasi")
                .setMessage("Apakah Anda Yakin Ingin Keluar Dari Aplikasi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AuthData.getInstance(getContext()).logout();
                        startActivity(new Intent(getContext(), Login.class));
                        ActivityCompat.finishAffinity(getActivity());

                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }
    private void loadJson()
    {
        db.truncateMenu();
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.Menu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    res = new JSONObject(response);
                    JSONArray arr = res.getJSONArray("data");
                    for(int i = 0; i < arr.length(); i++) {
                        try {
                            JSONObject data = arr.getJSONObject(i);
                            MenuModel md = new MenuModel();
                            int id = getResources().getIdentifier(data.getString("kode_menu").toLowerCase(), "drawable", getActivity().getPackageName());
                            db.insertMenu(data.getString("kode_menu").toLowerCase(), data.getString("nama_menu"), data.getString("link"), id);
                            Log.d("pesan", "nama menu "+data.getString("nama_menu"));
                        } catch (Exception ea) {
                            ea.printStackTrace();

                        }
                    }
                    loadDataFromSQlite();
                    adapter.notifyDataSetChanged();
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
                params.put("kode", AuthData.getInstance(getContext()).getAuthKey());
                params.put("kode_role", AuthData.getInstance(getContext()).getKode_role());
                params.put("show", "user");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
    }

}