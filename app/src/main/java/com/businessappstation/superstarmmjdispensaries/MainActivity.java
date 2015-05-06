package com.businessappstation.superstarmmjdispensaries;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;


public class MainActivity extends FragmentActivity {
    final ActionBar actionBar = getActionBar();
    //private List<SamplePagerItem>
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //tabs should be seen in action bar
        //since target us not only lollipop, it is possible to use setNavigationMode
        //or may be replaced with PagerTabStrip
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

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
                    fragment = new HomeFragment();
                    //fragment = new MapsFragment();
                    //implement maps fragment https://developers.google.com/maps/documentation/android/start#install_the_android_sdk
                    return fragment;
                case 2:
                    //fragment = new ProductsFragment();
                    fragment = new HomeFragment();
                    return fragment;
                case 3:
                    //fragment = new ProductsFragment();
                    fragment = new HomeFragment();
                    return fragment;
                default:
                    return null;
            }


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
