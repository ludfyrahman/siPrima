package com.android.primaitech.siprima.Akun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.primaitech.siprima.Config.AuthData;
import com.android.primaitech.siprima.R;

public class Fragment_Akun extends Fragment {
    TextView ubah_profil, keluar, nama_pengguna, email_pengguna;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_fragment_akun, container, false);
        ubah_profil = (TextView)v.findViewById(R.id.ubah_profil);
        ubah_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Ubah_Profil.class));
            }
        });
        nama_pengguna = (TextView)v.findViewById(R.id.nama_pengguna);
        email_pengguna = (TextView)v.findViewById(R.id.email_pengguna);
        nama_pengguna.setText(AuthData.getInstance(getActivity()).getUsername());
        email_pengguna.setText(AuthData.getInstance(getActivity()).getEmail());
        keluar = (TextView)v.findViewById(R.id.keluar);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.logo_app)
                        .setTitle("Keluar Akun")
                        .setMessage("Apakah Anda Yakin Ingin Keluar Dari Akun Ini ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AuthData.getInstance(getContext()).logout();
                                startActivity(new Intent(getActivity(), Login.class));


                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });
        return v;
    }
}
