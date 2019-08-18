package com.android.primaitech.siprima.RAB.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.primaitech.siprima.Akun_Bank.Form_Akun_Bank;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.RAB.Model.RAB_Model;

import java.util.ArrayList;

public class Adapter_Pilih_RAB extends RecyclerView.Adapter<Adapter_Pilih_RAB.ViewHolder>  {
    private ArrayList<RAB_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Pilih_RAB(Activity activity, ArrayList<RAB_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Pilih_RAB.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_rab, parent, false);
        Adapter_Pilih_RAB.ViewHolder vh = new Adapter_Pilih_RAB.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pilih_RAB.ViewHolder holder, int position) {
        RAB_Model md = listdata.get(position);
        String status[] = {"", "Pembeli", "Calon Pembeli"};
        final Adapter_Pilih_RAB.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_rab());
        holder.nama_usaha.setText(listdata.get(position).getNama_usaha());
        holder.nama_rab.setText(listdata.get(position).getNama_rab());
        holder.total_anggaran.setText(listdata.get(position).getTotal_anggaran());
        holder.limit_bulanan.setText(listdata.get(position).getLimit_bulanan());
        holder.status.setText(listdata.get(position).getStatus());
        holder.tanggal.setText(listdata.get(position).getCreate_at());
        holder.edit.setVisibility(View.GONE);
        holder.hapus.setVisibility(View.GONE);
        holder.mContext = context;
        holder.kode.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView kode, nama_rab, nama_usaha, total_anggaran, limit_bulanan, sisa_anggaran, status, tanggal, edit, hapus;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_rab=(TextView)v.findViewById(R.id.nama_rab);
            nama_usaha=(TextView) v.findViewById(R.id.nama_usaha);
            total_anggaran = (TextView)v.findViewById(R.id.total_anggaran);
            limit_bulanan = (TextView)v.findViewById(R.id.limit_bulanan);
            status = (TextView)v.findViewById(R.id.status);
            tanggal = (TextView)v.findViewById(R.id.tanggal);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                            Intent intent = new Intent(v.getContext(), Form_Akun_Bank.class);
                            intent.putExtra("kode_rab", kode.getText().toString());
                            intent.putExtra("nama_rab", nama_rab.getText().toString());
                            v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }

}