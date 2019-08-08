package com.android.primaitech.siprima.Database.Model;

public class Master_SQlite {
    public static final String TABLE_NAME = "master";

    public static final String final_key = "key";

    private String key;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + final_key + " TEXT PRIMARY KEY)";
    public Master_SQlite() {
    }

    public Master_SQlite(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
