package com.android.primaitech.siprima.Pembeli.Model;

public class Pembeli_Model {
    String kode_pembeli;
    String nama_pembeli;
    String no_ktp;
    int status;
    String no_hp;
    String instansi_kerja, alamat_instansi, no_instansi, alamat_pembeli, email;

    public String getInstansi_kerja() {
        return instansi_kerja;
    }

    public void setInstansi_kerja(String instansi_kerja) {
        this.instansi_kerja = instansi_kerja;
    }

    public String getAlamat_instansi() {
        return alamat_instansi;
    }

    public void setAlamat_instansi(String alamat_instansi) {
        this.alamat_instansi = alamat_instansi;
    }

    public String getNo_instansi() {
        return no_instansi;
    }

    public void setNo_instansi(String no_instansi) {
        this.no_instansi = no_instansi;
    }

    public String getAlamat_pembeli() {
        return alamat_pembeli;
    }

    public void setAlamat_pembeli(String alamat_pembeli) {
        this.alamat_pembeli = alamat_pembeli;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
