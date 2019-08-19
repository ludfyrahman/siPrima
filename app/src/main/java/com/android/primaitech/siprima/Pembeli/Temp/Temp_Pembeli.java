package com.android.primaitech.siprima.Pembeli.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Cuti.Temp.Temp_Cuti;

public class Temp_Pembeli {
    private static Temp_Pembeli mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefcuti";
    private static final String nama_pembeli = "nama_pembeli";
    private static final String nik = "nik";
    private static final String no_hp = "no_hp";
    private static final String email = "email";
    private static final String alamat_pembeli = "alamat_pembeli";
    private static final String kode_pembeli = "kode_pembeli";
    private static final String tipe_form = "tipe_form";

    //
//
    private Temp_Pembeli(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Pembeli getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Pembeli(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(nama_pembeli, null) != null) {
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
    public boolean setNamaPembeli(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_pembeli, xst);
        editor.apply();

        return true;
    }
    public String getNama_pembeli() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_pembeli, null);
    }
    public boolean setNik(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nik, xst);
        editor.apply();

        return true;
    }
    public String getNik() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nik, null);
    }
    public boolean setNoHp(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_hp, xst);
        editor.apply();

        return true;
    }
    public String getNo_hp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_hp, null);
    }
    public boolean setEmail(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(email, xst);
        editor.apply();

        return true;
    }
    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(email, null);
    }
    public boolean setAlamatPembeli(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(alamat_pembeli, xst);
        editor.apply();

        return true;
    }
    public String getAlamat_pembeli() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(alamat_pembeli, null);
    }
    public boolean setKodePembeli(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_pembeli, xst);
        editor.apply();

        return true;
    }
    public String getKode_pembeli() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_pembeli, null);
    }
}
