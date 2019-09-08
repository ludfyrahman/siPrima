package com.android.primaitech.siprima.Proyek.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Penjualan.Detail_Penjualan;
import com.android.primaitech.siprima.Penjualan.Model.Penjualan_Model;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Penjualan_Proyek extends RecyclerView.Adapter<Adapter_Penjualan_Proyek.ViewHolder>  {
    private ArrayList<Penjualan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    Detail_Proyek detail_proyek = new Detail_Proyek();
    public Adapter_Penjualan_Proyek(Activity activity, ArrayList<Penjualan_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Penjualan_Proyek.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_penjualan, parent, false);
        Adapter_Penjualan_Proyek.ViewHolder vh = new Adapter_Penjualan_Proyek.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Penjualan_Proyek.ViewHolder holder, int position) {
        Penjualan_Model md = listdata.get(position);
        final Adapter_Penjualan_Proyek.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode());
        holder.nama_penjual.setText(listdata.get(position).getNama_penjual());

        holder.nama_pembeli.setText(listdata.get(position).getNama_pembeli());
        holder.nama_penjual.setText(listdata.get(position).getNama_penjual());
        holder.tanggal_penjualan.setText(listdata.get(position).getTanggal_penjualan());
        holder.harga_deal.setText(listdata.get(position).getHarga_jual_bersih());
        Glide.with(activity)
                .load(listdata.get(position).getCover())
                .into(holder.gambar);
        holder.kode.setVisibility(View.GONE);
        holder.edit.setVisibility(View.GONE);
        holder.hapus.setVisibility(View.GONE);
        holder.detailStatus = detail_proyek.detailjual;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView kode, nama_penjualan, nama_pembeli, nama_penjual, tanggal_penjualan, harga_deal, edit, hapus;
        boolean detailStatus;
        ImageView gambar;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_penjualan=(TextView)v.findViewById(R.id.nama_penjualan);
            nama_pembeli = (TextView)v.findViewById(R.id.nama_pembeli);
            nama_penjual = (TextView)v.findViewById(R.id.nama_penjual);
            tanggal_penjualan = (TextView)v.findViewById(R.id.tanggal_penjualan);
            harga_deal = (TextView)v.findViewById(R.id.harga_deal);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    try {
                        if(detailStatus){
                            Intent intent = new Intent(v.getContext(), Detail_Penjualan.class);
                            intent.putExtra("kode", kode.getText().toString());
                            intent.putExtra("nama_menu", nama_penjualan.getText().toString());
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
