package com.primagroup.primaitech.siprima.Config;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerAccess {
    public static final String BASE_URL = "http://192.168.1.7/siprima/";
//    public static final String BASE_URL = "http://192.168.43.234/siprima/";
//    public static final String BASE_URL = "http://10.10.3.168/siprima/";
//    public static final String BASE_URL = "http://siprima.primaitech.com/";
    public static final String Menu = BASE_URL+"api/api";
    public static final String ROOT_API = BASE_URL+"api/";
    public static final String SubMenu = BASE_URL+"api/api/submenu";
    public static final String result = ROOT_API+"api/getdataresult/";
    public static final String delete = ROOT_API+"api/delete/";
    public static final String asset = BASE_URL+"assets/";
    public static final String upload = asset+"upload/";
    public static final String URL_CEK_TOKEN=Menu+"/cektoken";
    public static final String URL_LOGIN=ROOT_API+"auth/authlogin";
    public static final String URL_PEMBELI=ROOT_API+"pembeli/";
    public static final String URL_KAVLING=ROOT_API+"kavling/";
    public static final String URL_TIPE_RUMAH=ROOT_API+"tiperumah/";
    public static final String URL_DIVISI=ROOT_API+"divisi/";
    public static final String URL_KARYAWAN=ROOT_API+"karyawan/";
    public static final String URL_RAB=ROOT_API+"rab/";
    public static final String URL_HPP=ROOT_API+"hpp/";
    public static final String URL_LAHAN=ROOT_API+"lahan/";
    public static final String URL_UNIT_BISNIS=ROOT_API+"unitbisnis/";
    public static final String URL_PROYEK=ROOT_API+"proyek/";
    public static final String URL_AKUN_BANK=ROOT_API+"akunbank/";
    public static final String URL_KATEGORI_KAVLING=ROOT_API+"kategorikavling/";
    public static final String URL_PENJUALAN=ROOT_API+"penjualan/";
    public static final String URL_KUNJUNGAN=ROOT_API+"kunjungan/";
    public static final String URL_KEGIATAN=ROOT_API+"kegiatan/";
    public static final String URL_CUTI=ROOT_API+"cuti/";
    public static final String URL_LOGOUT=ROOT_API+"Api/authlogout";
    public static final String URL_NOTIF=ROOT_API+"Apikomplain/kirimnotif";
    public static final String URL_LAPORAN=ROOT_API+"Apikomplain/getlaporan/";
    public static final String UPDATE_TOKEN=BASE_URL+"api/auth/authsettoken";
    public static String bannerProyek = "bannerproyek", bannerUnitBisnis = "unitbisnis", kavling = "kavling";
    public static String status_kavling[] = {"dihapus", "Tersedia", "Dipesan", "Terjual"};
    public static String statusFollowUp[] = {"Belum Dikunjungi", "Selesai"};
    public static String prospek[] = {"-", "Rendah", "Sedang", "Tinggi"};
    public static String bulan[] = {"", "Januari", "Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember" };
    public static String statusPenjualan[] = {"Batal","Proses Angsuran","Menunggu Persetujuan","Selesai","Di Tolak"};
    public static String jenis_pembayaran[] = {"","Uang Booking","Bayar DP","Angsuran Bulanan","Pembayaran Cash"};
    public static String status_pembayaran[] = {"Belum Lunas","Lunas", "Menunggu Konfirmasi"};
    public static String metode[] = {"","Cash","Transfer Bank"};
    public static String statusLahan[] = {"","Tersedia","Terpakai", "Menunggu Konfirmasi"};
    public static String menu_key = "$2y$10xZ42EBgUzmd12bS/ZQIh5D4dDNd/3IESRCEi9xpzgMwOzrFzexcS";
    public static String tipe_karyawan[] = {"-","Karyawan Unit Bisnis", "Karyawan Proyek"};
    public static String status_karyawan[] = {"-","Aktif", "Menunggu Konfirmasi", "Ditolak"};
    public static String gender[] = {"-","Laki Laki", "Perempuan"};
    public static String status_pernikahan[] = {"Belum Menikah","Sudah Menikah"};
    public static String tipe_unit[] = {"","Developer", "Jasa", "Penjualan"};
    public static String status[] = {"Tidak Aktif","Aktif"};

    public static String numberFormat(int val){
        NumberFormat nf =NumberFormat.getInstance();
        return nf.format(val);
    }
    public static String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String numberConvert(String val){
        double v = Double.parseDouble(val);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String format = formatter.format(v);
        return "Rp "+format;
    }
    public static String dateFormat(String date){
//        String date ="29/07/13";
        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        String str = null;
        try {
            Date oneWayTripDate = input.parse(date);                 // parse input
            str = output.format(oneWayTripDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static String Inddate(String date){
//        String date ="29/07/13";
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
        String str = null;
        try {
            Date oneWayTripDate = input.parse(date);                 // parse input
            str = output.format(oneWayTripDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
