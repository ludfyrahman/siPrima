package com.primagroup.primaitech.siprima.Pembeli.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Follow_Up.Detail_Follow_Up;
import com.primagroup.primaitech.siprima.Pembeli.Kunjungan_Pembeli;
import com.primagroup.primaitech.siprima.Pembeli.Model.Kunjungan_Pembeli_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Kunjungan_Pembeli extends RecyclerView.Adapter<Adapter_Kunjungan_Pembeli.ViewHolder>  {
    private ArrayList<Kunjungan_Pembeli_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Kunjungan_Pembeli(Activity activity, ArrayList<Kunjungan_Pembeli_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Kunjungan_Pembeli.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_kunjungan_pembeli, parent, false);
        Adapter_Kunjungan_Pembeli.ViewHolder vh = new Adapter_Kunjungan_Pembeli.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Kunjungan_Pembeli.ViewHolder holder, int position) {
        Kunjungan_Pembeli_Model md = listdata.get(position);
        String status[] = {"", "Pembeli", "Calon Pembeli"};
        final Adapter_Kunjungan_Pembeli.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_kunjungan());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());
        holder.tanggal_pertemuan.setText(listdata.get(position).getTanggal_pertemuan());
        holder.alamat_temu.setText(listdata.get(position).getAlamat_temu());
        holder.prospek.setText(listdata.get(position).getProspek());
        holder.status.setText(listdata.get(position).getStatus());
        holder.nama_pembeli.setText(listdata.get(position).getNama_pembeli());
        Kunjungan_Pembeli kunjungan_pembeli = new Kunjungan_Pembeli();
        edit = kunjungan_pembeli.edit;
        hapus = kunjungan_pembeli.hapus;
        detail = kunjungan_pembeli.detail;
//        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
//        if (!hapus.equals("1"))
//            holder.hapus.setVisibility(View.GONE);
        holder.mContext = context;
        holder.kode.setVisibility(View.GONE);
        holder.detailStatus = detail;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_karyawan, alamat_temu, tanggal_pertemuan, prospek, status, nama_pembeli, edit, hapus;
        String detailStatus;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_karyawan=(TextView)v.findViewById(R.id.nama_karyawan);
            alamat_temu=(TextView) v.findViewById(R.id.alamat_temu);
            tanggal_pertemuan=(TextView) v.findViewById(R.id.tanggal_pertemuan);
            prospek=(TextView) v.findViewById(R.id.prospek);
            status = (TextView)v.findViewById(R.id.status);
            nama_pembeli = (TextView)v.findViewById(R.id.nama_pembeli);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus ")
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mContext instanceof Kunjungan_Pembeli) {
                                        ((Kunjungan_Pembeli)mContext).delete(kode.getText().toString());
                                    }
                                }

                            })
                            .setNegativeButton("Tidak", null)
                            .show();
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        if(detailStatus.equals("1")){
                            Intent intent = new Intent(v.getContext(), Detail_Follow_Up.class);
                            intent.putExtra("nama_menu", tanggal_pertemuan.getText().toString());
                            intent.putExtra("kode", kode.getText().toString());
                            v.getContext().startActivity(intent);
//                        }
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }

}