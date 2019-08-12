package com.android.primaitech.siprima.Penjualan.Temp;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.primaitech.siprima.Config.AuthData;

public class Temp_Penjualan {
    private static Temp_Penjualan mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String kode_pembeli = "kode_pembeli";
    private static final String nama_pembeli = "nama_pembeli";
    private static final String nama_kavling = "nama_kavling";
    private static final String nik = "nik";
    private static final String no_hp = "no_hp";
    private static final String email = "email";
    private static final String metode_bayar = "metode_bayar";
    private static final String alamat_pembeli = "alamat_pembeli";
    private static final String instansi_kerja = "instansi_kerja";
    private static final String alamat_instansi= "alamat_instansi";
    private static final String no_telp_instansi= "no_telp_instansi";
    private static final String kode_proyek= "kode_proyek";
    private static final String kode_kavling= "kode_kavling";
    private static final String kode_karyawan= "kode_karyawan";
    private static final String harga_jual= "harga_jual";
    private static final String diskon= "diskon";
    private static final String harga_bersih= "harga_bersih";
    private static final String uang_booking= "uang_booking";
    private static final String tanggal_bayar_booking= "tanggal_bayar_booking";
    private static final String tanggal_sisa_pembayaran= "tanggal_sisa_pembayaran";
    private static final String lain_lain= "lain_lain";
    private static final String uang_muka= "uang_muka";
    private static final String sisa_dp= "sisa_dp";
    private static final String jumlah_angsuran_dp= "jumlah_angsuran_dp";
    private static final String jumlah_uang_dp_perbulan= "jumlah_uang_dp_perbulan";
    private static final String tanggal_bayar_dp= "tanggal_bayar_dp";
    private static final String sisa_pembayaran_setelah_dp= "sisa_pembayaran_setelah_dp";
    private static final String lama_angsuran_bulanan= "lama_angsuran_bulanan";
    private static final String jumlah_angsuran_perbulan= "jumlah_angsuran_perbulan";
    private static final String jumlah_uang_angsuran_perbulan= "jumlah_uang_angsuran_perbulan";
    private static final String tanggal_bayar_angsuran = "tanggal_bayar_angsuran";

    private static final String stemail = "0";
    private static final String kode_penjualan = "kode_penjualan";


