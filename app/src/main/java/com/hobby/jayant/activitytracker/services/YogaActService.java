package com.hobby.jayant.activitytracker.services;

/**
 * Created by I329687 on 11/23/2017.
 */
import com.hobby.jayant.activitytracker.models.User;
import com.hobby.jayant.activitytracker.models.Yoga;

import java.util.List;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface YogaActService {

    // The path where we expect the VideoSvc to live
    public static final String YOGA_SVC_PATH = "/api/activity/yoga";

    void deleteActivityById(Long id);

    void deleteAllActivities();

    boolean isActivityExist(Long id);

    @GET(YOGA_SVC_PATH)
    Call<List<Yoga>> findAllActivities();


    @POST(YOGA_SVC_PATH)
    Call<Void> saveActivity(@Body Yoga activity);


}
