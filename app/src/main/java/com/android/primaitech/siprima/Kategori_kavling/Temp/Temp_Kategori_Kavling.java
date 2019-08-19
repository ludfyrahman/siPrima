package com.android.primaitech.siprima.Kategori_kavling.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Cuti.Temp.Temp_Cuti;

public class Temp_Kategori_Kavling {
    private static Temp_Kategori_Kavling mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefkategori";
    private static final String kode_mandor = "kode_mandor";
    private static final String kode_proyek = "kode_proyek";
    private static final String nama_kategori = "nama_kategori";
    private static final String kode_kategori = "kode_kategori";
    private static final String nama_proyek = "nama_proyek";
    private static final String nama_pengawas = "nama_pengawas";
    private static final String tipe_form = "tipe_form";

    //
//
    private Temp_Kategori_Kavling(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Kategori_Kavling getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Kategori_Kavling(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(nama_kategori, null) != null) {
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
    public boolean setKodeMandor(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_mandor, xst);
        editor.apply();

        return true;
    }
    public String getKode_mandor() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_mandor, null);
    }
    public boolean setKodeProyek(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_proyek, xst);
        editor.apply();

        return true;
    }
    public String getKode_proyek() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_proyek, null);
    }
    public boolean setNamaKategori(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_kategori, xst);
        editor.apply();

        return true;
    }
    public String getNama_kategori() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_kategori, null);
    }
    public boolean setKodeKategori(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_kategori, xst);
        editor.apply();

        return true;
    }
    public String getKode_kategori() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_kategori, null);
    }
    public boolean setNamaProyek(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_proyek, xst);
        editor.apply();

        return true;
    }
    public String getNama_proyek() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_proyek, null);
    }
    public boolean setNamaPengawas(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_pengawas, xst);
        editor.apply();

        return true;
    }
    public String getNama_pengawas() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_pengawas, null);
    }

}
