package com.hobby.jayant.activitytracker;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by I329687 on 8/16/2017.
 */

public class ActivityTracker extends Application {
    private static Context context;
    public ActivityTracker(){}
    public static Context getApplicationGlobalContext(){ return ActivityTracker.context;}
    public void onCreate() {
        super.onCreate();
        ActivityTracker.context = getApplicationContext();
    }

}
