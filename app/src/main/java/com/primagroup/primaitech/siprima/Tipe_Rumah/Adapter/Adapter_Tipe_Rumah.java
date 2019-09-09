package com.primagroup.primaitech.siprima.Tipe_Rumah.Adapter;

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
import com.android.primaitech.siprima.R;
import com.primagroup.primaitech.siprima.Tipe_Rumah.Detail_Tipe_Rumah;
import com.primagroup.primaitech.siprima.Tipe_Rumah.Form_Tipe_Rumah;
import com.primagroup.primaitech.siprima.Tipe_Rumah.Model.Tipe_Rumah_Model;
import com.primagroup.primaitech.siprima.Tipe_Rumah.Temp.Temp_Tipe_Rumah;
import com.primagroup.primaitech.siprima.Tipe_Rumah.Tipe_Rumah;

import java.util.ArrayList;

public class Adapter_Tipe_Rumah extends RecyclerView.Adapter<Adapter_Tipe_Rumah.ViewHolder>  {
    private ArrayList<Tipe_Rumah_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Tipe_Rumah(Activity activity, ArrayList<Tipe_Rumah_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Tipe_Rumah.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_tipe_rumah, parent, false);
        Adapter_Tipe_Rumah.ViewHolder vh = new Adapter_Tipe_Rumah.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Tipe_Rumah.ViewHolder holder, int position) {
        Tipe_Rumah_Model md = listdata.get(position);
        final Adapter_Tipe_Rumah.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_tipe());
        holder.tipe.setText(listdata.get(position).getNama_tipe());
        holder.hpp.setText(listdata.get(position).getHpp());
        holder.harga_jual.setText(listdata.get(position).getHarga_jual());
        Tipe_Rumah c = new Tipe_Rumah();
        edit = c.edit;
        hapus = c.hapus;
        detail = c.detail;
        if(!edit.equals("1"))
            holder.edit.setVisibility(View.GONE);
        if (!hapus.equals("1"))
            holder.hapus.setVisibility(View.GONE);
        holder.kode.setVisibility(View.GONE);
        holder.mContext = context;
        holder.detailStatus = detail;
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView kode, tipe, biaya_konstruksi, hpp, harga_jual, edit, hapus;
        String detailStatus;
        ImageView gambar;
        Context mContext;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            tipe=(TextView)v.findViewById(R.id.tipe);
            biaya_konstruksi=(TextView) v.findViewById(R.id.biaya_konstruksi);
            hpp=(TextView) v.findViewById(R.id.hpp);
            harga_jual=(TextView) v.findViewById(R.id.harga_jual);
            edit = (TextView)v.findViewById(R.id.edit);
            hapus = (TextView)v.findViewById(R.id.hapus);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Temp_Tipe_Rumah.getInstance(v.getContext()).setTipeForm("edit");
                    Temp_Tipe_Rumah.getInstance(v.getContext()).setKodeTipe(kode.getText().toString());
                    Intent intent = new Intent(v.getContext(), Form_Tipe_Rumah.class);
                    intent.putExtra("kode", kode.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setIcon(R.drawable.logo_app)
                            .setTitle("Hapus  "+tipe.getText().toString())
                            .setMessage("Apakah Anda Yakin ??")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mContext instanceof Tipe_Rumah) {
                                        ((Tipe_Rumah)mContext).delete(kode.getText().toString());
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
                            Intent intent = new Intent(v.getContext(), Detail_Tipe_Rumah.class);
                            intent.putExtra("nama_menu", tipe.getText().toString());
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
