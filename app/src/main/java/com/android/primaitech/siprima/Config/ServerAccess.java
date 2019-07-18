package com.android.primaitech.siprima.Config;

import java.text.NumberFormat;
import java.util.Locale;

public class ServerAccess {
    public static final String BASE_URL = "http://192.168.1.28/siprima/";
//    public static final String BASE_URL = "http://192.168.43.234/siprima/";
//    public static final String BASE_URL = "https://primagroup.primaitech.com/";
    public static final String Menu = BASE_URL+"api/api";
    public static final String result = BASE_URL+"api/api/getdataresult/";
    public static final String delete = BASE_URL+"api/api/delete/";
    public static final String asset = BASE_URL+"assets/";
    public static final String upload = asset+"upload/";
    public static final String URL_CEK_TOKEN=Menu+"/cektoken";
    public static final String URL_LOGIN=BASE_URL+"/api/auth/authlogin";
    public static final String URL_PEMBELI=BASE_URL+"/api/pembeli/";
    public static final String URL_LOGOUT=BASE_URL+"Api/authlogout";
    public static final String URL_NOTIF=BASE_URL+"Apikomplain/kirimnotif";
    public static final String URL_LAPORAN=BASE_URL+"Apikomplain/getlaporan/";
    public static final String UPDATE_TOKEN=BASE_URL+"api/auth/authsettoken";
    public static String bannerProyek = "bannerproyek", bannerUnitBisnis = "unitbisnis", kavling = "kavling";
    public static String numberFormat(int val){
        NumberFormat nf =NumberFormat.getInstance();
        return nf.format(val);
    }
}
