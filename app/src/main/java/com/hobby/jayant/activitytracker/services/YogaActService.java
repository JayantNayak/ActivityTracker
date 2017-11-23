package com.hobby.jayant.activitytracker.services;

/**
 * Created by I329687 on 11/23/2017.
 */
import com.hobby.jayant.activitytracker.models.Yoga;

import java.util.List;



import retrofit2.Call;
import retrofit2.http.GET;
public interface YogaActService {

    // The path where we expect the VideoSvc to live
    public static final String YOGA_SVC_PATH = "/api/activity/yoga";

    void deleteActivityById(Long id);

    void deleteAllActivities();

    boolean isActivityExist(Long id);

    @GET(YOGA_SVC_PATH)
    Call<List<Yoga>> findAllActivities();

    Yoga findById(Long id);

    List<Yoga> getActivitiesForUserFromDate(long userid, String startDate);

    void saveActivity(Yoga activity);

    void updateActivity(Yoga activity);

}
