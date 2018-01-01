package com.hobby.jayant.activitytracker.services;




import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by I329687 on 11/4/2017.
 */

public  class ActivityTrackerService {

    //TODO Replace with factory url or Parametrise it if possible
    private static final String TEST_URL ="https://tranquil-crag-24504.herokuapp.com/";
    private static YogaActService yogaActivityService ;
    private static ExerciseActService exerciseActivityService ;
    private static ShootingActService shootingActivityService;
    private static UserService userService ;
    private static PhotosActService photoActService;

    public static YogaActService getYogaActivityService(final String  basicAuthToken){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClientForBasicAuth(basicAuthToken))
                .build();
        yogaActivityService = retrofit.create(YogaActService.class);
        return yogaActivityService;
    }


    public static ExerciseActService getExerciseActivityService(final String  basicAuthToken){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClientForBasicAuth(basicAuthToken))
                .build();
        exerciseActivityService = retrofit.create(ExerciseActService.class);
        return exerciseActivityService;
    }

    public static ShootingActService getShootingActivityService(final String  basicAuthToken){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClientForBasicAuth(basicAuthToken))
                .build();
        shootingActivityService = retrofit.create(ShootingActService.class);
        return shootingActivityService;
    }


    public static UserService getUserService(){
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        userService = retrofit.create(UserService.class);
        return userService;
    }

    public static PhotosActService getPhotoActService(){
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gdurl.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        photoActService = retrofit.create(PhotosActService.class);
        return photoActService;
    }

    public static UserService getUserBasicAuthService(String usernameoremail , String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClientForBasicAuth(usernameoremail, password))
                .build();
        userService = retrofit.create(UserService.class);
        return userService;
    }

    private static OkHttpClient getHttpClientForBasicAuth(String usernameoremail , String password){
        final String authToken = Credentials.basic(usernameoremail, password);
         OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization", authToken)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }

    private static OkHttpClient getHttpClientForBasicAuth(final String authToken){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .addHeader("Authorization", authToken)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}
