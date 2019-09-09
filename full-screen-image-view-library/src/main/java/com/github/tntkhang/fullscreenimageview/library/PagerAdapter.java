package com.github.tntkhang.fullscreenimageview.library;


import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentManager;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<Uri> imagesUri = new ArrayList<>();

    PagerAdapter(FragmentManager fragmentManager, List<String> images) {
        super(fragmentManager);
        for (String image : images) {
            this.imagesUri.add(Uri.parse(image));
        }
    }

    @Override
    public Fragment getItem(int position) {
        Uri item = imagesUri.get(position);
        return ImageFragment.newInstance(item);
    }

    @Override
    public int getCount() {
        return imagesUri.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}
