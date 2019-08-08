package com.android.primaitech.siprima.Kehadiran.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Kavling.Adapter.Adapter_Kavling;
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Kavling.Model.Kavling_Model;
import com.android.primaitech.siprima.Kehadiran.Model.Kehadiran_Model;
import com.android.primaitech.siprima.Penjualan.Detail_Penjualan;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Kehadiran  extends RecyclerView.Adapter<Adapter_Kehadiran.ViewHolder>  {
    private ArrayList<Kehadiran_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Kehadiran(Activity activity, ArrayList<Kehadiran_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Kehadiran.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_kehadiran, parent, false);
        Adapter_Kehadiran.ViewHolder vh = new Adapter_Kehadiran.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Kehadiran.ViewHolder holder, int position) {
        Kehadiran_Model md = listdata.get(position);
        final Adapter_Kehadiran.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_absensi());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());

        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.create_at.setText(listdata.get(position).getCreate_at());
        holder.status.setText(listdata.get(position).getStatus());
        holder.keterangan.setText(listdata.get(position).getKeterangan());
        holder.kode.setVisibility(View.GONE);
        holder.mContext = context;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        Context mContext;
        private TextView kode, nama_karyawan,nama_proyek, nama_unit, status, keterangan, create_at;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_karyawan=(TextView)v.findViewById(R.id.nama_karyawan);
            status = (TextView)v.findViewById(R.id.status);
            nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
            nama_karyawan = (TextView)v.findViewById(R.id.nama_karyawan);
            nama_unit = (TextView)v.findViewById(R.id.nama_unit);
            keterangan = (TextView)v.findViewById(R.id.keterangan);
            create_at = (TextView)v.findViewById(R.id.create_at);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    try {
//                            Intent intent = new Intent(v.getContext(), Detail_Penjualan.class);
//                            intent.putExtra("kode", kode.getText().toString());
//                            intent.putExtra("nama_karyawan", nama_karyawan.getText().toString());
//                            v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }
}
