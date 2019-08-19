package com.android.primaitech.siprima.Pembeli.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.primaitech.siprima.Akun_Bank.Adapter.Adapter_Akun_Bank;
import com.android.primaitech.siprima.Akun_Bank.Akun_bank;
import com.android.primaitech.siprima.Akun_Bank.Detail_Akun_Bank;
import com.android.primaitech.siprima.Akun_Bank.Form_Akun_Bank;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Proyek;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Unit_Bisnis;
import com.android.primaitech.siprima.Akun_Bank.Model.Akun_Bank_Model;
import com.android.primaitech.siprima.Akun_Bank.Temp.Temp_Akun_Bank;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Kegiatan.Detail_Kegiatan;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Fragment_Calon_Pembeli;
import com.android.primaitech.siprima.Pembeli.Fragment_Pembeli;
import com.android.primaitech.siprima.Pembeli.Model.Pembeli_Model;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Pembeli extends RecyclerView.Adapter<Adapter_Pembeli.ViewHolder>  {
    private ArrayList<Pembeli_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Pembeli(Activity activity, ArrayList<Pembeli_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Pembeli.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_pembeli, parent, false);
        Adapter_Pembeli.ViewHolder vh = new Adapter_Pembeli.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pembeli.ViewHolder holder, int position) {
        Pembeli_Model md = listdata.get(position);
        String status[] = {"", "Pembeli", "Calon Pembeli"};
        final Adapter_Pembeli.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_pembeli());
        holder.nama_pembeli.setText(listdata.get(position).getNama_pembeli());
        holder.no_ktp.setText(listdata.get(position).getNo_ktp());
        holder.no_hp.setText(listdata.get(position).getNo_hp());
        Fragment fragment;
        holder.status.setText(status[listdata.get(position).getStatus()]);
        holder.status_pembeli = listdata.get(position).getStatus();
        if(listdata.get(position).getStatus() == 1) {
            fragment = new Fragment_Pembeli();
            edit = ((Fragment_Pembeli) fragment).edit;
            hapus = ((Fragment_Pembeli) fragment).hapus;
            detail = ((Fragment_Pembeli) fragment).detail;
        }else{
            fragment = new Fragment_Calon_Pembeli();
            edit = ((Fragment_Calon_Pembeli) fragment).edit;
            hapus = ((Fragment_Calon_Pembeli) fragment).hapus;
            detail = ((Fragment_Calon_Pembeli) fragment).detail;
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
        private TextView kode, nama_pembeli, no_ktp, status, no_hp, edit, hapus;
        String detailStatus;
        Context mContext;
        int status_pembeli;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_pembeli=(TextView)v.findViewById(R.id.nama_pembeli);
            no_ktp=(TextView) v.findViewById(R.id.no_ktp);
            status = (TextView)v.findViewById(R.id.status);
            no_hp = (TextView)v.findViewById(R.id.no_hp);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pembeli m = new Pembeli();
                    m.tipe_pembeli = String.valueOf(status_pembeli);
                    Temp_Akun_Bank.getInstance(v.getContext()).setTipeForm("edit");
                    Temp_Akun_Bank.getInstance(v.getContext()).setKodeAkun(kode.getText().toString());
                    Intent intent = new Intent(v.getContext(), Form_Akun_Bank.class);
                    intent.putExtra("kode", kode.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus "+status.getText().toString()+" "+nama_pembeli.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.finishAffinity(dashboard_manager_dev.this);
                                    if (mContext instanceof Pembeli) {
                                        ((Pembeli)mContext).delete(kode.getText().toString());
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
                            Intent intent = new Intent(v.getContext(), Detail_Pembeli.class);
                            intent.putExtra("nama_menu", nama_pembeli.getText().toString());
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