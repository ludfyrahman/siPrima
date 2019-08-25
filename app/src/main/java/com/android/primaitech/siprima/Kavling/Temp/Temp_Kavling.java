package com.android.primaitech.siprima.Kavling.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Divisi.Temp.Temp_Divisi;

public class Temp_Kavling {
    private static Temp_Kavling mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefkavling";
    private static final String nama_proyek = "nama_proyek";
    private static final String kode_proyek = "kode_proyek";
    private static final String nama_kategori_kavling = "nama_kategori_kavling";
    private static final String tipe_rumah = "tipe_rumah";
    private static final String tipe_tambah_data = "tipe_tambah_data";
    private static final String tipe_form = "tipe_form";
    private static final String no_kavling = "no_kavling";
    private static final String no_awal_kavling = "no_awal_kavling";
    private static final String no_akhir_kavling = "no_akhir_kavling";
    private static final String kode_kavling = "kode_kavling";
    private Temp_Kavling(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Kavling getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Kavling(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(kode_proyek, null) != null) {
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
    public boolean setNama_Proyel(String xst){
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
    public boolean setKode_Proyek(String xst){
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
    public boolean setNama_Kategori_Kavling(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_kategori_kavling, xst);
        editor.apply();

        return true;
    }
    public String getNama_kategori_kavling() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_kategori_kavling, null);
    }
    public boolean setTipe_Rumah(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tipe_rumah, xst);
        editor.apply();

        return true;
    }
    public String getTipe_rumah() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tipe_rumah, null);
    }
    public boolean setTipe_Tambah_Data(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tipe_tambah_data, xst);
        editor.apply();

        return true;
    }
    public String getTipe_tambah_data() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tipe_tambah_data, null);
    }
    public boolean setNo_Kavling(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_kavling, xst);
        editor.apply();

        return true;
    }
    public String getNo_kavling() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_kavling, null);
    }
    public boolean setNo_Awal_Kavling(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_awal_kavling, xst);
        editor.apply();

        return true;
    }
    public String getNo_awal_kavling() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_awal_kavling, null);
    }
    public boolean setNo_Akhir_Kavling(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_akhir_kavling, xst);
        editor.apply();

        return true;
    }
    public String getNo_akhir_kavling() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_akhir_kavling, null);
    }
    public boolean setKode_Kavling(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_kavling, xst);
        editor.apply();

        return true;
    }
    public String getKode_kavling() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_kavling, null);
    }
}
