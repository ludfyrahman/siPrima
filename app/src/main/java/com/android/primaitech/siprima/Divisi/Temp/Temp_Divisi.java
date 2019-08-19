package com.android.primaitech.siprima.Divisi.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Unit_Bisnis.Temp.Temp_Unit_Bisnis;

public class Temp_Divisi {
    private static Temp_Divisi mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefdivisi";
    private static final String nama_divisi = "nama_divisi";
    private static final String unit_bisnis = "unit_bisnis";
    private static final String kode_divisi = "kode_divisi";
    private static final String tipe_form = "tipe_form";
    private static final String kode_usaha = "kode_usaha";
    private Temp_Divisi(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Divisi getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Divisi(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(nama_divisi, null) != null) {
            return true;
        }
        return false;
    }
    public boolean clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public boolean setNamaDivisi(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_divisi, xst);
        editor.apply();

        return true;
    }
    public String getNama_divisi() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_divisi, null);
    }
    public boolean setKodeUsaha(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_usaha, xst);
        editor.apply();

        return true;
    }
    public String getKode_usaha() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_usaha, null);
    }
    public boolean setUnitBisnis(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(unit_bisnis, xst);
        editor.apply();

        return true;
    }
    public String getUnit_bisnis() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(unit_bisnis, null);
    }
    public boolean setKodeDivisi(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_divisi, xst);
        editor.apply();

        return true;
    }
    public String getKode_divisi() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_divisi, null);
    }
    public boolean setTipeForm(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tipe_form, xst);
        editor.apply();

        return true;
    }
    public String getTipe_form() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tipe_form, null);
    }
}
