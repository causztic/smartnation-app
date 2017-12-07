package com.example.anweshabiswas.smartnation;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class LibraryMainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private   ArrayList<MeetingPlaces> list;
    public static MeetingPlaces lvl1;
    public static MeetingPlaces lvl2;
    public static MeetingPlaces lvl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_library);

        Intent i = getIntent();
       list = (ArrayList<MeetingPlaces>) i
                .getSerializableExtra("LIBRARY");
        Log.i("Library",list.toString());

        for(MeetingPlaces a:list)
        {
            if(a.getName().equalsIgnoreCase("Library (1st floor)"))
            {
                lvl1 = a;
                Log.i("library",a.getName());
            }
            else
            if(a.getName().equalsIgnoreCase("Library (2nd floor)"))
                lvl2=a;
            else
                lvl3=a;

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }
    public static MeetingPlaces getObj(int pos)
    {
        switch(pos)
        {
            case 0:
                return lvl1;

            case 1:
                return lvl2;

            case 2:
                return lvl3;

            default:
                return null;
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Library1Fragment lib1 = new Library1Fragment();
                    return lib1;

                case 1:
                    Library2Fragment lib2 = new Library2Fragment();
                    return lib2;
                case 2:
                    Library3Fragment lib3 = new Library3Fragment();
                    return lib3;
                default:
                    return null;
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Level 1";
                case 1:
                    return "Level 2";
                case 2:
                    return "Level 3";
            }
            return null;
        }


    }
}
