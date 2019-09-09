package com.primagroup.primaitech.siprima.Lahan.Temp;

import android.content.Context;
import android.content.SharedPreferences;

public class Temp_Lahan {
    private static Temp_Lahan mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefcuti";
    private static final String nama_lahan = "nama_lahan";
    private static final String harga_lahan = "harga_lahan";
    private static final String alamat = "alamat";
    private static final String tgl_mulai_bayar = "tgl_mulai_bayar";
    private static final String tgl_akhir_bayar = "tgl_akhir_bayar";
    private static final String luas_lahan = "luas_lahan";
    private static final String kode_lahan = "kode_lahan";
    private static final String tipe_form = "tipe_form";

    //
//
    private Temp_Lahan(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Lahan getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Lahan(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(nama_lahan, null) != null) {
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
    public boolean setNamaLahan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_lahan, xst);
        editor.apply();

        return true;
    }
    public String getNama_lahan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_lahan, null);
    }
    public boolean setHargaLahan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(harga_lahan, xst);
        editor.apply();

        return true;
    }
    public String getHarga_lahan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(harga_lahan, null);
    }
    public boolean setAlamat(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(alamat, xst);
        editor.apply();

        return true;
    }
    public String getAlamat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(alamat, null);
    }
    public boolean setTanggalMulai(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tgl_mulai_bayar, xst);
        editor.apply();

        return true;
    }
    public String getTgl_mulai_bayar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tgl_mulai_bayar, null);
    }
    public boolean setTanggalAkhir(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tgl_akhir_bayar, xst);
        editor.apply();

        return true;
    }
    public String getTgl_akhir_bayar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tgl_akhir_bayar, null);
    }
    public boolean setLuasLahan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(luas_lahan, xst);
        editor.apply();

        return true;
    }
    public String getLuas_lahan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(luas_lahan, null);
    }
    public boolean setKodeLahan(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_lahan, xst);
        editor.apply();

        return true;
    }
    public String getKode_lahan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_lahan, null);
    }

}
