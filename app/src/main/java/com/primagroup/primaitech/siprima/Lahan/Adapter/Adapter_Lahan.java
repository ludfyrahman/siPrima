package com.primagroup.primaitech.siprima.Lahan.Adapter;

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

import com.primagroup.primaitech.siprima.Lahan.Detail_Lahan;
import com.primagroup.primaitech.siprima.Lahan.Form_Lahan;
import com.primagroup.primaitech.siprima.Lahan.Lahan;
import com.primagroup.primaitech.siprima.Lahan.Model.Lahan_Model;
import com.primagroup.primaitech.siprima.Lahan.Temp.Temp_Lahan;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Lahan extends RecyclerView.Adapter<Adapter_Lahan.ViewHolder>  {
    private ArrayList<Lahan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Lahan(Activity activity, ArrayList<Lahan_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Lahan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_lahan, parent, false);
        Adapter_Lahan.ViewHolder vh = new Adapter_Lahan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Lahan.ViewHolder holder, int position) {
        Lahan_Model md = listdata.get(position);
        final Adapter_Lahan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_lahan());
        holder.nama_lahan.setText(listdata.get(position).getNama_lahan());
        holder.nama_proyek.setText(listdata.get(position).getNama_proyek());
        holder.status.setText(listdata.get(position).getStatus());
        holder.harga.setText(listdata.get(position).getHarga_lahan());
        holder.tanggal.setText(listdata.get(position).getTgl_akhir_bayar());
        Lahan lahan = new Lahan();
        edit = lahan.edit;
        hapus = lahan.hapus;
        detail = lahan.detail;
        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
        if (!hapus.equals("1"))
            holder.hapus.setVisibility(View.GONE);
        holder.detailStatus = detail;
        holder.kode.setVisibility(View.GONE);
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
        private TextView kode, nama_lahan,nama_proyek, harga, status, tanggal, edit, hapus;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_lahan=(TextView)v.findViewById(R.id.nama_lahan);
            nama_proyek = (TextView)v.findViewById(R.id.nama_proyek);
            status = (TextView)v.findViewById(R.id.status);
            harga = (TextView)v.findViewById(R.id.harga);
            tanggal = (TextView)v.findViewById(R.id.tanggal);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Temp_Lahan.getInstance(v.getContext()).setTipeForm("edit");
                    Temp_Lahan.getInstance(v.getContext()).setKodeLahan(kode.getText().toString());
                    Intent intent = new Intent(v.getContext(), Form_Lahan.class);
                    intent.putExtra("kode", kode.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mContext instanceof Lahan) {
//                        ((Lahan)mContext).delete(kode.getText().toString());
//                    }
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(detailStatus.equals("1")){
                            Intent intent = new Intent(v.getContext(), Detail_Lahan.class);
                            intent.putExtra("nama_menu", nama_lahan.getText().toString());
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
