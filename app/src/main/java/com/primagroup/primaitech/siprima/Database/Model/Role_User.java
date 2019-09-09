package com.primagroup.primaitech.siprima.Database.Model;

public class Role_User {
    public static final String TABLE_NAME = "role_user";

    public static final String final_kode_role = "kode_role";
    public static final String final_nama_role = "nama_role";
    public static final String final_revisi_code = "revisi_code";
    public static final String final_kode_menu = "kode_menu";
    public static final String final_nama_menu = "nama_menu";
    public static final String final_tipe = "tipe";
    public static final String final_kode_parent = "kode_parent";
    public static final String final_akses = "akses";
    public static final String final_buat = "buat";
    public static final String final_edit = "edit";
    public static final String final_hapus = "hapus";
    public static final String final_akses_view = "akses_view";
    public static final String final_show_menu = "show_menu";
    public static final String final_sub_show = "sub_show";
    public static final String final_urutan = "urutan";
    public static final String final_bisa_hapus = "bisa_hapus";

    private String kode_role;
    private String nama_role;
    private String revisi_code;
    private String kode_menu;
    private String nama_menu;
    private String tipe;
    private String kode_parent;
    private String akses;
    private String buat;
    private String edit;
    private String hapus;
    private String akses_view;
    private String show_menu;
    private String sub_show;
    private String urutan;
    private String bisa_hapus;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + final_kode_role + " TEXT PRIMARY KEY ,"
                    + final_nama_role + " TEXT,"
                    + final_revisi_code + " TEXT,"
                    + final_kode_menu + " TEXT,"
                    + final_nama_menu + " TEXT,"
                    + final_tipe + " TEXT,"
                    + final_kode_parent + " TEXT,"
                    + final_akses + " TEXT,"
                    + final_buat + " TEXT,"
                    + final_edit + " TEXT,"
                    + final_hapus + " TEXT,"
                    + final_akses_view + " TEXT,"
                    + final_show_menu + " TEXT,"
                    + final_sub_show + " TEXT,"
                    + final_urutan + " TEXT,"
                    + final_bisa_hapus + " TEXT"
                    + ")";
    public static final String TRUNCATE = "TRUNCATE "+TABLE_NAME;
    public Role_User() {
    }

    public Role_User(String kode_role, String nama_role, String revisi_code, String kode_menu, String nama_menu, String tipe, String kode_parent, String akses, String buat, String edit, String hapus, String akses_view, String show_menu, String sub_show, String urutan, String bisa_hapus) {
        this.kode_role = kode_role;
        this.nama_role = nama_role;
        this.revisi_code = revisi_code;
        this.kode_menu = kode_menu;
        this.nama_menu = nama_menu;
        this.tipe = tipe;
        this.kode_parent = kode_parent;
        this.akses = akses;
        this.buat = buat;
        this.edit = edit;
        this.hapus = hapus;
        this.akses_view = akses_view;
        this.show_menu = show_menu;
        this.sub_show = sub_show;
        this.urutan = urutan;
        this.bisa_hapus = bisa_hapus;
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

    public  String getFinal_kode_menu() {
        return final_kode_menu;
    }

    public void setFinal_kode_menu(String kode) {
        this.kode_menu = kode;
    }

    public  String getFinal_nama_menu() {
        return final_nama_menu;
    }

    public void setNama_menu(String kode) {
        this.nama_menu = kode;
    }

    public  String getFinal_tipe() {
        return final_tipe;
    }

    public void setTipe(String kode) {
        this.tipe = kode;
    }

    public  String getFinal_kode_parent() {
        return final_kode_parent;
    }

    public void setKode_parent(String kode) {
        this.kode_parent = kode;
    }

    public  String getFinal_akses() {
        return final_akses;
    }

    public void setAkses(String kode) {
        this.akses = kode;
    }

    public  String getFinal_buat() {
        return final_buat;
    }

    public void setBuat(String kode) {
        this.buat = kode;
    }

    public  String getFinal_edit() {
        return final_edit;
    }

    public void setEdit(String kode) {
        this.edit = kode;
    }

    public  String getFinal_hapus() {
        return final_hapus;
    }

    public void setHapus(String kode) {
        this.hapus = kode;
    }

    public static String getFinal_akses_view() {
        return final_akses_view;
    }

    public void setAkses_view(String kode) {
        this.akses_view = kode;
    }

    public static String getFinal_show_menu() {
        return final_show_menu;
    }

    public void setShow_menu(String kode) {
        this.show_menu = kode;
    }

    public  String getFinal_sub_show() {
        return final_sub_show;
    }

    public void setSub_show(String kode) {
        this.sub_show = kode;
    }

    public  String getFinal_urutan() {
        return final_urutan;
    }

    public void setUrutan(String kode) {
        this.urutan = kode;
    }

    public  String getFinal_bisa_hapus() {
        return final_bisa_hapus;
    }

    public void setBisa_hapus(String kode) {
        this.bisa_hapus = kode;
    }
}
