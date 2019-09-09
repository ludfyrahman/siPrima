package com.primagroup.primaitech.siprima.Akun_Bank.Model;

public class Akun_Bank_Model {
    String kode_akunbank;
    String nama_rekening;
    String nama_unit;
    String nama_bank;
    String no_rekening;
    String tipe_akun;

    public String getTipe_akun() {
        return tipe_akun;
    }

    public void setTipe_akun(String tipe_akun) {
        this.tipe_akun = tipe_akun;
    }

    public String getKode_akunbank() {
        return kode_akunbank;
    }

    public void setKode_akunbank(String kode_akunbank) {
        this.kode_akunbank = kode_akunbank;
    }

    public String getNama_rekening() {
        return nama_rekening;
    }

    public void setNama_rekening(String nama_rekening) {
        this.nama_rekening = nama_rekening;
    }

    public String getNama_unit() {
        return nama_unit;
    }

    public void setNama_unit(String nama_unit) {
        this.nama_unit = nama_unit;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    String saldo;
}
