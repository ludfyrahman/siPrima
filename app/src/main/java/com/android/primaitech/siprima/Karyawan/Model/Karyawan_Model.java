package com.android.primaitech.siprima.Karyawan.Model;

public class Karyawan_Model {
    String kode_karyawan;
    String nama_unit;
    String nama_proyek;
    String nama_karyawan;
    String nama_divisi;
    String tanggal_gabung;
    String tipe_karyawan;

    public String getTipe_karyawan() {
        return tipe_karyawan;
    }

    public void setTipe_karyawan(String tipe_karyawan) {
        this.tipe_karyawan = tipe_karyawan;
    }

    public String getKode_karyawan() {
        return kode_karyawan;
    }

    public void setKode_karyawan(String kode_karyawan) {
        this.kode_karyawan = kode_karyawan;
    }

    public String getNama_unit() {
        return nama_unit;
    }

    public void setNama_unit(String nama_unit) {
        this.nama_unit = nama_unit;
    }

    public String getNama_proyek() {
        return nama_proyek;
    }

    public void setNama_proyek(String nama_proyek) {
        this.nama_proyek = nama_proyek;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan = nama_karyawan;
    }

    public String getNama_divisi() {
        return nama_divisi;
    }

    public void setNama_divisi(String nama_divisi) {
        this.nama_divisi = nama_divisi;
    }

    public String getTanggal_gabung() {
        return tanggal_gabung;
    }

    public void setTanggal_gabung(String tanggal_gabung) {
        this.tanggal_gabung = tanggal_gabung;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    String foto;
}
