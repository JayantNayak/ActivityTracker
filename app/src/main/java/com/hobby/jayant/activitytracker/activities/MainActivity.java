package com.hobby.jayant.activitytracker.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hobby.jayant.activitytracker.R;
import com.hobby.jayant.activitytracker.activities.statistics.ExerciseStatsActivity;
import com.hobby.jayant.activitytracker.activities.statistics.ShootingStatsActivity;
import com.hobby.jayant.activitytracker.activities.statistics.YogaStatsActivity;
import com.hobby.jayant.activitytracker.adapters.SectionsPagerAdapter;
import com.hobby.jayant.activitytracker.models.Photos;
import com.hobby.jayant.activitytracker.services.ActivityTrackerService;
import com.hobby.jayant.activitytracker.services.PhotosActService;
import com.hobby.jayant.activitytracker.utils.Utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String profileUserName;
    private String basicAuthToken;
    private static Photos photosStore;
    private static String backGroundImgUrl;
    private static ImageView imgBackView;

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
    static private final String TAG ="MainActivity";
    private static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        /*String firstname = intent.getStringExtra("firstname");
        String lastname = intent.getStringExtra("lastname");
         emailId = intent.getStringExtra("emailId");
         password = intent.getStringExtra("password");*/

        SharedPreferences usersettings = getSharedPreferences(SiginActivity.PREFS_NAME, 0);
        String firstname = usersettings.getString(SiginActivity.USER_FIRST_NAME,"null");
        String lastname = usersettings.getString(SiginActivity.USER_LAST_NAME,"null");
        basicAuthToken = usersettings.getString(SiginActivity.BASIC_AUTH_TOKEN,"null");

        profileUserName = firstname +" "+ lastname;

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        imgBackView = (ImageView)findViewById(R.id.imageView);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.profileUserName);
        nav_user.setText(profileUserName);


        if(photosStore == null){

            PhotosActService photosActService  = ActivityTrackerService.getPhotoActService();
            Call<Photos> photos =  photosActService.getAllPic();

            photos.enqueue(new Callback<Photos>() {
                @Override
                public void onResponse(Call<Photos> call, Response<Photos> response) {

                    Photos photoResponse = response.body();
                    photosStore = photoResponse;


                    Log.d(TAG, "Photo Response ! "+photoResponse.toString());

                    if(photosStore!=null){
                        backGroundImgUrl = getBackgroundPicUrl("Shooting");

                        ArrayList<Utils.DummyItem> dummyItems = new ArrayList<>();
                        dummyItems.add(new Utils.DummyItem(getBackgroundPicUrl("Shooting")));
                        dummyItems.add(new Utils.DummyItem(getBackgroundPicUrl("Yoga")));
                        dummyItems.add(new Utils.DummyItem(getBackgroundPicUrl("Exercise")));
                        mSectionsPagerAdapter.setDummyItems(dummyItems);
                        mSectionsPagerAdapter.notifyDataSetChanged();

                    }

                }
                @Override
                public void onFailure(Call<Photos> call, Throwable t) {
                    //final TextView textView = (TextView) findViewById(R.id.textView);
                    //textView.setText("Something went wrong: " + t.getMessage());

                    Log.d(TAG, "Failed Photo Service! "+t.toString());

                }
            });

        }
        if(photosStore!=null){
            backGroundImgUrl = getBackgroundPicUrl("Shooting");

            ArrayList<Utils.DummyItem> dummyItems = new ArrayList<>();
            dummyItems.add(new Utils.DummyItem(getBackgroundPicUrl("Shooting")));
            dummyItems.add(new Utils.DummyItem(getBackgroundPicUrl("Yoga")));
            dummyItems.add(new Utils.DummyItem(getBackgroundPicUrl("Exercise")));
            mSectionsPagerAdapter.setDummyItems(dummyItems);
            mSectionsPagerAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            //TextView profileName = (TextView)findViewById(R.id.profileUserName);
           // profileName.setText(profileUserName);
        }
    }
    private void displayToast(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.yoga_stats) {

            Intent myIntent = new Intent(MainActivity.this, YogaStatsActivity.class);
            MainActivity.this.startActivity(myIntent);

        } else if (id == R.id.exercise_stats) {
            Intent myIntent = new Intent(MainActivity.this, ExerciseStatsActivity.class);
            MainActivity.this.startActivity(myIntent);

        } else if (id == R.id.shooting_stats) {
            Intent myIntent = new Intent(MainActivity.this, ShootingStatsActivity.class);
            MainActivity.this.startActivity(myIntent);

        } else if (id == R.id.email) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private static  String getBackgroundPicUrl(String pageTitle ){

        String url ="";
        //pageTitle = PAGETYPE.getPageTitle(tabLayout.getSelectedTabPosition());
        switch (pageTitle){
            case "Shooting":
                url = randomPicUrl(photosStore.getShootingphotos());
                break;
            case "Yoga":
                url = randomPicUrl(photosStore.getYogaphotos());
                break;
            case "Exercise":
                url = randomPicUrl(photosStore.getExercisephotos());
                break;
        }
        return url;

    }


    private  static  String randomPicUrl(Map<String,String> activityTypePicStore){
        String url = "";
        int randomNum = ThreadLocalRandom.current().nextInt(1, activityTypePicStore.size() + 1);
        String key = randomNum+"";
        if(activityTypePicStore.containsKey(key)){
            url =  activityTypePicStore.get(key);
        }
       // url=activityTypePicStore.get("1");
        return  url;

    }


}
