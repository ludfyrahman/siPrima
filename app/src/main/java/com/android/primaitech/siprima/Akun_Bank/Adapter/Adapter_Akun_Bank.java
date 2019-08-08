package com.android.primaitech.siprima.Akun_Bank.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.primaitech.siprima.Akun_Bank.Akun_bank;
import com.android.primaitech.siprima.Akun_Bank.Detail_Akun_Bank;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Proyek;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Unit_Bisnis;
import com.android.primaitech.siprima.Akun_Bank.Model.Akun_Bank_Model;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Akun_Bank extends RecyclerView.Adapter<Adapter_Akun_Bank.ViewHolder>  {
    private ArrayList<Akun_Bank_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Akun_Bank(Activity activity, ArrayList<Akun_Bank_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Akun_Bank.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_akun_bank, parent, false);
        Adapter_Akun_Bank.ViewHolder vh = new Adapter_Akun_Bank.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Akun_Bank.ViewHolder holder, int position) {
        Akun_Bank_Model md = listdata.get(position);
        final ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_akunbank());
        holder.nama_rekening.setText(listdata.get(position).getNama_rekening());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.nama_bank.setText(listdata.get(position).getNama_bank());
        holder.no_rekening.setText(listdata.get(position).getNo_rekening());
        holder.saldo.setText(listdata.get(position).getSaldo());
        Fragment fragment;
        if(listdata.get(position).getTipe_akun().equals("1")) {
            fragment = new Fragment_Ab_Unit_Bisnis();
            edit = ((Fragment_Ab_Unit_Bisnis) fragment).edit;
            hapus = ((Fragment_Ab_Unit_Bisnis) fragment).hapus;
            detail = ((Fragment_Ab_Unit_Bisnis) fragment).detail;
        }else{
            fragment = new Fragment_Ab_Proyek();
            edit = ((Fragment_Ab_Proyek) fragment).edit;
            hapus = ((Fragment_Ab_Proyek) fragment).hapus;
            detail = ((Fragment_Ab_Proyek) fragment).detail;
        }
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
        private TextView kode, nama_rekening, nama_unit, nama_bank, no_rekening, saldo, edit, hapus;
        String detailStatus;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_rekening=(TextView)v.findViewById(R.id.nama_rekening);
            nama_unit=(TextView) v.findViewById(R.id.nama_unit);
            nama_bank = (TextView)v.findViewById(R.id.nama_bank);
            saldo = (TextView)v.findViewById(R.id.saldo);
            no_rekening = (TextView)v.findViewById(R.id.no_rekening);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Akun Bank "+nama_unit.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.finishAffinity(dashboard_manager_dev.this);
                                    if (mContext instanceof Akun_bank) {
                                        ((Akun_bank)mContext).delete(kode.getText().toString());
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
                            Intent intent = new Intent(v.getContext(), Detail_Akun_Bank.class);
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