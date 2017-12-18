package com.hobby.jayant.activitytracker.services;

import com.hobby.jayant.activitytracker.models.Exercise;
import com.hobby.jayant.activitytracker.models.Shooting;
import com.hobby.jayant.activitytracker.models.Yoga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ShootingActService {


    public static final String SHOOTING_SVC_PATH = "/api/activity/shooting";


    @GET(SHOOTING_SVC_PATH)
    Call<List<Shooting>> findAllActivities();


    @POST(SHOOTING_SVC_PATH)
    Call<Void> saveActivity(@Body Shooting activity);


}
