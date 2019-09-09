package com.primagroup.primaitech.siprima.Pembeli.Adapter;

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

import com.primagroup.primaitech.siprima.Config.MenuData;
import com.primagroup.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.primagroup.primaitech.siprima.Pembeli.Form_Pembeli;
import com.primagroup.primaitech.siprima.Pembeli.Fragment_Calon_Pembeli;
import com.primagroup.primaitech.siprima.Pembeli.Fragment_Pembeli;
import com.primagroup.primaitech.siprima.Pembeli.Model.Pembeli_Model;
import com.primagroup.primaitech.siprima.Pembeli.Pembeli;
import com.primagroup.primaitech.siprima.Pembeli.Temp.Temp_Pembeli;
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
        holder.status.setText(status[listdata.get(position).getStatus()]);
        holder.status_pembeli = listdata.get(position).getStatus();
        if(listdata.get(position).getStatus() == 1) {
            Fragment_Pembeli cl = new Fragment_Pembeli();
            edit = cl.edit;
            hapus = cl.hapus;
            detail = cl.detail;
        }else{
            Fragment_Calon_Pembeli cl = new Fragment_Calon_Pembeli();
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
                    Temp_Pembeli.getInstance(v.getContext()).setTipeForm("edit");
                    Temp_Pembeli.getInstance(v.getContext()).setKodePembeli(kode.getText().toString());
                    Intent intent = new Intent(v.getContext(), Form_Pembeli.class);
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