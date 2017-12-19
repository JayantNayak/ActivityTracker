package com.hobby.jayant.activitytracker.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hobby.jayant.activitytracker.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        SharedPreferences usersettings = getSharedPreferences(SigninActivity.PREFS_NAME, 0);
        String basicAuthToken = usersettings.getString(SigninActivity.BASIC_AUTH_TOKEN,null);
        // user already signed in directly navigate to main activity
        // else navigate to signin activity
        if(basicAuthToken != null && !"".equals(basicAuthToken)){
            Intent myIntent = new Intent(LauncherActivity.this, MainActivity.class);
            LauncherActivity.this.startActivity(myIntent);
        }else{
            Intent myIntent = new Intent(LauncherActivity.this, SigninActivity.class);
            LauncherActivity.this.startActivity(myIntent);
        }
    }
}
