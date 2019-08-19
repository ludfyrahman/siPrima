package com.android.primaitech.siprima.Unit_Bisnis.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Akun_Bank.Temp.Temp_Akun_Bank;

public class Temp_Unit_Bisnis {
    private static Temp_Unit_Bisnis mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefunitbisnis";
    private static final String nama_menu = "nama_menu";
    private Temp_Unit_Bisnis(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Unit_Bisnis getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Unit_Bisnis(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(nama_menu, null) != null) {
            return true;
        }
        return false;
    }
    public boolean setNamaMenu(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_menu, xst);
        editor.apply();

        return true;
    }
    public String getNama_menu() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_menu, null);
    }
}
