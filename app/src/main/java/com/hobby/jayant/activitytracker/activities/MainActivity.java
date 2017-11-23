package com.hobby.jayant.activitytracker.activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.models.Yoga;
import com.hobby.jayant.activitytracker.services.ActivityTrackerService;
import com.hobby.jayant.activitytracker.services.YogaActService;
import com.hobby.jayant.activitytracker.utils.DownloadImageTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    public enum PAGETYPE {
        SHOOTING("Shooting"),YOGA("Yoga"),EXERCISE("Exercise");

        private String pageTitle;

        PAGETYPE(String pageTitle) {
            this.pageTitle = pageTitle;
        }
        public String toString(){
            return pageTitle;
        }
        public  String pageTitle() {
            return pageTitle;
        }
        public  static String getPageTitle(int position){
            String result = null;
            switch (position) {
                case 0:
                    result = PAGETYPE.SHOOTING.pageTitle();
                    break;
                case 1:
                    result=  PAGETYPE.YOGA.pageTitle();
                    break;
                case 2:
                    result= PAGETYPE.EXERCISE.pageTitle();
                    break;
            }
            return result;
        }
    };
    private static String pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pageTitle = PAGETYPE.getPageTitle(tabLayout.getSelectedTabPosition());
                Intent myIntent = new Intent(MainActivity.this, CreateSaveActivity.class);
                myIntent.putExtra("pagetitle", pageTitle); //Optional parameters
                MainActivity.this.startActivity(myIntent);

                Snackbar.make(view, "Replace with your own action " + pageTitle, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

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
            YogaActService yogaActivityService  = ActivityTrackerService.getYogaActivityService();
            Call<List<Yoga>> yogas =  yogaActivityService.findAllActivities();

            yogas.enqueue(new Callback<List<Yoga>>() {
                @Override
                public void onResponse(Call<List<Yoga>> call, Response<List<Yoga>> response) {


                    List<Yoga>yogaList = response.body();
                    String com=yogaList.get(0).getComment();
                   // Toast.makeText(this, "hiii " +com,Toast.LENGTH_LONG).show();
                    //String com1 = com;
                    displayToast(com);
                }
                @Override
                public void onFailure(Call<List<Yoga>> call, Throwable t) {
                    //final TextView textView = (TextView) findViewById(R.id.textView);
                    //textView.setText("Something went wrong: " + t.getMessage());
                }
            });







            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void displayToast(String msg){
        Toast.makeText(this, "hiii " +msg,Toast.LENGTH_LONG).show();
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
            String MY_URL_STRING = "https://www.biggerbolderbaking.com/wp-content/uploads/2016/05/BBB124-Top-5-Homemade-Ice-Cream-Flavors-Thumbnail-FINAL-2-1024x576.jpg";
            //String MY_URL_STRING = "http://homean.me/wp-content/uploads/2017/04/quotes-about-life-buddha-14-top-10-most-inspiring-stalker.jpg";
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            String text = getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER));
            textView.setText(text);
            new DownloadImageTask((ImageView) rootView.findViewById(R.id.imageView))
                    .execute(MY_URL_STRING);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGETYPE.getPageTitle(position);
        }
    }
}
