package com.hobby.jayant.activitytracker.services;

import com.hobby.jayant.activitytracker.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by I329687 on 11/24/2017.
 */

public interface UserService {

    @POST("api/createUser")
    Call<Void> saveUser(@Body User user);

    @GET("api/userlogin")
    Call<User> loginAndGetUser();


}
