package com.android.primaitech.siprima.Penjualan.Adapter;

import android.app.Activity;
import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.primaitech.siprima.Penjualan.Model.Info_Pembayaran_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Info_Pembayaran extends RecyclerView.Adapter<Adapter_Info_Pembayaran.ViewHolder>  {
    private ArrayList<Info_Pembayaran_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Info_Pembayaran(Activity activity, ArrayList<Info_Pembayaran_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Info_Pembayaran.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_info_pembayaran, parent, false);
        Adapter_Info_Pembayaran.ViewHolder vh = new Adapter_Info_Pembayaran.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Info_Pembayaran.ViewHolder holder, int position) {
        Info_Pembayaran_Model md = listdata.get(position);
        final Adapter_Info_Pembayaran.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_jadwal());
        holder.jenis_pembayaran.setText(listdata.get(position).getNama_pembayaran());

        holder.jumlah_bayar.setText(listdata.get(position).getJumlah_bayar());
        holder.tanggal_bayar.setText(listdata.get(position).getTanggal_bayar());
        holder.angsuran_ke.setText(listdata.get(position).getAngsuran_ke());
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
        private TextView kode, jenis_pembayaran, jumlah_bayar, tanggal_bayar, angsuran_ke, status, edit, hapus;
        String detailStatus;
        ProgressBar progress;
        ImageView gambar;
        String jumlah;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            jenis_pembayaran=(TextView)v.findViewById(R.id.jenis);
            jumlah_bayar = (TextView)v.findViewById(R.id.jumlah_bayar);
            tanggal_bayar = (TextView)v.findViewById(R.id.tanggal_bayar);
            angsuran_ke = (TextView)v.findViewById(R.id.angsuran_ke);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            status = (TextView)v.findViewById(R.id.status);
            gambar = (ImageView)v.findViewById(R.id.gambar);
        }
    }
}
