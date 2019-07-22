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
import com.android.primaitech.siprima.Kavling.Adapter.Adapter_Kavling;
import com.android.primaitech.siprima.Kavling.Detail_Kavling;
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Kavling.Model.Kavling_Model;
import com.android.primaitech.siprima.Penjualan.Model.Penjualan_Model;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Penjualan extends RecyclerView.Adapter<Adapter_Penjualan.ViewHolder>  {
    private ArrayList<Penjualan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Penjualan(Activity activity, ArrayList<Penjualan_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Penjualan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_penjualan, parent, false);
        Adapter_Penjualan.ViewHolder vh = new Adapter_Penjualan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Penjualan.ViewHolder holder, int position) {
        Penjualan_Model md = listdata.get(position);
        final Adapter_Penjualan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode());
        holder.nama_penjual.setText(listdata.get(position).getNama_penjual());

        holder.nama_pembeli.setText(listdata.get(position).getNama_pembeli());
        holder.nama_penjual.setText(listdata.get(position).getNama_penjual());
        holder.tanggal_penjualan.setText(listdata.get(position).getTanggal_penjualan());
        holder.harga_deal.setText(listdata.get(position).getHarga_jual_bersih());
        Glide.with(activity)
                .load(listdata.get(position).getCover())
                .into(holder.gambar);
        Kavling kavling = new Kavling();
        edit = kavling.edit;
        hapus = kavling.hapus;
        detail = kavling.detail;
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
        private TextView kode, nama_penjualan, nama_pembeli, nama_penjual, tanggal_penjualan, harga_deal, edit, hapus;
        String detailStatus;
        ProgressBar progress;
        ImageView gambar;
        String jumlah;
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
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Kavling "+nama_penjualan.getText().toString())
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
                        if(detailStatus.equals("1")){
                            Intent intent = new Intent(v.getContext(), Detail_Kavling.class);
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
