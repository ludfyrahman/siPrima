package com.android.primaitech.siprima.Karyawan.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Karyawan.Detail_Karyawan;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Proyek;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Unit_Bisnis;
import com.android.primaitech.siprima.Karyawan.Karyawan;
import com.android.primaitech.siprima.Karyawan.Model.Karyawan_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Karyawan extends RecyclerView.Adapter<Adapter_Karyawan.ViewHolder>  {
    private ArrayList<Karyawan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Karyawan(Activity activity, ArrayList<Karyawan_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Karyawan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_karyawan, parent, false);
        Adapter_Karyawan.ViewHolder vh = new Adapter_Karyawan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Karyawan.ViewHolder holder, int position) {
        Karyawan_Model md = listdata.get(position);
        final Adapter_Karyawan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_karyawan());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());
        holder.nama_divisi.setText(listdata.get(position).getNama_divisi());
        holder.tanggal_gabung.setText(listdata.get(position).getTanggal_gabung());
        Fragment fragment;
        if(listdata.get(position).getTipe_karyawan().equals("1")) {
            Fragment_K_Unit_Bisnis cl = new Fragment_K_Unit_Bisnis();
            edit = cl.edit;
            hapus = cl.hapus;
            detail = cl.detail;
        }else{
            Fragment_K_Proyek cl = new Fragment_K_Proyek();
            edit = cl.edit;
            hapus = cl.hapus;
            detail = cl.detail;
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
        private TextView kode, nama_unit, nama_proyek, nama_karyawan, nama_divisi, tanggal_gabung, edit, hapus;
        String detailStatus;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_proyek=(TextView)v.findViewById(R.id.nama_proyek);
            nama_unit=(TextView) v.findViewById(R.id.nama_unit);
            nama_karyawan = (TextView)v.findViewById(R.id.nama_karyawan);
            nama_divisi = (TextView)v.findViewById(R.id.nama_divisi);
            tanggal_gabung = (TextView)v.findViewById(R.id.tanggal_gabung);
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
                                    if (mContext instanceof Karyawan) {
                                        ((Karyawan)mContext).delete(kode.getText().toString());
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
                            Intent intent = new Intent(v.getContext(), Detail_Karyawan.class);
                            intent.putExtra("nama_menu", nama_karyawan.getText().toString());
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