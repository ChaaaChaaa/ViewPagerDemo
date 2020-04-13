package com.example.viewpagerdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;


import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        getViews();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Tab Layout");
        }
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }


    private void getViews() {
        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),viewPager.getCurrentItem());
        viewPagerAdapter.addFragment(new FragmentOne(), "One");
        viewPagerAdapter.addFragment(new FragmentTwo(), "Two");
        viewPagerAdapter.addFragment(new FragmentThree(), "Three");
        viewPagerAdapter.addFragment(new FragmentFour(), "Four");
        viewPagerAdapter.addFragment(new FragmentFive(), "Five");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
