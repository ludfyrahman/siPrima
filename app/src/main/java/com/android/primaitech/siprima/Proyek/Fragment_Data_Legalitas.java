package com.android.primaitech.siprima.Proyek;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.primaitech.siprima.R;

public class Fragment_Data_Legalitas extends Fragment {
    Detail_Proyek detail_proyek = new Detail_Proyek();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_data_legalitas, container, false);
        return v;
    }

}
