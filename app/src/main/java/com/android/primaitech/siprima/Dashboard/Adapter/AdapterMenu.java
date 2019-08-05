package com.android.primaitech.siprima.Dashboard.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Config.ServerAccess;
import com.android.primaitech.siprima.Dashboard.Model.MenuModel;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolder>  {
    private ArrayList<MenuModel> listdata;
    private Activity activity;
    private Context context;
    public AdapterMenu(Activity activity, ArrayList<MenuModel> listdata) {
        this.listdata = listdata;
        this.activity = activity;
    }

    @Override
    public AdapterMenu.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_menu_dashboard, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuModel md = listdata.get(position);
        holder.link.setText(listdata.get(position).getLink());
        holder.judul.setText(listdata.get(position).getJudul());
        final ViewHolder x=holder;
//        Glide.with(activity)
//                .load(listdata.get(position).getGambar())
//                .into(holder.thumbnail);
        holder.thumbnail.setImageResource(listdata.get(position).getGambar());
        holder.progress.setVisibility(View.GONE);
        holder.kode_menu.setText(listdata.get(position).getKode_menu());
        holder.link.setVisibility(View.GONE);
        holder.kode_menu.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView link, judul, kode_menu;
        private ImageView thumbnail;

        ProgressBar progress;
        String jumlah;
        public ViewHolder(View v) {
            super(v);
            link=(TextView)v.findViewById(R.id.link);
            judul=(TextView)v.findViewById(R.id.judul);
            progress = (ProgressBar)v.findViewById(R.id.progress);
            thumbnail=(ImageView) v.findViewById(R.id.thumbnail);
            kode_menu = (TextView)v.findViewById(R.id.kode_menu);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuData menuData = new MenuData();
                    try {
                        Intent intent = new Intent(v.getContext(), menuData.halaman(kode_menu.getText().toString()));
                        menuData.nama_menu = judul.getText().toString();
                        menuData.kode_menu = kode_menu.getText().toString();
                        intent.putExtra("kode_menu", kode_menu.getText().toString());
                        intent.putExtra("nama_menu",judul.getText().toString());
                        v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Log.d("pesan", "error");
                    }
                }
            });
        }
    }
}
