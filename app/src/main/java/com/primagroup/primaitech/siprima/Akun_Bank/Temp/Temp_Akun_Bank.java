package com.primagroup.primaitech.siprima.Akun_Bank.Temp;

import android.content.Context;
import android.content.SharedPreferences;

public class Temp_Akun_Bank {

    private static Temp_Akun_Bank mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefbank";
    private static final String nama_rekening = "nama_rekening";
    private static final String nama_bank = "nama_bank";
    private static final String no_rekening = "no_rekening";
    private static final String nama_rab = "nama_rab";

    private static final String kode_rab = "kode_rab";
    private static final String nama_usaha = "nama_usaha";
    private static final String kode_usaha = "kode_usaha";
    private static final String tipe_akun = "tipe_akun";
    private static final String tipe_form = "tipe_form";
    private static final String kode_akun = "kode_akun";

//
//
    private Temp_Akun_Bank(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Akun_Bank getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Akun_Bank(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(kode_usaha, null) != null) {
            return true;
        }
        return false;
    }
    public boolean setKodeAkun(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_akun, xst);
        editor.apply();

        return true;
    }
    public String getKode_akun() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_akun, null);
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
    public boolean setNamaRekening(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_rekening, xst);
        editor.apply();

        return true;
    }
    public String getNama_rekening() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_rekening, null);
    }
    public boolean setNamaBank(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_bank, xst);
        editor.apply();

        return true;
    }
    public String getNama_bank() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_bank, null);
    }
    public boolean setNoRekening(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_rekening, xst);
        editor.apply();

        return true;
    }
    public String getNo_rekening() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_rekening, null);
    }
    public String getNama_rab() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_rab, null);
    }
    public boolean setNamaRab(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_rab, xst);
        editor.apply();

        return true;
    }
    public boolean setKodeRab(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_rab, xst);
        editor.apply();

        return true;
    }
    public String getKode_rab() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_rab, null);
    }
    public boolean setNamaUsaha(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_usaha, xst);
        editor.apply();

        return true;
    }
    public String getNama_usaha() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_usaha, null);
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
    public boolean setTipeAkun(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tipe_akun, xst);
        editor.apply();

        return true;
    }
    public String getTipe_akun() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tipe_akun, null);
    }
    public boolean clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
