package com.android.primaitech.siprima.Unit_Bisnis;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AppController;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_Info_Unit_Bisnis extends Fragment {
    ProgressDialog pd;
    LinearLayout bg;
    TextView nama_unit, deskripsi, no_hp, email, alamat, kode_pos, url_web, tanggal_mulai, status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_info_unit_bisnis, container, false);
        pd = new ProgressDialog(getActivity());
        nama_unit = (TextView)v.findViewById(R.id.nama_unit);
        deskripsi = (TextView)v.findViewById(R.id.deskripsi);
        no_hp = (TextView)v.findViewById(R.id.no_hp);
        email = (TextView)v.findViewById(R.id.email);
        alamat = (TextView)v.findViewById(R.id.alamat);
        kode_pos = (TextView)v.findViewById(R.id.kode_pos);
        url_web = (TextView)v.findViewById(R.id.url_web);
        tanggal_mulai = (TextView)v.findViewById(R.id.tanggal_mulai);
        status = (TextView)v.findViewById(R.id.status);
        bg = (LinearLayout)v.findViewById(R.id.bg);
        loadJson();
        return v;
    }

    private void loadJson()
    {
        pd.setMessage("Menampilkan Data");
        pd.setCancelable(false);
        pd.show();
        Detail_Unit_Bisnis detail_unit_bisnis = new Detail_Unit_Bisnis();
        final String kode =detail_unit_bisnis.kode;
        StringRequest senddata = new StringRequest(Request.Method.POST, ServerAccess.URL_UNIT_BISNIS+"detailunitbisnis", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject res = null;
                try {
                    pd.cancel();
                    res = new JSONObject(response);
                    JSONObject data = res.getJSONObject("data");
                    nama_unit.setText(data.getString("nama_unit"));
                    deskripsi.setText(data.getString("deskripsi"));
                    alamat.setText(data.getString("alamat"));
                    no_hp.setText(data.getString("no_hp"));
                    email.setText(data.getString("email"));
                    no_hp.setText(data.getString("kode_pos"));
                    url_web.setText(data.getString("url_web"));
                    tanggal_mulai.setText(ServerAccess.parseDate(data.getString("tgl_mulai")));
                    Glide.with(getActivity())
                            .load(ServerAccess.BASE_URL+"/"+data.getString("banner"))
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        bg.setBackground(resource);
                                    }
                                }
                            });
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
                params.put("kode", AuthData.getInstance(getContext()).getAuthKey());
                params.put("kode_unit", kode);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(senddata);
    }
}