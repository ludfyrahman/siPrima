package com.primagroup.primaitech.siprima.Penjualan.Model;

public class Penjualan_Model {
    String kode;
    String nama_penjualan;
    String nama_pembeli;
    String nama_penjual;
    String tanggal_penjualan;
    String harga_jual_bersih;
    String cover;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTanggal_penjualan() {
        return tanggal_penjualan;
    }

    public void setTanggal_penjualan(String tanggal_penjualan) {
        this.tanggal_penjualan = tanggal_penjualan;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getHarga_jual_bersih() {
        return harga_jual_bersih;
    }

    public void setHarga_jual_bersih(String harga_jual_bersih) {
        this.harga_jual_bersih = harga_jual_bersih;
    }


    public String getNama_penjualan() {
        return nama_penjualan;
    }

    public void setNama_penjualan(String nama_penjualan) {
        this.nama_penjualan = nama_penjualan;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getNama_penjual() {
        return nama_penjual;
    }

    public void setNama_penjual(String nama_penjual) {
        this.nama_penjual = nama_penjual;
    }

}
