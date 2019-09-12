package com.primagroup.primaitech.siprima.Proyek.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Config.MenuData;
import com.primagroup.primaitech.siprima.Proyek.Detail_Proyek;
import com.primagroup.primaitech.siprima.Proyek.Model.Proyek_Model;
import com.primagroup.primaitech.siprima.Proyek.Proyek;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Proyek extends RecyclerView.Adapter<Adapter_Proyek.ViewHolder>  {
    private ArrayList<Proyek_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Proyek(Activity activity, ArrayList<Proyek_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Proyek.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_proyek, parent, false);
        Adapter_Proyek.ViewHolder vh = new Adapter_Proyek.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Proyek.ViewHolder holder, int position) {
        Proyek_Model md = listdata.get(position);
        final Adapter_Proyek.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_proyek());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.link_web.setText(listdata.get(position).getLink_web());
        holder.luas_proyek.setText(listdata.get(position).getLuas_proyek());
        holder.tanggal_mulai.setText(listdata.get(position).getTanggal_mulai());
        Glide.with(activity)
                .load(listdata.get(position).getBanner_proyek_kecil())
                .into(holder.gambar);
            Proyek proyek = new Proyek();
            edit = proyek.edit;
            hapus = proyek.hapus;
            detail = proyek.detail;
//        if(!edit.equals("1"))
//            holder.edit.setVisibility(View.GONE);
//        if (!hapus.equals("1"))
//            holder.hapus.setVisibility(View.GONE);
        Log.d("pesan", "editnya adalah "+edit +" hapusnya adalah "+hapus);
        holder.kode.setVisibility(View.GONE);
        holder.detailStatus = detail;
        holder.mContext = context;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_unit, nama_proyek, link_web, luas_proyek, tanggal_mulai, edit, hapus;
        String detailStatus;
        ImageView gambar;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_proyek=(TextView)v.findViewById(R.id.nama_proyek);
            nama_unit=(TextView) v.findViewById(R.id.nama_unit);
            link_web = (TextView)v.findViewById(R.id.link_web);
            luas_proyek = (TextView)v.findViewById(R.id.luas_proyek);
            tanggal_mulai = (TextView)v.findViewById(R.id.tanggal_mulai);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Proyek "+nama_proyek.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mContext instanceof Proyek) {
                                        ((Proyek)mContext).delete(kode.getText().toString());
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