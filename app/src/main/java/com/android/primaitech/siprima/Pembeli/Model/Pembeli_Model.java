package com.android.primaitech.siprima.Pembeli.Model;

public class Pembeli_Model {
    String kode_pembeli;
    String nama_pembeli;
    String no_ktp;
    int status;
    String no_hp;

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getKode_pembeli() {
        return kode_pembeli;
    }

    public void setKode_pembeli(String kode_pembeli) {
        this.kode_pembeli = kode_pembeli;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
