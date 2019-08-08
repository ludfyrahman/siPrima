package com.android.primaitech.siprima.Database.Model;

public class Menu_Table {
    public static final String TABLE_NAME = "menu";

    public static final String final_kode_menu = "kode_menu";
    public static final String final_gambar = "gambar";
    public static final String final_link = "link";
    public static final String final_nama_menu = "nama_menu";

    private String kode_menu;
    private int gambar;
    private String link;
    private String judul;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + final_kode_menu + " TEXT PRIMARY KEY ,"
                    + final_nama_menu + " TEXT,"
                    + final_link + " TEXT, "
                    + final_gambar + " INTEGER"
                    + ")";
    public Menu_Table() {
    }

    public Menu_Table(String kode_menu, String link, String judul, Integer gambar) {
        this.kode_menu = kode_menu;
        this.link = link;
        this.judul = judul;
        this.gambar = gambar;
    }

    public String getKode_menu() {
        return kode_menu;
    }

    public void setKode_menu(String kode_menu) {
        this.kode_menu = kode_menu;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
