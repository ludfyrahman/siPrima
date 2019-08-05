package com.android.primaitech.siprima.Dashboard;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.RequestHandler;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Adapter.AdapterMenu;
import com.android.primaitech.siprima.Dashboard.Model.MenuModel;
import com.android.primaitech.siprima.Database.Database_Helper;
import com.android.primaitech.siprima.Database.Model.Role_User;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;

public class Fragment_Dashboard extends Fragment {
    private AdapterMenu adapter;
    private ArrayList<MenuModel> listdata;
    private GridLayoutManager layoutManager;
    private RecyclerView dashboard_menu;
    ImageView reload;
    FrameLayout refresh;
    SwipeRefreshLayout swLayout;
    LinearLayout banner;
    private Database_Helper db;
    TextView nama_pengguna, nama_usaha, level_akun;
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
        loadJson();
        db = new Database_Helper(getContext());
//        Toast.makeText(getActivity(), getActivity().getPackageName(), Toast.LENGTH_SHORT).show();
        nama_usaha = (TextView)v.findViewById(R.id.nama_usaha);
        level_akun = (TextView)v.findViewById(R.id.level_akun);
        banner = (LinearLayout)v.findViewById(R.id.banner);
        nama_pengguna = (TextView)v.findViewById(R.id.nama_pengguna);
        adapter = new AdapterMenu(getActivity(),listdata);
        dashboard_menu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refresh = (FrameLayout) v.findViewById(R.id.refresh);
        swLayout = (SwipeRefreshLayout) v.findViewById(R.id.swlayout);
        swLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
        reload = (ImageView)v.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listdata.clear();
                loadJson(); // your code
            }
        });
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listdata.clear();
                loadJson(); // your code
                swLayout.setRefreshing(false);
            }
        });

        checkRevisiCode();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("pesan ", "Onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("pesan", "on Pause");
    }

    private void checkRevisiCode() {
        // you can check notesList.size() > 0

        if (db.getRoleUserCount() > 0) {
//            noNotesView.setVisibility(View.GONE);
            dataDashboard(2);
            Log.d("pesan", "Sqlite Ada Data");

        } else {
            dataDashboard(1);
            Log.d("pesan", "Sqlite Tidak Ada Data");
//            noNotesView.setVisibility(View.VISIBLE);
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
                            if(status == 1){
                                db.insertRole_User(dataRole.getString("kode_role"), dataRole.getString("nama_role"), dataRole.getString("revisi_code"));
                            }else if(status == 2){
                                db.getRole_UserDetail(dataRole.getString("kode_role"));
                                Role_User role_user = new Role_User();
                                Log.d("pesan", "data dari online "+dataRole.getString("revisi_code"));
                                if(dataRole.getString("revisi_code").equals(db.getRole_UserDetail(dataRole.getString("kode_role")).getRevisi_code())){
                                    Log.d("pesan", "revisi code sama");
                                }else{
                                    db.truncateRole_User();
                                    db.insertRole_User(dataRole.getString("kode_role"), dataRole.getString("nama_role"), dataRole.getString("revisi_code"));
                                    Log.d("pesan", "revisi code tidak sama");
                                }
                            }
                            String dir = serverAccess.bannerProyek;
                            Glide.with(getActivity())
                                    .load(ServerAccess.BASE_URL+"/"+data.getString("banner"))
                                    .into(new SimpleTarget<GlideDrawable>() {
                                        @Override
                                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
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
    public static Bitmap getBitmapFromURL(String src) {
        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            InputStream srt = new java.net.URL(src).openStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(srt);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            Log.d("error", "pesan error "+e.getMessage());
            return null;
        }
    }
    private void loadJson()
    {

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
                            md.setJudul(data.getString("nama_menu"));
                            md.setLink(data.getString("link"));
                            int id = getResources().getIdentifier(data.getString("kode_menu").toLowerCase(), "drawable", getActivity().getPackageName());
//                            int id = R.drawable.class.getField(data.getString("kode_menu")).getInt(null);
                            md.setGambar(id);
                            md.setKode_menu(data.getString("kode_menu").toLowerCase());
//                            Log.e("responnya ",""+"@drawable/"+data.getString("kode_menu").toLowerCase());
                            listdata.add(md);
                        } catch (Exception ea) {
                            ea.printStackTrace();

                        }
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
                params.put("kode", "2");
                params.put("kode_role", AuthData.getInstance(getContext()).getKode_role());
                params.put("show", "user");
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(senddata);
    }
}