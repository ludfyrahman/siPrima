package com.android.primaitech.siprima.Cuti.Adapter;

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
import com.android.primaitech.siprima.Cuti.Cuti;
import com.android.primaitech.siprima.Cuti.Detail_Cuti;
import com.android.primaitech.siprima.Cuti.Model.Cuti_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Cuti extends RecyclerView.Adapter<Adapter_Cuti.ViewHolder>  {
    private ArrayList<Cuti_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Cuti(Activity activity, ArrayList<Cuti_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Cuti.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_cuti, parent, false);
        Adapter_Cuti.ViewHolder vh = new Adapter_Cuti.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Cuti.ViewHolder holder, int position) {
        Cuti_Model md = listdata.get(position);
        final Adapter_Cuti.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_detail_cuti());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.tanggal.setText(listdata.get(position).getTanggal());
        holder.keterangan.setText(listdata.get(position).getKeterangan());
        holder.status.setText(listdata.get(position).getStatus());
        Cuti cuti = new Cuti();
        edit = cuti.edit;
        hapus = cuti.hapus;
        detail = cuti.detail;
        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
        if (!hapus.equals("1"))
            holder.hapus.setVisibility(View.GONE);
        holder.kode.setVisibility(View.GONE);
        holder.detailStatus = detail;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_karyawan, nama_proyek, nama_unit,tanggal, status, keterangan, edit, hapus;
        String detailStatus;
        ProgressBar progress;
        String jumlah;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_karyawan=(TextView)v.findViewById(R.id.nama_karyawan);
            nama_proyek=(TextView)v.findViewById(R.id.nama_proyek);
            nama_unit=(TextView)v.findViewById(R.id.nama_unit);
            tanggal=(TextView)v.findViewById(R.id.tanggal);
            status=(TextView)v.findViewById(R.id.status);
            keterangan=(TextView)v.findViewById(R.id.keterangan);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Cuti ")
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Cuti cuti = new Cuti();
                                    cuti.delete(kode.getText().toString());
                                }

                            })
                            .setNegativeButton("Tidak", null)
                            .show();
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    try {
                        if(detailStatus.equals("1")){
                            Intent intent = new Intent(v.getContext(), Detail_Cuti.class);
                            intent.putExtra("kode", kode.getText().toString());
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
