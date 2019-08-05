package com.android.primaitech.siprima.Pembeli.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Follow_Up.Tambah_Follow_Up;
import com.android.primaitech.siprima.Kavling.Pilih_Kavling;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Fragment_Calon_Pembeli;
import com.android.primaitech.siprima.Pembeli.Fragment_Pembeli;
import com.android.primaitech.siprima.Pembeli.Model.Pembeli_Model;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.Pembeli.Pilih_Pembeli;
import com.android.primaitech.siprima.Penjualan.Tambah_Penjualan;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Pilih_Pembeli extends RecyclerView.Adapter<Adapter_Pilih_Pembeli.ViewHolder>  {
    private ArrayList<Pembeli_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Pilih_Pembeli(Activity activity, ArrayList<Pembeli_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Pilih_Pembeli.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_pembeli, parent, false);
        Adapter_Pilih_Pembeli.ViewHolder vh = new Adapter_Pilih_Pembeli.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pilih_Pembeli.ViewHolder holder, int position) {
        Pembeli_Model md = listdata.get(position);
        String status[] = {"", "Pembeli", "Calon Pembeli"};
        final Adapter_Pilih_Pembeli.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_pembeli());
        holder.nama_pembeli.setText(listdata.get(position).getNama_pembeli());
        holder.no_ktp.setText(listdata.get(position).getNo_ktp());
        holder.no_hp.setText(listdata.get(position).getNo_hp());
        holder.status.setText(status[listdata.get(position).getStatus()]);
        holder.edit.setVisibility(View.GONE);
        holder.hapus.setVisibility(View.GONE);
        holder.kode.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_pembeli, no_ktp, status, no_hp, edit, hapus;
        String detailStatus;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_pembeli=(TextView)v.findViewById(R.id.nama_pembeli);
            no_ktp=(TextView) v.findViewById(R.id.no_ktp);
            status = (TextView)v.findViewById(R.id.status);
            no_hp = (TextView)v.findViewById(R.id.no_hp);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pilih_Pembeli pilih_pembeli = new Pilih_Pembeli();
                    try {
                            if (pilih_pembeli.code.equals("1")){
                                Intent intent = new Intent(v.getContext(), Tambah_Penjualan.class);
                                intent.putExtra("nama_pembeli", nama_pembeli.getText().toString());
                                intent.putExtra("kode_pembeli", kode.getText().toString());
                                v.getContext().startActivity(intent);
                            }else{
                                Intent intent = new Intent(v.getContext(), Tambah_Follow_Up.class);
                                intent.putExtra("nama_pembeli", nama_pembeli.getText().toString());
                                intent.putExtra("kode_pembeli", kode.getText().toString());
                                intent.putExtra("code", "2");
                                v.getContext().startActivity(intent);
                            }
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }

}