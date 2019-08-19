package com.android.primaitech.siprima.Unit_Bisnis.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.primaitech.siprima.Akun_Bank.Form_Akun_Bank;
import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.Unit_Bisnis.Model.Unit_Bisnis_Model;
import com.android.primaitech.siprima.Unit_Bisnis.Temp.Temp_Unit_Bisnis;
import com.android.primaitech.siprima.Unit_Bisnis.Unit_Bisnis;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Pilih_Unit_Bisnis extends RecyclerView.Adapter<Adapter_Pilih_Unit_Bisnis.ViewHolder>  {
    private ArrayList<Unit_Bisnis_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Pilih_Unit_Bisnis(Activity activity, ArrayList<Unit_Bisnis_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Pilih_Unit_Bisnis.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_unit_bisnis, parent, false);
        Adapter_Pilih_Unit_Bisnis.ViewHolder vh = new Adapter_Pilih_Unit_Bisnis.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pilih_Unit_Bisnis.ViewHolder holder, int position) {
        Unit_Bisnis_Model md = listdata.get(position);
        final Adapter_Pilih_Unit_Bisnis.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_unit());
        holder.tipe.setText(listdata.get(position).getTipe());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.url_web.setText(listdata.get(position).getUrl_web());
        holder.tanggal.setText(listdata.get(position).getTgl_mulai());
        holder.status.setText(listdata.get(position).getStatus());
        Unit_Bisnis unit_bisnis = new Unit_Bisnis();
        edit = unit_bisnis.edit;
        hapus = unit_bisnis.hapus;
        detail = unit_bisnis.detail;
        Glide.with(activity)
                .load(listdata.get(position).getLogo())
                .into(holder.gambar);
        holder.edit.setVisibility(View.GONE);
        holder.hapus.setVisibility(View.GONE);
        holder.kode.setVisibility(View.GONE);
        holder.mContext = context;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_unit, tipe, url_web, tanggal, status, edit, hapus;
        ImageView gambar;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            tipe=(TextView)v.findViewById(R.id.tipe);
            nama_unit=(TextView) v.findViewById(R.id.nama_unit);
            tanggal=(TextView) v.findViewById(R.id.tanggal);
            status=(TextView) v.findViewById(R.id.status);
            url_web=(TextView) v.findViewById(R.id.url_web);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    try {
//                            Intent intent = new Intent(v.getContext(), Form_Akun_Bank.class);
//                            intent.putExtra("nama_usaha", nama_unit.getText().toString());
//                            intent.putExtra("kode_usaha", kode.getText().toString());
//                            v.getContext().startActivity(intent);

                            Intent intent = new Intent(v.getContext(), menuData.halaman_navigasi(Temp_Unit_Bisnis.getInstance(v.getContext()).getNama_menu()));
                            intent.putExtra("nama_usaha", nama_unit.getText().toString());
                            intent.putExtra("kode_usaha", kode.getText().toString());
                            v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error "+Temp_Unit_Bisnis.getInstance(v.getContext()).getNama_menu());
                    }
                }
            });
        }
    }
}
