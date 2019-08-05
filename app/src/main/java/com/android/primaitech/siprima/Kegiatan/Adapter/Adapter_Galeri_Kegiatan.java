package com.android.primaitech.siprima.Kegiatan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.primaitech.siprima.Kegiatan.Detail_Kegiatan;
import com.android.primaitech.siprima.Kegiatan.Kegiatan;
import com.android.primaitech.siprima.Kegiatan.Model.Galeri_Kegiatan_Model;
import com.android.primaitech.siprima.Kegiatan.Model.Kegiatan_Model;
import com.android.primaitech.siprima.MainActivity;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;
import com.github.tntkhang.fullscreenimageview.library.FullScreenImageViewActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Adapter_Galeri_Kegiatan extends RecyclerView.Adapter<Adapter_Galeri_Kegiatan.ViewHolder>  {
    private ArrayList<Galeri_Kegiatan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Galeri_Kegiatan(Activity activity, ArrayList<Galeri_Kegiatan_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Galeri_Kegiatan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_galeri_kegiatan, parent, false);
        Adapter_Galeri_Kegiatan.ViewHolder vh = new Adapter_Galeri_Kegiatan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Galeri_Kegiatan.ViewHolder holder, int position) {
        Galeri_Kegiatan_Model md = listdata.get(position);
        final Adapter_Galeri_Kegiatan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_galery());
        holder.keterangan.setText(listdata.get(position).getKeterangan());
        holder.gambarText = listdata.get(position).getFoto();
        Glide.with(activity)
                .load(listdata.get(position).getFoto_kecil())
                .into(holder.gambar);
        holder.edit.setVisibility(View.GONE);
        holder.kode.setVisibility(View.GONE);
        holder.index = listdata.get(position).getIndex();
        holder.mContext = context;

    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        String gambarText;
        Context mContext;
        private TextView kode, keterangan,tanggal, edit, hapus;
        ImageView gambar;
        int index;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            keterangan=(TextView)v.findViewById(R.id.keterangan);
//            tanggal = (TextView)v.findViewById(R.id.tanggal);
            edit = (TextView)v.findViewById(R.id.edit);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus = (TextView)v.findViewById(R.id.hapus);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof Detail_Kegiatan) {
                        ((Detail_Kegiatan)mContext).delete(kode.getText().toString());
                    }
                }
            });
            gambar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof Detail_Kegiatan) {
                        ((Detail_Kegiatan)mContext).onImageClickAction(index);
                    }

                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    try {
//                        if(detailStatus.equals("1")){
//                            Intent intent = new Intent(v.getContext(), Detail_Kegiatan.class);
//                            intent.putExtra("nama_menu", kegiatan.getText().toString());
//                            intent.putExtra("kode", kode.getText().toString());
//                            v.getContext().startActivity(intent);
//                        }
//                    } catch (Exception e) {
//                        Log.d("pesan", "error");
//                    }
                }
            });
        }
    }
}
