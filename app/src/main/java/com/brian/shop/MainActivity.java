package com.brian.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    TabLayout tabLayout;
    private View tabView;
    private TextView tabTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar =(Toolbar) findViewById(R.id.mainActivityBar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // When the tab is selected, switch to the
                // corresponding page in the ViewPager.
                mViewPager.setCurrentItem(tab.getPosition());


            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(mViewPager);

       }

    private void logout(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

    }
    private void post(){
        Intent intent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_post) {
            post();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    public void goToBidsFragment(){
//        mViewPager.setCurrentItem(2);
//    }
//    public void goTowonBidssFragment(){
//        mViewPager.setCurrentItem(1);
//    }
//    public void goToItemsFragment(){
//        mViewPager.setCurrentItem(0);
//    }
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fm;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    ItemsFragment itemsFragment = ItemsFragment.newInstance();
                    return itemsFragment;

                case 1:
                    BidFragment bidsFragment = BidFragment.newInstance();
                    return bidsFragment;
                case 2:
                    Won_BidsFragment wonFragment = Won_BidsFragment.newInstance();
                    return wonFragment;
//
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "items";
                case 1:
                    return "bids";
                case 2:
                    return "won-bids";
            }
            return "";
        }

        protected View getTabView(int position){
            switch (position) {
                case 0:
                    tabView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_custom_view, null);
                    tabTextView = (TextView) tabView.findViewById(R.id.tab_name);
                    tabTextView.setText("ITEMS");
                    return tabView;
                case 1:
                    tabView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_custom_view, null);
                    tabTextView = (TextView) tabView.findViewById(R.id.tab_name);
                    tabTextView.setText("BIDS");
                    return tabView;
                case 2:
                    tabView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_custom_view, null);
                    tabTextView = (TextView) tabView.findViewById(R.id.tab_name);
                    tabTextView.setText("WON-BIDS");
                    return tabView;
            }
            return null;
        }
    }

    private void d(String s){
        try {
            Log.d("MainActivity", s);
        }catch(NullPointerException ex){
            Log.d("MainActivity","msg is null");

        }
    }
}
