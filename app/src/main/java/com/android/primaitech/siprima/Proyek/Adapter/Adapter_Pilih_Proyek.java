package com.android.primaitech.siprima.Proyek.Adapter;

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
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Karyawan.Temp.Temp_Karyawan;
import com.android.primaitech.siprima.Proyek.Model.Proyek_Model;
import com.android.primaitech.siprima.Proyek.Proyek;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Pilih_Proyek extends RecyclerView.Adapter<Adapter_Pilih_Proyek.ViewHolder>  {
    private ArrayList<Proyek_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Pilih_Proyek(Activity activity, ArrayList<Proyek_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Pilih_Proyek.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_proyek, parent, false);
        Adapter_Pilih_Proyek.ViewHolder vh = new Adapter_Pilih_Proyek.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pilih_Proyek.ViewHolder holder, int position) {
        Proyek_Model md = listdata.get(position);
        final Adapter_Pilih_Proyek.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_proyek());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.link_web.setText(listdata.get(position).getLink_web());
        holder.luas_proyek.setText(listdata.get(position).getLuas_proyek());
        holder.tanggal_mulai.setText(listdata.get(position).getTanggal_mulai());
        Glide.with(activity)
                .load(listdata.get(position).getBanner_proyek_kecil())
                .into(holder.gambar);
        Proyek proyek = new Proyek();
//        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
//        if (!hapus.equals("1"))
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
        private TextView kode, nama_unit, nama_proyek, link_web, luas_proyek, tanggal_mulai, edit, hapus;
        String detailStatus;
        ImageView gambar;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_proyek=(TextView)v.findViewById(R.id.nama_proyek);
            nama_unit=(TextView) v.findViewById(R.id.nama_unit);
            link_web = (TextView)v.findViewById(R.id.link_web);
            luas_proyek = (TextView)v.findViewById(R.id.luas_proyek);
            tanggal_mulai = (TextView)v.findViewById(R.id.tanggal_mulai);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    try {
                            Intent intent = new Intent(v.getContext(), menuData.halaman_navigasi(Temp_Karyawan.getInstance(v.getContext()).getNama_menu()));
                            intent.putExtra("nama_usaha", nama_proyek.getText().toString());
                            intent.putExtra("kode_usaha", kode.getText().toString());
                            v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }
}
