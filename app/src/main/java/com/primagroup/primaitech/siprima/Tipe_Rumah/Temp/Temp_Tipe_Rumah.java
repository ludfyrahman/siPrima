package com.primagroup.primaitech.siprima.Tipe_Rumah.Temp;

import android.content.Context;
import android.content.SharedPreferences;

public class Temp_Tipe_Rumah {
    private static Temp_Tipe_Rumah mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "preftiperumah";
    private static final String kode_tipe = "kode_tipe";
    private static final String tipe_form = "tipe_form";

    //
//
    private Temp_Tipe_Rumah(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Tipe_Rumah getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Tipe_Rumah(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(kode_tipe, null) != null) {
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
    public boolean setKodeTipe(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_tipe, xst);
        editor.apply();

        return true;
    }
    public String getKode_tipe() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_tipe, null);
    }
}
