package com.android.primaitech.siprima.Karyawan.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Unit_Bisnis.Temp.Temp_Unit_Bisnis;

public class Temp_Karyawan {
    private static Temp_Karyawan mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefkaryawan";
    private static final String nama_menu = "nama_menu";
    private static final String tipe = "tipe";
    private Temp_Karyawan(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Karyawan getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Karyawan(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(nama_menu, null) != null) {
            return true;
        }
        return false;
    }
    public boolean setNamaMenu(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_menu, xst);
        editor.apply();

        return true;
    }
    public String getNama_menu() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_menu, null);
    }
    public boolean setTipe(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tipe, xst);
        editor.apply();

        return true;
    }
    public String getTipe() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tipe, null);
    }
}