package com.primagroup.primaitech.siprima.Proyek;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.primaitech.siprima.R;
import com.primagroup.primaitech.siprima.Config.ServerAccess;

public class Fragment_Siteplan extends Fragment {
    WebView main_website;
    WebSettings webSettings;
    public String TAG = "pesan";
    Detail_Proyek detail_proyek = new Detail_Proyek();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_siteplan, container, false);
        main_website = (WebView)v.findViewById(R.id.main_website);
        webSettings = main_website.getSettings();
        webSettings.setJavaScriptEnabled(true);
        main_website.getSettings().setDefaultTextEncodingName("UTF-8");
        main_website.getSettings().setBlockNetworkImage(false);
        main_website.getSettings().setBuiltInZoomControls(true);
//        main_website.loadUrl("http://primagroup.primaitech.com/proyek/map/8VG1564F");
        main_website.loadUrl(ServerAccess.ROOT_API+"api/maps/"+detail_proyek.kode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            main_website.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        main_website.setWebViewClient(new WebViewClient());

//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.ECLAIR) {
//            try {
//                Log.d(TAG, "Enabling HTML5-Features");
//                Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
//                m1.invoke(webSettings, Boolean.TRUE);
//
//                Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", new Class[]{Boolean.TYPE});
//                m2.invoke(webSettings, Boolean.TRUE);
//
//                Method m3 = WebSettings.class.getMethod("setDatabasePath", new Class[]{String.class});
//                m3.invoke(webSettings, "/data/data/" + v.getContext().getPackageName() + "/databases/");
//
//                Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", new Class[]{Long.TYPE});
//                m4.invoke(webSettings, 1024*1024*8);
//
//                Method m5 = WebSettings.class.getMethod("setAppCachePath", new Class[]{String.class});
//                m5.invoke(webSettings, "/data/data/" + v.getContext().getPackageName() + "/cache/");
//
//                Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", new Class[]{Boolean.TYPE});
//                m6.invoke(webSettings, Boolean.TRUE);
//
//                Log.d(TAG, "Enabled HTML5-Features");
//            }
//            catch (NoSuchMethodException e) {
//                Log.e(TAG, "Reflection fail", e);
//            }
//            catch (InvocationTargetException e) {
//                Log.e(TAG, "Reflection fail", e);
//            }
//            catch (IllegalAccessException e) {
//                Log.e(TAG, "Reflection fail", e);
//            }
//        }
//        main_website.getSettings().setDefaultTextEncodingName("UTF-8");
//        main_website.getSettings().setBlockNetworkImage(false);
//        main_website.getSettings().setBuiltInZoomControls(true);
//
//        main_website.loadUrl(ServerAccess.ROOT_API+"/api/maps/K8A1VWJA");
//        main_website.setWebViewClient(new WebViewClient());
        return v;
    }
}
