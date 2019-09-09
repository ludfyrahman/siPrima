package com.primagroup.primaitech.siprima.Dashboard.Temp;

import android.content.Context;
import android.content.SharedPreferences;

public class Temp_Menu {
    private static Temp_Menu mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefmenu";
    private static final String tanggal_awal = "tanggal_awal";
    private static final String menu = "menu";
    private static final String kode_menu = "kode_menu";
    private static final String jumlah = "jumlah";

    //
//
    private Temp_Menu(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Menu getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Menu(context);
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
    public boolean setMenu(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(menu, xst);
        editor.apply();

        return true;
    }
    public String getMenu() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(menu, null);
    }
    public boolean setKode_Menu(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_menu, xst);
        editor.apply();

        return true;
    }
    public String getKode_menu() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_menu, null);
    }
    public boolean setJumlah(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(jumlah, xst);
        editor.apply();

        return true;
    }
    public String getJumlah() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jumlah, null);
    }
}
