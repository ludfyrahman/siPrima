package com.android.primaitech.siprima.Follow_Up;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Divisi.Temp.Temp_Divisi;

public class Temp_Follow_Up {
    private static Temp_Follow_Up mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "preffollowup";
    private static final String code = "code";
    private static final String tipe_form = "tipe_form";
    private Temp_Follow_Up(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Follow_Up getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Follow_Up(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(code, null) != null) {
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
    public boolean setCode(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(code, xst);
        editor.apply();

        return true;
    }
    public String getCode() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(code, null);
    }
}
