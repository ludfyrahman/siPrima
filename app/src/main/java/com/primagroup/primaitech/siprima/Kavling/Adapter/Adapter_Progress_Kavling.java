package com.primagroup.primaitech.siprima.Kavling.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Kavling.Detail_Kavling;
import com.primagroup.primaitech.siprima.Kavling.Kavling;
import com.primagroup.primaitech.siprima.Kavling.Model.Progress_Kavling_Model;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Progress_Kavling extends RecyclerView.Adapter<Adapter_Progress_Kavling.ViewHolder>  {
    private ArrayList<Progress_Kavling_Model> listdata;
    private Activity activity;
    private Context context;
    boolean edit,hapus, detail;
    public Adapter_Progress_Kavling(Activity activity, ArrayList<Progress_Kavling_Model> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public Adapter_Progress_Kavling.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_progress_kavling, parent, false);
        Adapter_Progress_Kavling.ViewHolder vh = new Adapter_Progress_Kavling.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Progress_Kavling.ViewHolder holder, int position) {
        Progress_Kavling_Model md = listdata.get(position);
        final Adapter_Progress_Kavling.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_kavling());
        holder.judul.setText(listdata.get(position).getJudul());

        holder.deskripsi.setText(listdata.get(position).getDeskripsi());
        holder.tanggal.setText(listdata.get(position).getCreate_at());
        Glide.with(activity)
                .load(listdata.get(position).getFoto_kecil())
                .into(holder.gambar);
        Detail_Kavling detail_kavling = new Detail_Kavling();
        edit = detail_kavling.editprogres;
        hapus = detail_kavling.hapusprogres;
        if(!edit)
            holder.edit.setVisibility(View.GONE);
        if (!hapus)
            holder.hapus.setVisibility(View.GONE);
        holder.kode.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, judul, deskripsi, tanggal, edit, hapus;
        String detailStatus;
        ProgressBar progress;
        ImageView gambar;
        String jumlah;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            judul=(TextView)v.findViewById(R.id.judul);
            deskripsi = (TextView)v.findViewById(R.id.deskripsi);
            tanggal = (TextView)v.findViewById(R.id.tanggal);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus  "+judul.getText().toString())
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
        }
    }
}
