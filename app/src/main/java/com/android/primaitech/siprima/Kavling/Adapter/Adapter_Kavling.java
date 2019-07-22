package com.android.primaitech.siprima.Kavling.Adapter;

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
import com.android.primaitech.siprima.Kavling.Detail_Kavling;
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Kavling.Model.Kavling_Model;
import com.android.primaitech.siprima.Penjualan.Detail_Penjualan;
import com.android.primaitech.siprima.Proyek.Adapter.Adapter_Proyek;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
import com.android.primaitech.siprima.Proyek.Model.Proyek_Model;
import com.android.primaitech.siprima.Proyek.Proyek;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Kavling  extends RecyclerView.Adapter<Adapter_Kavling.ViewHolder>  {
    private ArrayList<Kavling_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Kavling(Activity activity, ArrayList<Kavling_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Kavling.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_kavling, parent, false);
        Adapter_Kavling.ViewHolder vh = new Adapter_Kavling.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Kavling.ViewHolder holder, int position) {
        Kavling_Model md = listdata.get(position);
        final Adapter_Kavling.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_kavling());
        holder.nama_kavling.setText(listdata.get(position).getNama_kategori()+"-"+listdata.get(position).getNama_kavling());

        holder.harga_jual.setText(listdata.get(position).getHarga_jual());
        holder.tipe_rumah.setText(listdata.get(position).getTipe_rumah());
        holder.create_at.setText(listdata.get(position).getCreate_at());
        holder.status.setText(listdata.get(position).getStatus());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        Glide.with(activity)
                .load(listdata.get(position).getDesain_rumah())
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
        private TextView kode, nama_kavling, harga_jual, tipe_rumah, create_at, status, nama_proyek, edit, hapus;
        String detailStatus;
        ProgressBar progress;
        ImageView gambar;
        String jumlah;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_kavling=(TextView)v.findViewById(R.id.nama_kavling);
            harga_jual = (TextView)v.findViewById(R.id.harga_jual);
            tipe_rumah = (TextView)v.findViewById(R.id.tipe_rumah);
            create_at = (TextView)v.findViewById(R.id.tanggal);
            status = (TextView)v.findViewById(R.id.status);
            nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Kavling "+nama_kavling.getText().toString())
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
                            Intent intent = new Intent(v.getContext(), Detail_Penjualan.class);
                            intent.putExtra("kode", kode.getText().toString());
                            intent.putExtra("nama_menu", nama_kavling.getText().toString());
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
