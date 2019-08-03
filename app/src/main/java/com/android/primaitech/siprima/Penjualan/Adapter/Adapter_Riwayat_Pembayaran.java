package com.android.primaitech.siprima.Penjualan.Adapter;

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
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Penjualan.Detail_Penjualan;
import com.android.primaitech.siprima.Penjualan.Model.Info_Pembayaran_Model;
import com.android.primaitech.siprima.Penjualan.Model.Riyawat_Pembayaran_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Riwayat_Pembayaran extends RecyclerView.Adapter<Adapter_Riwayat_Pembayaran.ViewHolder>  {
    private ArrayList<Riyawat_Pembayaran_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Riwayat_Pembayaran(Activity activity, ArrayList<Riyawat_Pembayaran_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Riwayat_Pembayaran.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_riwayat_pembayaran, parent, false);
        Adapter_Riwayat_Pembayaran.ViewHolder vh = new Adapter_Riwayat_Pembayaran.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Riwayat_Pembayaran.ViewHolder holder, int position) {
        Riyawat_Pembayaran_Model md = listdata.get(position);
        final Adapter_Riwayat_Pembayaran.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_jadwal());
        holder.jenis_pembayaran.setText(listdata.get(position).getNama_pembayaran());
        holder.metode_bayar.setText(listdata.get(position).getMetode_bayar());
        holder.jumlah_diterima.setText(listdata.get(position).getJumlah_diterima());
        holder.jumlah_bayar.setText(listdata.get(position).getJumlah_bayar());
        holder.create_at.setText(listdata.get(position).getCreate_at());
        holder.status.setText(listdata.get(position).getStatus_bayar());
//        if(!edit.equals("1"))
        holder.edit.setVisibility(View.GONE);
//        if (!hapus.equals("1"))
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
        private TextView kode, metode_bayar, jumlah_diterima, jenis_pembayaran, jumlah_bayar, create_at, status, edit, hapus;
        String detailStatus;
        ProgressBar progress;
        ImageView gambar;
        String jumlah;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            jenis_pembayaran=(TextView)v.findViewById(R.id.jenis);
            jumlah_bayar = (TextView)v.findViewById(R.id.jumlah_bayar);
            metode_bayar = (TextView)v.findViewById(R.id.metode_bayar);

            jumlah_diterima = (TextView)v.findViewById(R.id.jumlah_diterima);
            create_at = (TextView)v.findViewById(R.id.create_at);
            create_at = (TextView)v.findViewById(R.id.create_at);
            status = (TextView)v.findViewById(R.id.status);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus  "+jenis_pembayaran.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Kavling kavling = new Kavling();
                                    kavling.delete(kode.getText().toString());
//                                    proyek.reload();
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
//                        if(detailStatus.equals("1")){
                        Intent intent = new Intent(v.getContext(), Detail_Penjualan.class);
                        intent.putExtra("kode", kode.getText().toString());
                        intent.putExtra("nama_menu", jenis_pembayaran.getText().toString());
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
