package com.android.primaitech.siprima.Kegiatan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.primaitech.siprima.Kegiatan.Detail_Kegiatan;
import com.android.primaitech.siprima.Kegiatan.Kegiatan;
import com.android.primaitech.siprima.Kegiatan.Model.Kegiatan_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Kegiatan extends RecyclerView.Adapter<Adapter_Kegiatan.ViewHolder>  {
    private ArrayList<Kegiatan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Kegiatan(Activity activity, ArrayList<Kegiatan_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Kegiatan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_kegiatan, parent, false);
        Adapter_Kegiatan.ViewHolder vh = new Adapter_Kegiatan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Kegiatan.ViewHolder holder, int position) {
        Kegiatan_Model md = listdata.get(position);
        final Adapter_Kegiatan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_kegiatan());
        holder.kegiatan.setText(listdata.get(position).getKegiatan());
        holder.nama_karyawan.setText(listdata.get(position).getNama_karyawan());

        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.nama_unit.setText(listdata.get(position).getNama_unit());
        holder.create_at.setText(listdata.get(position).getCreate_at());
        holder.status.setText(listdata.get(position).getStatus());
        Kegiatan kegiatan = new Kegiatan();
        edit = kegiatan.edit;
        hapus = kegiatan.hapus;
        detail = kegiatan.detail;
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
        String detailStatus;
        Context mContext;
        private TextView kode, nama_karyawan,nama_proyek, nama_unit, status, kegiatan, create_at, edit, hapus;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_karyawan=(TextView)v.findViewById(R.id.nama_karyawan);
            status = (TextView)v.findViewById(R.id.status);
            nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
            nama_karyawan = (TextView)v.findViewById(R.id.nama_karyawan);
            nama_unit = (TextView)v.findViewById(R.id.nama_unit);
            kegiatan = (TextView)v.findViewById(R.id.kegiatan);
            create_at = (TextView)v.findViewById(R.id.create_at);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Temp_Kegiatan.getInstance(v.getContext()).setTipeForm("edit");
//                    Temp_Kegiatan.getInstance(v.getContext()).setKodeKegiatan(kode.getText().toString());
//                    Intent intent = new Intent(v.getContext(), Form_Kegiatan.class);
//                    intent.putExtra("kode", kode.getText().toString());
//                    v.getContext().startActivity(intent);
                }
            });
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof Kegiatan) {
                        ((Kegiatan)mContext).delete(kode.getText().toString());
                    }
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(detailStatus.equals("1")){
                            Intent intent = new Intent(v.getContext(), Detail_Kegiatan.class);
                            intent.putExtra("nama_menu", kegiatan.getText().toString());
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
