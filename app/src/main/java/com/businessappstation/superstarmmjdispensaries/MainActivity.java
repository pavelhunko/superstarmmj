package com.businessappstation.superstarmmjdispensaries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {

    public static FragmentManager fragmentManager;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //maps fragment
        fragmentManager = getSupportFragmentManager();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);
        //mSectionsPagerAdapter.set

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        //fi
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setAdapter(mSectionsPagerAdapter);


    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    return fragment;
                case 1:
                    //fragment = new HomeFragment();
                    fragment = new MapsFragment();
                    //implement maps fragment https://developers.google.com/maps/documentation/android/start#install_the_android_sdk
                    return fragment;
                case 2:
                    fragment = new ProductsFragment();
                    //fragment = new HomeFragment();
                    return fragment;
                case 3:
                    // fragment = new HomeFragment();
                    fragment = new QRFragment();
                    return fragment;

            }
            //return fragment;
            return null;

        }

        //pages total
        @Override
        public int getCount() {
            return 4;
        }

        //page titles
        @Override
        public CharSequence getPageTitle(int position) {
            //Locale l = Locale.getDefault();
            //https://github.com/astuetz/PagerSlidingTabStrip
            switch (position) {
                case 0:
                    //title image
                    return getString(R.string.home_tab);
                case 1:
                    //map
                    return getString(R.string.location_tab);
                case 2:
                    //products
                    return getString(R.string.products_tab);
                case 3:
                    //qrcode scanner
                    return getString(R.string.qr_tab);
            }
            return null;
        }
    }
}
