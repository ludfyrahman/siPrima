package com.android.primaitech.siprima.Karyawan.Temp;

import android.content.Context;
import android.content.SharedPreferences;


public class Temp_Karyawan {
    private static Temp_Karyawan mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "prefkaryawan";
    private static final String nama_menu = "nama_menu";
    private static final String tipe = "tipe";
    private static final String kode_karyawan = "kode_karyawan";
    private static final String nama_karyawan = "nama_karyawan";
    private static final String nik = "nik";
    private static final String jenis_kelamin = "jenis_kelamin";
    private static final String jatah_cuti = "jatah_cuti";
    private static final String email = "email";
    private static final String no_hp = "no_hp";
    private static final String no_hp_darurat = "no_hp_darurat";
    private static final String status_pernikahan = "status_pernikahan";
    private static final String nama_pasangan = "nama_pasangan";
    private static final String jumlah_tanggungan = "jumlah_tanggungan";
    private static final String tempat_lahir = "tempat_lahir";
    private static final String tanggal_lahir = "tanggal_lahir";
    private static final String tanggal_bergabung = "tanggal_bergabung";
    private static final String foto_karyawan = "foto_karyawan";
    private static final String alamat = "alamat";
    private static final String unit_bisnis = "unit_bisnis";
    private static final String kode_unit_bisnis = "kode_unit_bisnis";
    private static final String proyek = "proyek";
    private static final String kode_proyek = "kode_proyek";
    private static final String divisi = "divisi";
    private static final String kode_divisi = "kode_divisi";
    private static final String jabatan = "jabatan";
    private static final String gaji_pokok = "gaji_pokok";
    private static final String tunjangan = "tunjangan";
    private static final String lainnya = "lainnya";
    private static final String potongan = "potongan";
    private static final String rekening = "rekening";
    private static final String no_rekening = "no_rekening";
    private static final String nama_bank = "nama_bank";

