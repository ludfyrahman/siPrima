package com.android.primaitech.siprima.Proyek.Adapter;

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

import com.android.primaitech.siprima.Akun_Bank.Detail_Akun_Bank;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Karyawan.Adapter.Adapter_Karyawan;
import com.android.primaitech.siprima.Karyawan.Detail_Karyawan;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Proyek;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Unit_Bisnis;
import com.android.primaitech.siprima.Karyawan.Karyawan;
import com.android.primaitech.siprima.Karyawan.Model.Karyawan_Model;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Karyawan_Proyek extends RecyclerView.Adapter<Adapter_Karyawan_Proyek.ViewHolder>  {
    private ArrayList<Karyawan_Model> listdata;
    private Activity activity;
    private Context context;
    Detail_Proyek detail_proyek = new Detail_Proyek();
    String edit,hapus, detail;
    public Adapter_Karyawan_Proyek(Activity activity, ArrayList<Karyawan_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Karyawan_Proyek.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_karyawan, parent, false);
        Adapter_Karyawan_Proyek.ViewHolder vh = new Adapter_Karyawan_Proyek.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Karyawan_Proyek.ViewHolder holder, int position) {
        Karyawan_Model md = listdata.get(position);
        final Adapter_Karyawan_Proyek.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_karyawan());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());
        holder.nama_divisi.setText(listdata.get(position).getNama_divisi());
        holder.tanggal_gabung.setText(listdata.get(position).getTanggal_gabung());
        holder.kode.setVisibility(View.GONE);
        if(!detail_proyek.editkaryawan)
            holder.edit.setVisibility(View.GONE);
        if (!detail_proyek.hapuskaryawan)
            holder.hapus.setVisibility(View.GONE);
        holder.detailStatus = detail_proyek.detailkaryawan;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_unit, nama_proyek, nama_karyawan, nama_divisi, tanggal_gabung, edit, hapus;
        boolean detailStatus;
        ProgressBar progress;
        String jumlah;
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
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Akun Bank "+nama_unit.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.finishAffinity(dashboard_manager_dev.this);
                                    Karyawan karyawan = new Karyawan();
                                    karyawan.delete(kode.getText().toString());
//                                    finish();
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
                        if(detailStatus){
                            Intent intent = new Intent(v.getContext(), Detail_Karyawan.class);
                            intent.putExtra("nama_menu", nama_karyawan.getText().toString());
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