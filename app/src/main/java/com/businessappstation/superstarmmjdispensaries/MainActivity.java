package com.businessappstation.superstarmmjdispensaries;

import java.util.Locale;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends FragmentActivity {
    public static FragmentManager fragmentManager;

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

        fragmentManager = getSupportFragmentManager();

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
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
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    //show title image
                    return getString(R.string.title_section1).toUpperCase(l);

                case 1:
                    //opens map
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    //opens activity with adv
                    //ProductsFragment prods = new ProductsFragment();
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    //opens qrcode scanner
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }
    }

}
