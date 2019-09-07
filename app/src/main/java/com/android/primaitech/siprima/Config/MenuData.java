package com.android.primaitech.siprima.Config;

import android.support.v4.app.Fragment;

import com.android.primaitech.siprima.Akun_Bank.Akun_bank;
import com.android.primaitech.siprima.Akun_Bank.Detail_Akun_Bank;
import com.android.primaitech.siprima.Akun_Bank.Form_Akun_Bank;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Proyek;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Unit_Bisnis;
import com.android.primaitech.siprima.Artikel.Artikel;
import com.android.primaitech.siprima.Artikel.Detail_Artikel;
import com.android.primaitech.siprima.Cuti.Cuti;
import com.android.primaitech.siprima.Cuti.Detail_Cuti;
import com.android.primaitech.siprima.Divisi.Detail_Divisi;
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.Divisi.Form_Divisi;
import com.android.primaitech.siprima.Karyawan.Detail_Karyawan;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Proyek;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Unit_Bisnis;
import com.android.primaitech.siprima.Karyawan.Karyawan;
import com.android.primaitech.siprima.Kategori_kavling.Detail_Kategori_Kavling;
import com.android.primaitech.siprima.Kategori_kavling.Form_Kategori_Kavling;
import com.android.primaitech.siprima.Kategori_kavling.Kategori_kavling;
import com.android.primaitech.siprima.Kavling.Detail_Kavling;
import com.android.primaitech.siprima.Kavling.Fragment_Data_Legalitas_Kavling;
import com.android.primaitech.siprima.Kavling.Fragment_Info_Progress;
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Kegiatan.Detail_Kegiatan;
import com.android.primaitech.siprima.Kegiatan.Kegiatan;
import com.android.primaitech.siprima.Kehadiran.Kehadiran;
import com.android.primaitech.siprima.Lahan.Fragment_Galeri_Lahan;
import com.android.primaitech.siprima.Lahan.Fragment_Hpp_Lahan;
import com.android.primaitech.siprima.Lahan.Fragment_Legalitas_Lahan;
import com.android.primaitech.siprima.Lahan.Fragment_Pembayaran_Lahan;
import com.android.primaitech.siprima.Lahan.Lahan;
import com.android.primaitech.siprima.Pembeli.Detail_Pembeli;
import com.android.primaitech.siprima.Pembeli.Fragment_Calon_Pembeli;
import com.android.primaitech.siprima.Pembeli.Fragment_Follow_Up;
import com.android.primaitech.siprima.Pembeli.Fragment_Pembeli;
import com.android.primaitech.siprima.Pembeli.Pembeli;
import com.android.primaitech.siprima.Penjualan.Detail_Penjualan;
import com.android.primaitech.siprima.Penjualan.Penjualan;
import com.android.primaitech.siprima.Penjualan.Tambah_Penjualan;
import com.android.primaitech.siprima.Promo.Detail_Promo;
import com.android.primaitech.siprima.Promo.Promo;
import com.android.primaitech.siprima.Proyek.Detail_Proyek;
import com.android.primaitech.siprima.Proyek.Fragment_Data_Legalitas;
import com.android.primaitech.siprima.Proyek.Proyek;
import com.android.primaitech.siprima.RAB.Detail_RAB;
import com.android.primaitech.siprima.RAB.Fragment_Rab_Proyek;
import com.android.primaitech.siprima.RAB.Fragment_Rab_Unit_Bisnis;
import com.android.primaitech.siprima.RAB.RAB;
import com.android.primaitech.siprima.Unit_Bisnis.Unit_Bisnis;

import java.util.Arrays;

