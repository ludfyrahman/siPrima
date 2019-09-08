package com.android.primaitech.siprima.Dashboard.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.Config.MenuData;
import com.android.primaitech.siprima.Dashboard.Dashboard;
import com.android.primaitech.siprima.Dashboard.Detail_Menu;
import com.android.primaitech.siprima.Dashboard.Model.MenuModel;
import com.android.primaitech.siprima.Dashboard.Temp.Temp_Menu;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.ViewHolder>  {
    private ArrayList<MenuModel> listdata;
    private Activity activity;
    private Context context;
    public AdapterMenu(Activity activity, ArrayList<MenuModel> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
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
        int id = listdata.get(position).getGambar();
        if (id == 0){
            id = R.drawable.logo;
        }
        holder.thumbnail.setImageResource(id);
        holder.progress.setVisibility(View.GONE);
        holder.kode_menu.setText(listdata.get(position).getKode_menu());
        holder.link.setVisibility(View.GONE);
        holder.kode_menu.setVisibility(View.GONE);
        holder.mContext = context;
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
        Context mContext;
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


                            if (mContext instanceof Detail_Menu) {
                                ((Detail_Menu)mContext).checkData(kode_menu.getText().toString());
                            }
                            if (mContext instanceof Dashboard) {
                                ((Dashboard)mContext).checkData(kode_menu.getText().toString());
                            }
                            int jumlah = 0;
                            if (Temp_Menu.getInstance(v.getContext()).getJumlah() == null || Temp_Menu.getInstance(v.getContext()).getJumlah().equals("0"))
                                jumlah = 0;
                            else
                                jumlah = Integer.parseInt(Temp_Menu.getInstance(v.getContext()).getJumlah());

                            Log.d("pesan", "jumlah menunya adalah "+jumlah);
                            Log.d("pesan", "kode menunya adalah "+kode_menu.getText().toString());
                        if (jumlah > 0){
                            Log.d("pesan", "ada di detail menunya");
                            Intent intent =  new Intent(v.getContext(), Detail_Menu.class);
                            Temp_Menu.getInstance(v.getContext()).setMenu(judul.getText().toString());
                            Temp_Menu.getInstance(v.getContext()).setKode_Menu(kode_menu.getText().toString());
                            intent.putExtra("kode_menu", kode_menu.getText().toString());
                            intent.putExtra("nama_menu",judul.getText().toString());
                            v.getContext().startActivity(intent);
                        }else{
                            Log.d("pesan", "ada di menunya sekarang");
                            Intent intent = new Intent(v.getContext(), menuData.halaman(kode_menu.getText().toString()));
//                            Intent intent = new Intent(v.getContext(), menuData.halaman(kode_menu.getText().toString()));
                            AuthData.getInstance(v.getContext()).setKodeMenu(kode_menu.getText().toString());
                            AuthData.getInstance(v.getContext()).setNamaMenu(judul.getText().toString());
                            intent.putExtra("kode_menu", kode_menu.getText().toString());
                            intent.putExtra("nama_menu",judul.getText().toString());
                            v.getContext().startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.d("pesan", "error "+e.getMessage());
                    }
                }
            });
        }
    }
}
