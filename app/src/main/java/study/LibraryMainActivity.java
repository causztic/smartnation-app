package study;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.anweshabiswas.smartnation.R;

import java.util.ArrayList;
import java.util.List;

import models.MeetingPlaces;

public class LibraryMainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private ArrayList<MeetingPlaces> list;
    private List<Fragment> libraryFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_library);

        Intent i = getIntent();
        list = (ArrayList<MeetingPlaces>) i
                .getSerializableExtra("LIBRARY");
        libraryFragments = new ArrayList<>();

        for (MeetingPlaces item: list){
            LibraryFragment lf = new LibraryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("LIBRARY", item);
            lf.setArguments(bundle);
            libraryFragments.add(lf);
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return libraryFragments.get(position);
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
