package com.example.sharewardrobeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.sharewardrobeapp.view.MainViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends BasementActivity {
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private int mViewPagerPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = findViewById(R.id.main_activity_pager);
        mTabLayout = findViewById(R.id.main_activity_pager_tab_layout);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPagerPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainViewPagerAdapter mPagerAdapter =
                new MainViewPagerAdapter(getApplicationContext());
        mPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mPager);
        mPager.setCurrentItem(mViewPagerPage);
    }
}