    private Temp_Penjualan(Context context){
        mCtx = context;
    }
    public static synchronized Temp_Penjualan getInstance(Context context){
        if (mInstance == null){
            mInstance = new Temp_Penjualan(context);
        }
        return mInstance;
    }
    public boolean setdatapembeli(String xst, String xkode){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(stemail, xst);
        editor.putString(kode_penjualan, xkode);

        editor.apply();

        return true;
    }
    public boolean clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public boolean setstemail(String xst){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(stemail, xst);
        editor.apply();

        return true;
    }
    public String getNama_kavling() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_kavling, null);
    }

    public boolean setNamaKavling(String nama_kavling) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.nama_kavling, metode_bayar);

        editor.apply();

        return true;
    }
    public String getMetode_bayar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_pembeli, null);
    }

    public boolean setMetode_bayar(String metode_bayar) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.metode_bayar, metode_bayar);

        editor.apply();

        return true;
    }
    public String getKode_pembeli() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_pembeli, null);
    }

    public boolean setKode_pembeli(String kode_pembeli) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.kode_pembeli, kode_pembeli);

        editor.apply();

        return true;
    }

    public String getNama_pembeli() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(nama_pembeli, null);
    }

    public boolean setNama_pembeli(String nama_pembeli) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.nama_pembeli, nama_pembeli);

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

        editor.putString(this.nik, nik);

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

        editor.putString(this.no_hp, no_hp);

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

        editor.putString(this.email, email);

        editor.apply();

        return true;
    }

    public String getAlamat_pembeli() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(alamat_pembeli, null);
    }

    public boolean setAlamat_pembeli(String alamat_pembeli) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.alamat_pembeli, alamat_pembeli);

        editor.apply();

        return true;
    }

    public String getInstansi_kerja() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(instansi_kerja, null);
    }

    public boolean setInstansi_kerja(String instansi_kerja) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.instansi_kerja, instansi_kerja);

        editor.apply();

        return true;
    }

    public String getAlamat_instansi() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(alamat_instansi, null);
    }

    public boolean setAlamat_instansi(String alamat_instansi) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.alamat_instansi, alamat_instansi);

        editor.apply();

        return true;
    }

    public String getNo_telp_instansi() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(no_telp_instansi, null);
    }

    public boolean setNo_telp_instansi(String no_telp_instansi) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.no_telp_instansi, no_telp_instansi);

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

        editor.putString(this.kode_proyek, kode_proyek);

        editor.apply();

        return true;
    }

    public String getKode_kavling() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_kavling, null);
    }

    public boolean setKode_kavling(String kode_kavling) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.kode_kavling, kode_kavling);

        editor.apply();

        return true;
    }

    public String getKode_karyawan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(kode_karyawan, null);
    }

    public boolean setKode_karyawan(String kode_karyawan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.kode_karyawan, kode_karyawan);

        editor.apply();

        return true;
    }

    public String getHarga_jual() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(harga_jual, null);
    }

    public boolean setHarga_jual(String harga_jual) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.harga_jual, harga_jual);

        editor.apply();

        return true;
    }

    public String getDiskon() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(diskon, null);
    }

    public boolean setDiskon(String diskon) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.diskon, diskon);

        editor.apply();

        return true;
    }

    public String getHarga_bersih() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(harga_bersih, null);
    }

    public boolean setHarga_bersih(String harga_bersih) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.harga_bersih, harga_bersih);

        editor.apply();

        return true;
    }

    public String getUang_booking() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(uang_booking, null);
    }

    public boolean setUang_booking(String uang_booking) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.uang_booking, uang_booking);

        editor.apply();

        return true;
    }

    public String getTanggal_bayar_booking() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_bayar_booking, null);
    }

    public boolean setTanggal_bayar_booking(String tanggal_bayar_booking) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.tanggal_bayar_booking, tanggal_bayar_booking);

        editor.apply();

        return true;
    }

    public String getTanggal_sisa_pembayaran() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_sisa_pembayaran, null);
    }

    public boolean setTanggal_sisa_pembayaran(String tanggal_sisa_pembayaran) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.tanggal_sisa_pembayaran, tanggal_sisa_pembayaran);

        editor.apply();

        return true;
    }

    public String getLain_lain() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(lain_lain, null);
    }

    public boolean setLain_lain(String lain_lain) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.lain_lain, lain_lain);

        editor.apply();

        return true;
    }

    public String getUang_muka() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(uang_muka, null);
    }

    public boolean setUang_muka(String uang_muka) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.uang_muka, uang_muka);

        editor.apply();

        return true;
    }

    public String getSisa_dp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(sisa_dp, null);
    }

    public boolean setSisa_dp(String sisa_dp) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.sisa_dp, sisa_dp);

        editor.apply();

        return true;
    }

    public String getJumlah_angsuran_dp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jumlah_angsuran_dp, null);
    }

    public boolean setJumlah_angsuran_dp(String jumlah_angsuran_dp) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.jumlah_angsuran_dp, jumlah_angsuran_dp);

        editor.apply();

        return true;
    }

    public String getJumlah_uang_dp_perbulan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jumlah_uang_dp_perbulan, null);
    }

    public boolean setJumlah_uang_dp_perbulan(String jumlah_uang_dp_perbulan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.jumlah_uang_dp_perbulan, jumlah_uang_dp_perbulan);

        editor.apply();

        return true;
    }

    public String getTanggal_bayar_dp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_bayar_dp, null);
    }

    public boolean setTanggal_bayar_dp(String tanggal_bayar_dp) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.tanggal_bayar_dp, tanggal_bayar_dp);

        editor.apply();

        return true;
    }

    public String getSisa_pembayaran_setelah_dp() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(sisa_pembayaran_setelah_dp, null);
    }

    public boolean setSisa_pembayaran_setelah_dp(String sisa_pembayaran_setelah_dp) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.sisa_pembayaran_setelah_dp, sisa_pembayaran_setelah_dp);

        editor.apply();

        return true;
    }

    public String getLama_angsuran_bulanan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(lama_angsuran_bulanan, null);
    }

    public boolean setLama_angsuran_bulanan(String lama_angsuran_bulanan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.lama_angsuran_bulanan, lama_angsuran_bulanan);

        editor.apply();

        return true;
    }

    public String getJumlah_angsuran_perbulan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jumlah_angsuran_perbulan, null);
    }

    public boolean setJumlah_angsuran_perbulan(String jumlah_angsuran_perbulan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.jumlah_angsuran_perbulan, jumlah_angsuran_perbulan);

        editor.apply();

        return true;
    }

    public String getJumlah_uang_angsuran_perbulan() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(jumlah_uang_angsuran_perbulan, null);
    }

    public boolean setJumlah_uang_angsuran_perbulan(String jumlah_uang_angsuran_perbulan) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.jumlah_uang_angsuran_perbulan, jumlah_uang_angsuran_perbulan);

        editor.apply();

        return true;
    }

    public String getTanggal_bayar_angsuran() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(tanggal_bayar_angsuran, null);
    }

    public boolean setTanggal_bayar_angsuran(String tanggal_bayar_angsuran) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.tanggal_bayar_angsuran, tanggal_bayar_angsuran);

        editor.apply();

        return true;
    }

}
