package com.example.descubremadrid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageViewAdapter extends FragmentPagerAdapter {


    public PageViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new OficinasFragmente();
                break;

            case 1:
                fragment = new MapaFragmente();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
