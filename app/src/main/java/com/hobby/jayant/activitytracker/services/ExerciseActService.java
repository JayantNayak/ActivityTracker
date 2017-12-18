package com.hobby.jayant.activitytracker.services;

/**
 * Created by I329687 on 11/23/2017.
 */

import com.hobby.jayant.activitytracker.models.Exercise;
import com.hobby.jayant.activitytracker.models.Yoga;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ExerciseActService {


    public static final String EXERCISE_SVC_PATH = "/api/activity/exercise";


    @GET(EXERCISE_SVC_PATH)
    Call<List<Exercise>> findAllActivities();


    @POST(EXERCISE_SVC_PATH)
    Call<Void> saveActivity(@Body Exercise activity);


}