    private Temp_Karyawan(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Karyawan getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Karyawan(context);
        }
        return mInstance;
    }
    public boolean isExist() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(nama_menu, null) != null) {
            return true;
        }
        return false;
    }
    public boolean setNamaMenu(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_menu, xst);
        editor.apply();

        return true;
    }
    public String getNama_menu() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_menu, null);
    }
    public boolean setTipe(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tipe, xst);
        editor.apply();

        return true;
    }
    public String getTipe() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tipe, null);
    }

    public String getKode_karyawan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_karyawan, null);
    }

    public boolean setKode_karyawan(String kode_karyawan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_karyawan, kode_karyawan);
        editor.apply();

        return true;
    }

    public String getNama_karyawan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_karyawan, null);
    }

    public boolean setNama_karyawan(String nama_karyawan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_karyawan, nama_karyawan);
        editor.apply();

        return true;
    }

    public String getNik() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nik, null);
    }

    public boolean setNik(String nik) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nik, nik);
        editor.apply();

        return true;
    }

    public String getJenis_kelamin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jenis_kelamin, null);
    }

    public boolean setJenis_kelamin(String jenis_kelamin) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(jenis_kelamin, jenis_kelamin);
        editor.apply();

        return true;
    }

    public String getJatah_cuti() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jatah_cuti, null);
    }

    public boolean setJatah_cuti(String jatah_cuti) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(jatah_cuti, jatah_cuti);
        editor.apply();

        return true;
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(email, null);
    }

    public boolean setEmail(String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(email, email);
        editor.apply();

        return true;
    }

    public String getNo_hp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_hp, null);
    }

    public boolean setNo_hp(String no_hp) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_hp, no_hp);
        editor.apply();

        return true;
    }

    public String getNo_hp_darurat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_hp_darurat, null);
    }

    public boolean setNo_hp_darurat(String no_hp_darurat) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_hp_darurat, no_hp_darurat);
        editor.apply();

        return true;
    }

    public String getStatus_pernikahan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(status_pernikahan, null);
    }

    public boolean setStatus_pernikahan(String status_pernikahan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(status_pernikahan, status_pernikahan);
        editor.apply();

        return true;
    }

    public String getNama_pasangan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_pasangan, null);
    }

    public boolean setNama_pasangan(String nama_pasangan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_pasangan, nama_pasangan);
        editor.apply();

        return true;
    }

    public String getJumlah_tanggungan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jumlah_tanggungan, null);
    }

    public boolean setJumlah_tanggungan(String jumlah_tanggungan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(jumlah_tanggungan, jumlah_tanggungan);
        editor.apply();

        return true;
    }

    public String getTempat_lahir() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tempat_lahir, null);
    }

    public boolean setTempat_lahir(String tempat_lahir) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tempat_lahir, tempat_lahir);
        editor.apply();

        return true;
    }

    public String getTanggal_lahir() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_lahir, null);
    }

    public boolean setTanggal_lahir(String tanggal_lahir) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tanggal_lahir, tanggal_lahir);
        editor.apply();

        return true;
    }

    public String getTanggal_bergabung() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_bergabung, null);
    }

    public boolean setTanggal_bergabung(String tanggal_bergabung) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tanggal_bergabung, tanggal_bergabung);
        editor.apply();

        return true;
    }

    public String getFoto_karyawan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(foto_karyawan, null);
    }

    public boolean setFoto_karyawan(String foto_karyawan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(foto_karyawan, foto_karyawan);
        editor.apply();

        return true;
    }

    public String getAlamat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(alamat, null);
    }

    public boolean setAlamat(String alamat) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(alamat, alamat);
        editor.apply();

        return true;
    }

    public String getUnit_bisnis() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(unit_bisnis, null);
    }

    public boolean setUnit_bisnis(String unit_bisnis) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(unit_bisnis, unit_bisnis);
        editor.apply();

        return true;
    }
    public String getKode_unit_bisnis() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_unit_bisnis, null);
    }

    public boolean setKode_unit_bisnis(String kode_unit_bisnis) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_unit_bisnis, kode_unit_bisnis);
        editor.apply();

        return true;
    }
    public String getProyek() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(proyek, null);
    }

    public boolean setProyek(String proyek) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(proyek, proyek);
        editor.apply();

        return true;
    }
    public String getKode_proyek() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_proyek, null);
    }

    public boolean setKode_proyek(String kode_proyek) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_proyek, kode_proyek);
        editor.apply();

        return true;
    }

    public String getDivisi() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(divisi, null);
    }

    public boolean setDivisi(String divisi) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(divisi, divisi);
        editor.apply();

        return true;
    }
    public String getKode_divisi() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_divisi, null);
    }

    public boolean setKode_divisi(String kode_divisi) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(kode_divisi, kode_divisi);
        editor.apply();

        return true;
    }

    public String getJabatan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jabatan, null);
    }

    public boolean setJabatan(String jabatan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(jabatan, jabatan);
        editor.apply();

        return true;
    }

    public String getGaji_pokok() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(gaji_pokok, null);
    }

    public boolean setGaji_pokok(String gaji_pokok) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(gaji_pokok, gaji_pokok);
        editor.apply();

        return true;
    }

    public String getTunjangan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tunjangan, null);
    }

    public boolean setTunjangan(String tunjangan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(tunjangan, tunjangan);
        editor.apply();

        return true;
    }

    public String getLainnya() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(lainnya, null);
    }

    public boolean setLainnya(String lainnya) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(lainnya, lainnya);
        editor.apply();

        return true;
    }

    public String getPotongan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(potongan, null);
    }

    public boolean setPotongan(String potongan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(potongan, potongan);
        editor.apply();

        return true;
    }

    public String getRekening() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(rekening, null);
    }

    public boolean setRekening(String rekening) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(rekening, rekening);
        editor.apply();

        return true;
    }

    public String getNo_rekening() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_rekening, null);
    }

    public boolean setNo_rekening(String no_rekening) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(no_rekening, no_rekening);
        editor.apply();

        return true;
    }

    public String getNama_bank() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_bank, null);
    }

    public boolean setNama_bank(String nama_bank) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nama_bank, nama_bank);
        editor.apply();

        return true;
    }
}