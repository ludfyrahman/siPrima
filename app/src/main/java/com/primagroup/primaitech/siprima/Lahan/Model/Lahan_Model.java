package com.primagroup.primaitech.siprima.Lahan.Model;

public class Lahan_Model {
    String nama_lahan, kode_lahan, nama_proyek,harga_lahan, tgl_akhir_bayar, status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNama_lahan() {
        return nama_lahan;
    }

    public void setNama_lahan(String nama_lahan) {
        this.nama_lahan = nama_lahan;
    }

    public String getKode_lahan() {
        return kode_lahan;
    }

    public void setKode_lahan(String kode_lahan) {
        this.kode_lahan = kode_lahan;
    }

    public String getNama_proyek() {
        return nama_proyek;
    }

    public void setNama_proyek(String nama_proyek) {
        this.nama_proyek = nama_proyek;
    }

    public String getHarga_lahan() {
        return harga_lahan;
    }

    public void setHarga_lahan(String harga_lahan) {
        this.harga_lahan = harga_lahan;
    }

    public String getTgl_akhir_bayar() {
        return tgl_akhir_bayar;
    }

    public void setTgl_akhir_bayar(String tgl_akhir_bayar) {
        this.tgl_akhir_bayar = tgl_akhir_bayar;
    }
}
