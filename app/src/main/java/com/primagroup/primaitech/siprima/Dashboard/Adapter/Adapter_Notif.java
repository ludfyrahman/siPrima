package com.primagroup.primaitech.siprima.Dashboard.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Config.MenuData;
import com.primagroup.primaitech.siprima.Dashboard.Model.Notif_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Notif extends RecyclerView.Adapter<Adapter_Notif.ViewHolder>  {
    private ArrayList<Notif_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Notif(Activity activity, ArrayList<Notif_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Notif.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_notif, parent, false);
        Adapter_Notif.ViewHolder vh = new Adapter_Notif.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Notif.ViewHolder holder, int position) {
        Notif_Model md = listdata.get(position);
        final Adapter_Notif.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_bantu());
        holder.kode_menu.setText(listdata.get(position).getKode_menu());
        holder.deskripsi.setText(listdata.get(position).getDeskripsi());
        holder.tanggal.setText(listdata.get(position).getCreate_at());
        holder.kode.setVisibility(View.GONE);
        holder.mContext = context;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, kode_menu, deskripsi, tanggal;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            kode_menu=(TextView)v.findViewById(R.id.kode_menu);
            deskripsi=(TextView)v.findViewById(R.id.deskripsi);
            tanggal=(TextView)v.findViewById(R.id.tanggal);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    Log.d("pesan", "kodemenunya adalah "+kode_menu.getText().toString());
                    Log.d("pesan", "kode bantu adalah "+kode.getText().toString());
                    try {
                        Intent intent = new Intent(v.getContext(), menuData.detail_halaman(kode_menu.getText().toString()));
                        intent.putExtra("kode", kode.getText().toString());
                        v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }
}
