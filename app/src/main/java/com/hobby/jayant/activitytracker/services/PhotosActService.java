package com.hobby.jayant.activitytracker.services;

/**
 * Created by I329687 on 11/23/2017.
 */

import android.provider.ContactsContract;

import com.hobby.jayant.activitytracker.models.Exercise;
import com.hobby.jayant.activitytracker.models.Photos;
import com.hobby.jayant.activitytracker.models.Yoga;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PhotosActService {

    //@GET("https://gdurl.com/KD27")
    @GET("/KD27")
    Call<Photos> getAllPic();

}