public class MenuData {
    String menu[] = {
                        "menu6", "menu1", "menu9", "menu2", "menu14", "menu15", "menu3", "menu11", "menu10", "menu4",
                        "menu17", "menu25", "menu19", "menu26", "menu13", "menu24", "menu23","menu31","menu18", "menu33",
                        "menu32", "menu27", "menu20", "menu16", "menu12", "menu29", "menu28", "menu22", "menu8", "menu38",
                        "menu5", "menu7", "menu42", "submenu3", "submenu4", "submenu24", "submenu25","submenu31", "submenu32",
                        "submenu1", "submenu2","submenu29", "submenu30",
                    };
    Class halaman[] = {
            Proyek.class, Unit_Bisnis.class, Kategori_kavling.class, Proyek.class, Promo.class, Akun_bank.class, Proyek.class, Karyawan.class, Pembeli.class, RAB.class,
            Proyek.class, Proyek.class, Proyek.class, Proyek.class, Artikel.class, Akun_bank.class, Proyek.class, Proyek.class, Proyek.class, Proyek.class,
            Proyek.class, Proyek.class, Proyek.class, Proyek.class, Divisi.class, Kegiatan.class, Cuti.class, Proyek.class, Kavling.class, Proyek.class,
            Penjualan.class, Lahan.class, Kehadiran.class, Penjualan.class, Tambah_Penjualan.class, Fragment_Pembeli.class, Fragment_Calon_Pembeli.class, Fragment_Ab_Unit_Bisnis.class, Fragment_Ab_Proyek.class,
            Fragment_Rab_Unit_Bisnis.class, Fragment_Rab_Proyek.class, Fragment_K_Unit_Bisnis.class, Fragment_K_Proyek.class
            };
    Class detail_halaman[] = {
            Detail_Proyek.class, Detail_Proyek.class, Detail_Kategori_Kavling.class, Detail_Proyek.class, Detail_Promo.class, Detail_Akun_Bank.class, Detail_Proyek.class, Detail_Karyawan.class, Detail_Pembeli.class, Detail_RAB.class,
            Detail_Proyek.class, Detail_Proyek.class, Detail_Proyek.class, Detail_Proyek.class, Detail_Artikel.class, Detail_Akun_Bank.class, Detail_Proyek.class, Detail_Proyek.class, Detail_Proyek.class, Detail_Proyek.class,
            Detail_Proyek.class, Detail_Proyek.class, Detail_Proyek.class, Detail_Proyek.class, Detail_Divisi.class, Detail_Kegiatan.class, Detail_Cuti.class, Detail_Proyek.class, Detail_Kavling.class, Detail_Proyek.class,
            Detail_Penjualan.class, Detail_Proyek.class, Kehadiran.class
    };
    String kode_submenu[] = {"submenu26", "submenu22", "submenu23", "menu43", "submenu19", "submenu20", "submenu52"};
    Fragment fragment[] = { new Fragment_Follow_Up(), new Fragment_Info_Progress(), new Fragment_Data_Legalitas_Kavling(), new Fragment_Hpp_Lahan(), new Fragment_Pembayaran_Lahan(), new Fragment_Legalitas_Lahan(), new Fragment_Galeri_Lahan()};
    String kode_navigasi[] = {"akunbank", "divisi", "karyawan", "kategorikavling", "kavling", "pembeli", "promo"};
    Class navigasi[] = {
            Form_Akun_Bank.class, Form_Divisi.class, Form_Kategori_Kavling.class, Form_Kategori_Kavling.class
    };
    public static String nama_menu = "", kode_menu = "";
    public Class halaman_navigasi(String code_menu) {
        Class ret = navigasi[Arrays.asList(kode_navigasi).indexOf(code_menu)];
        return ret;
    }
    public Class halaman(String code_menu) {
        Class ret = halaman[Arrays.asList(menu).indexOf(code_menu)];
        return ret;
    }
    public Class detail_halaman(String code_menu) {
        Class ret = detail_halaman[Arrays.asList(menu).indexOf(code_menu)];
        return ret;
    }
    public Fragment frag(String code_menu){
        Fragment ret = fragment[Arrays.asList(kode_submenu).indexOf(code_menu)];
        return ret;
    }

}
