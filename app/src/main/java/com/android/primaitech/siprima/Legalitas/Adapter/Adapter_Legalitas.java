package com.android.primaitech.siprima.Legalitas.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Kavling.Detail_Kavling;
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Legalitas.Model.Legalitas_Model;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Legalitas extends RecyclerView.Adapter<Adapter_Legalitas.ViewHolder>  {
    private ArrayList<Legalitas_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    Detail_Proyek detail_proyek = new Detail_Proyek();
    public Adapter_Legalitas(Activity activity, ArrayList<Legalitas_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Legalitas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_legalitas, parent, false);
        Adapter_Legalitas.ViewHolder vh = new Adapter_Legalitas.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Legalitas.ViewHolder holder, int position) {
        Legalitas_Model md = listdata.get(position);
        final Adapter_Legalitas.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_legalitas());
        holder.nama.setText(listdata.get(position).getNama_legalitas());
        holder.no_legalitas.setText(listdata.get(position).getNo_surat());
        holder.tanggal_terbit.setText(listdata.get(position).getTgl_terbit());
        holder.kode.setVisibility(View.GONE);
        if(!detail_proyek.editdok)
            holder.edit.setVisibility(View.GONE);
        if (!detail_proyek.hapusdok)
            holder.hapus.setVisibility(View.GONE);
//        holder.detailStatus = detail_proyek.detailkavling;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView kode, nama, no_legalitas, tanggal_terbit, edit, hapus;
        boolean detailStatus;
        ImageView gambar;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama=(TextView)v.findViewById(R.id.nama);
            no_legalitas = (TextView)v.findViewById(R.id.no);
            tanggal_terbit = (TextView)v.findViewById(R.id.tanggal_terbit);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Legalitas "+nama.getText().toString())
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
                        if(detailStatus){
                            Intent intent = new Intent(v.getContext(), Detail_Kavling.class);
                            intent.putExtra("kode", kode.getText().toString());
                            intent.putExtra("nama_menu", nama.getText().toString());
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
