package com.android.primaitech.siprima.Lahan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.primaitech.siprima.Lahan.Fragment_Hpp_Lahan;
import com.android.primaitech.siprima.Lahan.Lahan;
import com.android.primaitech.siprima.Lahan.Model.Hpp_Lahan_Model;
import com.android.primaitech.siprima.Lahan.Model.Pembayaran_Lahan_Model;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Pembayaran_Lahan extends RecyclerView.Adapter<Adapter_Pembayaran_Lahan.ViewHolder>  {
    private ArrayList<Pembayaran_Lahan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Pembayaran_Lahan(Activity activity, ArrayList<Pembayaran_Lahan_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Pembayaran_Lahan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_pembayaran_lahan, parent, false);
        Adapter_Pembayaran_Lahan.ViewHolder vh = new Adapter_Pembayaran_Lahan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Pembayaran_Lahan.ViewHolder holder, int position) {
        Pembayaran_Lahan_Model md = listdata.get(position);
        final Adapter_Pembayaran_Lahan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_pembayaran());
        holder.jumlah_uang.setText(listdata.get(position).getJumlah_uang());
        holder.keterangan.setText(listdata.get(position).getKeterangan());
        holder.tanggal.setText(listdata.get(position).getCreate_at());
        holder.nama_rab.setText(listdata.get(position).getNama_rab());
        Glide.with(activity)
                .load(listdata.get(position).getBukti_bayar())
                .into(holder.gambar);
        Lahan lahan = new Lahan();
        Fragment fragment = new Fragment_Hpp_Lahan();
        edit = ((Fragment_Hpp_Lahan) fragment).edit;
        hapus = ((Fragment_Hpp_Lahan) fragment).hapus;
        detail = ((Fragment_Hpp_Lahan) fragment).detail;
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
        ImageView gambar;
        private TextView kode, jumlah_uang,keterangan, nama_rab, tanggal, edit, hapus;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_rab=(TextView)v.findViewById(R.id.nama_rab);
            keterangan = (TextView)v.findViewById(R.id.keterangan);
            jumlah_uang = (TextView)v.findViewById(R.id.jumlah_uang);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            tanggal = (TextView)v.findViewById(R.id.tanggal);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof Lahan) {
                        ((Lahan)mContext).delete(kode.getText().toString());
                    }
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        if(detailStatus.equals("1")){
//                            Intent intent = new Intent(v.getContext(), Detail_Lahan.class);
//                            intent.putExtra("nama_menu", nama_lahan.getText().toString());
//                            intent.putExtra("kode", kode.getText().toString());
//                            v.getContext().startActivity(intent);
//                        }
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }
}
