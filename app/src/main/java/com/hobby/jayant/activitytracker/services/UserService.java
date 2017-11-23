package com.hobby.jayant.activitytracker.services;

import com.hobby.jayant.activitytracker.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by I329687 on 11/24/2017.
 */

public interface UserService {

    @POST("api/user")
    Call<Void> saveUser(@Body User user);
}
