package com.android.primaitech.siprima.Promo.Adapter;

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

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Promo.Detail_Promo;
import com.android.primaitech.siprima.Promo.Model.Promo_Model;
import com.android.primaitech.siprima.Promo.Promo;
import com.android.primaitech.siprima.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_Promo extends RecyclerView.Adapter<Adapter_Promo.ViewHolder>  {
    private ArrayList<Promo_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Promo(Activity activity, ArrayList<Promo_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Promo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_promo, parent, false);
        Adapter_Promo.ViewHolder vh = new Adapter_Promo.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Promo.ViewHolder holder, int position) {
        Promo_Model md = listdata.get(position);
        final Adapter_Promo.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_promo());
        holder.nama_promo.setText(listdata.get(position).getNama_promo());

        holder.tanggal_mulai.setText(listdata.get(position).getTanggal_mulai());
        holder.tanggal_selesai.setText(listdata.get(position).getTanggal_selesai());
        holder.nama_usaha.setText(listdata.get(position).getNama_usaha());
        Glide.with(activity)
                .load(listdata.get(position).getCover())
                .into(holder.gambar);
        Promo promo = new Promo();
        edit = promo.edit;
        hapus = promo.hapus;
        detail = promo.detail;
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
        private TextView kode, nama_promo, tanggal_mulai, tanggal_selesai, nama_usaha, edit, hapus;
        String detailStatus;
        ImageView gambar;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_promo=(TextView)v.findViewById(R.id.nama_promo);
            tanggal_mulai = (TextView)v.findViewById(R.id.tanggal_mulai);
            tanggal_selesai = (TextView)v.findViewById(R.id.tanggal_selesai);
            nama_usaha = (TextView)v.findViewById(R.id.nama_usaha);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            gambar = (ImageView)v.findViewById(R.id.gambar);
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus Promo "+nama_promo.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mContext instanceof Promo) {
                                        ((Promo)mContext).delete(kode.getText().toString());
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
                            Intent intent = new Intent(v.getContext(), Detail_Promo.class);
                            intent.putExtra("nama_menu", nama_promo.getText().toString());
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
