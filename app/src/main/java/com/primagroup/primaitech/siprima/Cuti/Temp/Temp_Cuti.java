package com.primagroup.primaitech.siprima.Cuti.Temp;

import android.content.Context;
import android.content.SharedPreferences;

public class Temp_Cuti {
    private static Temp_Cuti mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefcuti";
    private static final String tanggal_awal = "tanggal_awal";
    private static final String tanggal_selesai = "tanggal_selesai";
    private static final String kegiatan = "kegiatan";
    private static final String kode_cuti = "kode_cuti";
    private static final String tipe_form = "tipe_form";

    //
//
    private Temp_Cuti(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Cuti getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Cuti(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(tanggal_awal, null) != null) {
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
    public boolean setTanggalAwal(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tanggal_awal, xst);
        editor.apply();

        return true;
    }
    public String getTanggal_awal() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_awal, null);
    }
    public boolean setTanggalSelesai(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tanggal_selesai, xst);
        editor.apply();

        return true;
    }
    public String getTanggal_selesai() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_selesai, null);
    }
    public boolean setKegiatan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kegiatan, xst);
        editor.apply();

        return true;
    }
    public String getKegiatan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kegiatan, null);
    }
    public boolean setKodeCuti(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_cuti, xst);
        editor.apply();

        return true;
    }
    public String getKode_cuti() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_cuti, null);
    }
}
