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
    //private static final String TEST_URL = "http://localhost:8080";
    //private static final String TEST_URL ="https://tranquil-crag-24504.herokuapp.com/api/activity/yoga";
    private static final String TEST_URL ="https://tranquil-crag-24504.herokuapp.com/";
    //private static final String TEST_URL ="https://api.github.com/";
    private static YogaActService yogaActivityService ;
    private static UserService userService ;

    public static YogaActService getYogaActivityService(final String  basicAuthToken){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClientForBasicAuth(basicAuthToken))
                .build();
        yogaActivityService = retrofit.create(YogaActService.class);
        return yogaActivityService;
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
