package com.primagroup.primaitech.siprima.Karyawan.Adapter;

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
import com.primagroup.primaitech.siprima.Karyawan.Model.Karyawan_Model;
import com.primagroup.primaitech.siprima.Karyawan.Temp.Temp_Karyawan;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Pilih_Karyawan extends RecyclerView.Adapter<Adapter_Pilih_Karyawan.ViewHolder>  {
    private ArrayList<Karyawan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Pilih_Karyawan(Activity activity, ArrayList<Karyawan_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Pilih_Karyawan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_karyawan, parent, false);
        Adapter_Pilih_Karyawan.ViewHolder vh = new Adapter_Pilih_Karyawan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pilih_Karyawan.ViewHolder holder, int position) {
        Karyawan_Model md = listdata.get(position);
        final Adapter_Pilih_Karyawan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_karyawan());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());
        holder.nama_divisi.setText(listdata.get(position).getNama_divisi());
        holder.tanggal_gabung.setText(listdata.get(position).getTanggal_gabung());
//        if(listdata.get(position).getTipe_karyawan().equals("1")) {
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
        private TextView kode, nama_unit, nama_proyek, nama_karyawan, nama_divisi, tanggal_gabung, edit, hapus;
        String detailStatus;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_proyek=(TextView)v.findViewById(R.id.nama_proyek);
            nama_unit=(TextView) v.findViewById(R.id.nama_unit);
            nama_karyawan = (TextView)v.findViewById(R.id.nama_karyawan);
            nama_divisi = (TextView)v.findViewById(R.id.nama_divisi);
            tanggal_gabung = (TextView)v.findViewById(R.id.tanggal_gabung);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    try {
                        Intent intent = new Intent(v.getContext(), menuData.halaman_navigasi(Temp_Karyawan.getInstance(v.getContext()).getNama_menu()));
                        intent.putExtra("nama_karyawan", nama_karyawan.getText().toString());
                        intent.putExtra("kode_karyawan", kode.getText().toString());
                        v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error di karyawan "+Temp_Karyawan.getInstance(v.getContext()).getNama_menu());
                    }
                }
            });
        }
    }

}