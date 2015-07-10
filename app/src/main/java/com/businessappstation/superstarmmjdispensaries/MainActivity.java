package com.businessappstation.superstarmmjdispensaries;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;


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


        //Bind tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

//        tabs.set
//        tabs.
        tabs.setViewPager(mViewPager);


    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.CustomTabProvider{

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        final int[] ICONS_GRAY = new int[]{
                R.drawable.home_icon_grey,
                R.drawable.location_icon_grey,
                R.drawable.products_icon_grey,
                R.drawable.qr_icon_grey,
        };
        final int[] ICONS_GREEN = new int[]{
                R.drawable.home_icon_green,
                R.drawable.location_icon_green,
                R.drawable.products_icon_green,
                R.drawable.qr_icon_green,
        };
        final int[] TAB_TITLE = new int[]{
                R.string.home_tab,
                R.string.location_tab,
                R.string.products_tab,
                R.string.qr_tab,
        };

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
                    fragment = new MapsFragment();
                    return fragment;
                case 2:
                    fragment = new ProductsFragment();
                    return fragment;
                case 3:
                    fragment = new QRFragment();
                    return fragment;

            }
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
           return getString(TAB_TITLE[position]);
        }


        @Override
        public View getCustomTabView(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.custom_tab_view, viewGroup, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
            imageView.setImageDrawable(getDrawable(ICONS_GRAY[i]));

            TextView textView = (TextView) view.findViewById(R.id.tab_name);
            textView.setText(getPageTitle(i));
            view.setPadding(0,0,0,20);
            return view;
        }

        @Override
        public void tabSelected(View view) {

        }

        @Override
        public void tabUnselected(View view) {

        }
    }
}
