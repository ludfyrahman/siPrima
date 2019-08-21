package com.android.primaitech.siprima.Dashboard;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.android.primaitech.siprima.Akun.Fragment_Akun;
import com.android.primaitech.siprima.R;

public class Dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new Fragment_Dashboard());
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment =  null;
        MenuItem beranda, notif, akun;
        beranda = navigation.getMenu().findItem(R.id.menuBeranda);
        notif = navigation.getMenu().findItem(R.id.menuNotif);
        akun = navigation.getMenu().findItem(R.id.menuAkun);
        switch (menuItem.getItemId()) {
            case R.id.menuBeranda:
                beranda.setIcon(R.drawable.home);
                notif.setIcon(R.drawable.notif_inactive);
                akun.setIcon(R.drawable.akun_inactive);
                fragment = new Fragment_Dashboard();
                break;
            case R.id.menuNotif:
                notif.setIcon(R.drawable.notif);
                beranda.setIcon(R.drawable.home_inactive);
                akun.setIcon(R.drawable.akun_inactive);
                fragment = new Fragment_Notif();
                break;
            case R.id.menuAkun:
                akun.setIcon(R.drawable.akun);
                notif.setIcon(R.drawable.notif_inactive);
                beranda.setIcon(R.drawable.home_inactive);
                fragment = new Fragment_Akun();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
