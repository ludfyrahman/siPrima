package com.primagroup.primaitech.siprima.Database.Model;

public class Master_SQLite {
    public static final String TABLE_NAME = "master";

    public static final String final_key = "key";
    public static final String final_table = "tabel";

    private String key;
    private String table;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + final_key + " TEXT PRIMARY KEY, " +
                    final_table + " TEXT )";
    public Master_SQLite() {
    }

    public Master_SQLite(String key, String table) {
        this.key = key;
        this.table = table;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
