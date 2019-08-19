package com.android.primaitech.siprima.Kegiatan.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Divisi.Temp.Temp_Divisi;

public class Temp_Kegiatan {
    private static Temp_Kegiatan mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefdivisi";
    private static final String kode_karyawan = "foto_kegiatan";
    private static final String kode_kegiatan = "kode_kegiatan";
    private static final String solusi = "solusi";
    private static final String foto_kegiatan = "foto_kegiatan";
    private static final String tipe_form = "tipe_form";
    private Temp_Kegiatan(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Kegiatan getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Kegiatan(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(solusi, null) != null) {
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
    public boolean setKodeKaryawan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_karyawan, xst);
        editor.apply();

        return true;
    }
    public String getKode_karyawan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_karyawan, null);
    }
    public boolean setKodeKegiatan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_kegiatan, xst);
        editor.apply();

        return true;
    }
    public String getKode_kegiatan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_kegiatan, null);
    }
    public boolean setSolusi(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(solusi, xst);
        editor.apply();

        return true;
    }
    public String getSolusi() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(solusi, null);
    }
    public boolean setFotoKegiatan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(foto_kegiatan, xst);
        editor.apply();

        return true;
    }
    public String getFoto_kegiatan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(foto_kegiatan, null);
    }

}
