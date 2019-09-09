package com.primagroup.primaitech.siprima.Hpp.Adapter;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.primagroup.primaitech.siprima.Lahan.Fragment_Hpp_Lahan;
import com.primagroup.primaitech.siprima.Lahan.Lahan;
import com.primagroup.primaitech.siprima.Hpp.Model.Hpp_Lahan_Model;
import com.android.primaitech.siprima.R;

import java.util.ArrayList;

public class Adapter_Hpp_Lahan extends RecyclerView.Adapter<Adapter_Hpp_Lahan.ViewHolder>  {
    private ArrayList<Hpp_Lahan_Model> listdata;
    private Activity activity;
    private Context context;
    String edit,hapus, detail;
    public Adapter_Hpp_Lahan(Activity activity, ArrayList<Hpp_Lahan_Model> listdata, Context context) {
        this.listdata = listdata;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public Adapter_Hpp_Lahan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_hpp, parent, false);
        Adapter_Hpp_Lahan.ViewHolder vh = new Adapter_Hpp_Lahan.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(Adapter_Hpp_Lahan.ViewHolder holder, int position) {
        Hpp_Lahan_Model md = listdata.get(position);
        final Adapter_Hpp_Lahan.ViewHolder x=holder;
        holder.kode.setText(listdata.get(position).getKode_hpp());
        holder.nama_biaya.setText(listdata.get(position).getNama_biaya());
        holder.jumlah_biaya.setText(listdata.get(position).getJumlah_biaya());
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
        private TextView kode, nama_biaya,jumlah_biaya, edit, hapus;
        public ViewHolder(View v) {
            super(v);
            kode=(TextView)v.findViewById(R.id.kode);
            nama_biaya=(TextView)v.findViewById(R.id.nama_biaya);
            jumlah_biaya = (TextView)v.findViewById(R.id.jumlah_biaya);
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
