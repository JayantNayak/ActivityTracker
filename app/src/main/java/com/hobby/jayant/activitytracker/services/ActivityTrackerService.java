package com.hobby.jayant.activitytracker.services;




import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by I329687 on 11/4/2017.
 */

public  class ActivityTrackerService {
    //private static final String TEST_URL = "http://localhost:8080";
    //private static final String TEST_URL ="https://tranquil-crag-24504.herokuapp.com/api/activity/yoga";
    private static final String TEST_URL ="https://tranquil-crag-24504.herokuapp.com/";
    //private static final String TEST_URL ="https://api.github.com/";
    private static YogaActService yogaActivityService ;
    private static UserService userService ;

    public static YogaActService getYogaActivityService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yogaActivityService = retrofit.create(YogaActService.class);
        return yogaActivityService;
    }
    public static UserService getUserService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
        return userService;
    }
}
