package com.android.primaitech.siprima.Config;

import android.support.v4.app.Fragment;

import com.android.primaitech.siprima.Akun_Bank.Akun_bank;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Proyek;
import com.android.primaitech.siprima.Akun_Bank.Fragment_Ab_Unit_Bisnis;
import com.android.primaitech.siprima.Artikel.Artikel;
import com.android.primaitech.siprima.Divisi.Divisi;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Proyek;
import com.android.primaitech.siprima.Karyawan.Fragment_K_Unit_Bisnis;
import com.android.primaitech.siprima.Karyawan.Karyawan;
import com.android.primaitech.siprima.Kategori_kavling.Kategori_kavling;
import com.android.primaitech.siprima.Kavling.Kavling;
import com.android.primaitech.siprima.Promo.Promo;
import com.android.primaitech.siprima.Proyek.Proyek;
import com.android.primaitech.siprima.RAB.Fragment_Rab_Proyek;
import com.android.primaitech.siprima.RAB.Fragment_Rab_Unit_Bisnis;
import com.android.primaitech.siprima.RAB.RAB;

import java.util.Arrays;

public class MenuData {
    String menu[] = {
                        "menu6", "menu1", "menu9", "menu2", "menu14", "menu15", "menu3", "menu11", "menu10", "menu4",
                        "menu17", "menu25", "menu19", "menu26", "menu13", "menu24", "menu23","menu31","menu18", "menu33",
                        "menu32", "menu27", "menu20", "menu16", "menu12", "menu29", "menu28", "menu22", "menu8", "menu38",
                        "menu5", "menu7"
                    };
    Class halaman[] = {
            Proyek.class, Proyek.class, Kategori_kavling.class, Proyek.class, Promo.class, Akun_bank.class, Proyek.class, Karyawan.class, Proyek.class, RAB.class,
            Proyek.class, Proyek.class, Proyek.class, Proyek.class, Artikel.class, Akun_bank.class, Proyek.class, Proyek.class, Proyek.class, Proyek.class,
            Proyek.class, Proyek.class, Proyek.class, Proyek.class, Divisi.class, Akun_bank.class, Proyek.class, Proyek.class, Kavling.class, Proyek.class,
            Proyek.class, Proyek.class
            };
    String kode_submenu[] = {"submenu31", "submenu32", "submenu29", "submenu30", "submenu1", "submenu2"};
    Fragment fragment[] = {new Fragment_Ab_Unit_Bisnis(), new Fragment_Ab_Proyek(), new Fragment_K_Unit_Bisnis(), new Fragment_K_Proyek(), new Fragment_Rab_Unit_Bisnis(), new Fragment_Rab_Proyek()};

    public Class halaman(String code_menu) {
        Class ret = halaman[Arrays.asList(menu).indexOf(code_menu)];
        return ret;
    }
    public Fragment frag(String code_menu){
        Fragment ret = fragment[Arrays.asList(kode_submenu).indexOf(code_menu)];
        return ret;
    }

}
