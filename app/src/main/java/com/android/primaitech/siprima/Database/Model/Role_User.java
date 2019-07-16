package com.android.primaitech.siprima.Database.Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Role_User {
    public static final String TABLE_NAME = "role_user";

    public static final String final_kode_role = "kode_role";
    public static final String final_nama_role = "nama_role";
    public static final String final_revisi_code = "revisi_code";

    private String kode_role;
    private String nama_role;
    private String revisi_code;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + final_kode_role + " TEXT PRIMARY KEY ,"
                    + final_nama_role + " TEXT,"
                    + final_revisi_code + " TEXT"
                    + ")";
    public static final String TRUNCATE = "TRUNCATE "+TABLE_NAME;
    public Role_User() {
    }

    public Role_User(String kode_role, String nama_role, String revisi_code) {
        this.kode_role = kode_role;
        this.nama_role = nama_role;
        this.revisi_code = revisi_code;
    }

    public String getKode_role() {
        return kode_role;
    }

    public void setKode_role(String kode_role) {
        this.kode_role = kode_role;
    }

    public String getNama_role() {
        return nama_role;
    }

    public void setNama_role(String nama_role) {
        this.nama_role = nama_role;
    }

    public String getRevisi_code() {
        return revisi_code;
    }

    public void setRevisi_code(String revisi_code) {
        this.revisi_code = revisi_code;
    }


}
