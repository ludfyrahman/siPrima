package com.primagroup.primaitech.siprima.Kategori_kavling.Adapter;

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
import com.primagroup.primaitech.siprima.Kategori_kavling.Detail_Kategori_Kavling;
import com.primagroup.primaitech.siprima.Kategori_kavling.Form_Kategori_Kavling;
import com.primagroup.primaitech.siprima.Kategori_kavling.Kategori_kavling;
import com.primagroup.primaitech.siprima.Kategori_kavling.Model.Kategori_Kavling_Model;
import com.primagroup.primaitech.siprima.Kategori_kavling.Temp.Temp_Kategori_Kavling;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Kategori_Kavling extends RecyclerView.Adapter<Adapter_Kategori_Kavling.ViewHolder>  {
    private ArrayList<Kategori_Kavling_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Kategori_Kavling(Activity activity, ArrayList<Kategori_Kavling_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Kategori_Kavling.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_kategori_kavling, parent, false);
        Adapter_Kategori_Kavling.ViewHolder vh = new Adapter_Kategori_Kavling.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Kategori_Kavling.ViewHolder holder, int position) {
        Kategori_Kavling_Model md = listdata.get(position);
        final Adapter_Kategori_Kavling.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_kategori());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.nama_kategori.setText(listdata.get(position).getNama_kategori());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());
        Kategori_kavling kategori_kavling = new Kategori_kavling();
        edit = kategori_kavling.edit;
        hapus = kategori_kavling.hapus;
        detail = kategori_kavling.detail;
        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
        if (!hapus.equals("1"))
            holder.hapus.setVisibility(View.GONE);
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
        private TextView kode, nama_proyek, nama_kategori, nama_karyawan, edit, hapus;
        String detailStatus;
        ImageView gambar;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_proyek=(TextView)v.findViewById(R.id.nama_proyek);
            nama_kategori=(TextView) v.findViewById(R.id.nama_kategori);
            nama_karyawan=(TextView) v.findViewById(R.id.nama_karyawan);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Temp_Kategori_Kavling.getInstance(v.getContext()).setTipeForm("edit");
                    Temp_Kategori_Kavling.getInstance(v.getContext()).setKodeKategori(kode.getText().toString());
                    Intent intent = new Intent(v.getContext(), Form_Kategori_Kavling.class);
                    intent.putExtra("kode", kode.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
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
                                    if (mContext instanceof Kategori_kavling) {
                                        ((Kategori_kavling)mContext).delete(kode.getText().toString());
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
                            Intent intent = new Intent(v.getContext(), Detail_Kategori_Kavling.class);
                            intent.putExtra("nama_menu", nama_kategori.getText().toString());
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
