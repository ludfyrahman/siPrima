package com.android.primaitech.siprima.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthData {
    private static AuthData mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String kodeauth = "kodeauth";
    private static final String kodeuser = "kodeuser";
    private static final String expired_date = "expired_date";
    private static final String sudahlogin = "n";

    private static final String kode_role = "kode_role";
    private static final String nama_user = "nama_user";
    private static final String email = "email";
    private static final String kode_unit = "kode_unit";
    private static final String kode_proyek = "kode_proyek";
    private static final String akses_data = "akses_data";

    private static final String stemail = "0";
    private static final String kode_penjualan = "kode_penjualan";
    private static final String kode_menu = "kode_menu";
    private static final String nama_menu = "nama_menu";


    private AuthData(Context context){
        mCtx = context;
    }
    public static synchronized AuthData getInstance(Context context){
        if (mInstance == null){
            mInstance = new AuthData(context);
        }
        return mInstance;
    }

    public boolean setdatauser(String xkode_auth, String xkode_role, String xkode_user,String xnama_user, String xkode_unit, String xkode_proyek,String xemail, String xakses_data){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kodeauth, xkode_auth);
        editor.putString(kode_role, xkode_role);
        editor.putString(kodeuser, xkode_user);
        editor.putString(nama_user, xnama_user);
        editor.putString(kode_unit, xkode_unit);
        editor.putString(kode_proyek, xkode_proyek);
        editor.putString(email, xemail);
        editor.putString(akses_data, xakses_data);

        editor.apply();

        return true;
    }
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(kodeuser, null) != null) {
            return true;
        }
        return false;
    }
    public boolean setdatapembeli(String xst, String xkode){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(stemail, xst);
        editor.putString(kode_penjualan, xkode);

        editor.apply();

        return true;
    }
    public boolean setstemail(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(stemail, xst);
        editor.apply();

        return true;
    }
    public boolean setKodeMenu(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_menu, xst);
        editor.apply();

        return true;
    }
    public boolean setNamaMenu(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_menu, xst);
        editor.apply();

        return true;
    }
    public boolean setdataauth(String xkodeauth, String xkodeuser,String xexpired_date, String sudahceklogin){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kodeauth, xkodeauth);
        editor.putString(kodeuser, xkodeuser);
        editor.putString(expired_date, xexpired_date);
        editor.putString(sudahlogin, sudahceklogin);

        editor.apply();

        return true;
    }



    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getAuthKey(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(kodeauth, null);
    }
    public String getKode_role(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(kode_role, null);
    }
    public String getKodeUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kodeuser, null);
    }
    public String getUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_user, null);
    }
    public String getKode_unit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_unit, null);
    }
    public String getKode_proyek() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_proyek, null);
    }

    public String getAksesData() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(akses_data, null);
    }
    public String getKode_menu() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_menu, null);
    }
    public String getNama_menu() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_menu, null);
    }
    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(email, null);
    }

    public String getKodePenjualan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_penjualan, null);
    }
    public String getStatusEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(stemail, null);
    }
}
