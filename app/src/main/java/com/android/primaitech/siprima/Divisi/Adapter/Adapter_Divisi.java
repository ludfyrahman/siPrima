package com.android.primaitech.siprima.Divisi.Adapter;

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
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.Divisi.Model.Divisi_Model;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.Proyek.Adapter.Adapter_Proyek;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
import com.android.primaitech.siprima.Proyek.Model.Proyek_Model;
import com.android.primaitech.siprima.Proyek.Proyek;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Divisi extends RecyclerView.Adapter<Adapter_Divisi.ViewHolder>  {
    private ArrayList<Divisi_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Divisi(Activity activity, ArrayList<Divisi_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Divisi.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_divisi, parent, false);
        Adapter_Divisi.ViewHolder vh = new Adapter_Divisi.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Divisi.ViewHolder holder, int position) {
        Divisi_Model md = listdata.get(position);
        final Adapter_Divisi.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_divisi());
        holder.nama_divisi.setText(listdata.get(position).getNama_divisi());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        Divisi divisi = new Divisi();
        edit = divisi.edit;
        hapus = divisi.hapus;
        detail = divisi.detail;
        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
        if (!hapus.equals("1"))
            holder.hapus.setVisibility(View.GONE);
        holder.kode.setVisibility(View.GONE);
        holder.mContext = context;
        holder.detailStatus = detail;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_unit, nama_divisi, edit, hapus;
        String detailStatus;
        ImageView gambar;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_divisi=(TextView)v.findViewById(R.id.nama_divisi);
            nama_unit=(TextView) v.findViewById(R.id.nama_unit);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Divisi "+nama_divisi.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mContext instanceof Divisi) {
                                        ((Divisi)mContext).delete(kode.getText().toString());
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
                    MenuData menuData = new MenuData();
                    try {
                        if(detailStatus.equals("1")){
                            Intent intent = new Intent(v.getContext(), Detail_Proyek.class);
                            intent.putExtra("nama_menu", nama_unit.getText().toString());
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
