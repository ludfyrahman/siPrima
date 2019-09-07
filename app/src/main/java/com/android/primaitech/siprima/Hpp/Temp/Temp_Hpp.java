package com.android.primaitech.siprima.Hpp.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Tipe_Rumah.Temp.Temp_Tipe_Rumah;

public class Temp_Hpp {
    private static Temp_Hpp mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefhpp";
    private static final String kode_hpp = "kode_hpp";
    private static final String tipe_form = "tipe_form";
    private static final String kode_produk = "kode_produk";

    //
//
    private Temp_Hpp(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Hpp getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Hpp(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(kode_hpp, null) != null) {
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
    public boolean setKodeHpp(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_hpp, xst);
        editor.apply();

        return true;
    }
    public String getKode_hpp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_hpp, null);
    }
    public boolean setKode_Produk(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_produk, xst);
        editor.apply();

        return true;
    }
    public String getKode_produk() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_produk, null);
    }
}
