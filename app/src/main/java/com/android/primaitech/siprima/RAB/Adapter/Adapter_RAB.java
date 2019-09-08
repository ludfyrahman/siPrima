package com.android.primaitech.siprima.RAB.Adapter;

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
import android.widget.TextView;

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.R;
import com.android.primaitech.siprima.RAB.Detail_RAB;
import com.android.primaitech.siprima.RAB.Fragment_Rab_Proyek;
import com.android.primaitech.siprima.RAB.Fragment_Rab_Unit_Bisnis;
import com.android.primaitech.siprima.RAB.Model.RAB_Model;
import com.android.primaitech.siprima.RAB.RAB;

import java.util.ArrayList;

public class Adapter_RAB extends RecyclerView.Adapter<Adapter_RAB.ViewHolder>  {
    private ArrayList<RAB_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_RAB(Activity activity, ArrayList<RAB_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_RAB.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_rab, parent, false);
        Adapter_RAB.ViewHolder vh = new Adapter_RAB.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_RAB.ViewHolder holder, int position) {
        RAB_Model md = listdata.get(position);
        String status[] = {"", "Pembeli", "Calon Pembeli"};
        final Adapter_RAB.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_rab());
        holder.nama_usaha.setText(listdata.get(position).getNama_usaha());
        holder.nama_rab.setText(listdata.get(position).getNama_rab());
        holder.total_anggaran.setText(listdata.get(position).getTotal_anggaran());
        holder.limit_bulanan.setText(listdata.get(position).getLimit_bulanan());
//        holder.sisa_anggaran.setText(listdata.get(position).getSisa_anggaran());
        holder.status.setText(listdata.get(position).getStatus());
        holder.tanggal.setText(listdata.get(position).getCreate_at());
        if(listdata.get(position).getTipe_rab().equals("1")) {
            Fragment_Rab_Unit_Bisnis cl = new Fragment_Rab_Unit_Bisnis();
            edit = cl.edit;
            hapus = cl.hapus;
            detail = cl.detail;
        }else{
            Fragment_Rab_Proyek cl = new Fragment_Rab_Proyek();
            edit = cl.edit;
            hapus = cl.hapus;
            detail = cl.detail;
        }
        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
        if (!hapus.equals("1"))
            holder.hapus.setVisibility(View.GONE);
        holder.mContext = context;
        holder.kode.setVisibility(View.GONE);
        holder.detailStatus = detail;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, nama_rab, nama_usaha, total_anggaran, limit_bulanan, sisa_anggaran, status, tanggal, edit, hapus;
        String detailStatus;
        Context mContext;
        String jumlah;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_rab=(TextView)v.findViewById(R.id.nama_rab);
            nama_usaha=(TextView) v.findViewById(R.id.nama_usaha);
            total_anggaran = (TextView)v.findViewById(R.id.total_anggaran);
            limit_bulanan = (TextView)v.findViewById(R.id.limit_bulanan);
//            sisa_anggaran = (TextView)v.findViewById(R.id.sisa_anggaran);
            status = (TextView)v.findViewById(R.id.status);
            tanggal = (TextView)v.findViewById(R.id.tanggal);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus "+status.getText().toString()+" "+nama_rab.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.finishAffinity(dashboard_manager_dev.this);
                                    if (mContext instanceof RAB) {
                                        ((RAB)mContext).delete(kode.getText().toString());
                                    }
//                                    finish();
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
                            Intent intent = new Intent(v.getContext(), Detail_RAB.class);
                            intent.putExtra("nama_menu", nama_rab.getText().toString());
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