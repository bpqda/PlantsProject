package com.example.plantsproject.server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final String SERVER_IPV4= "http://10.0.2.2:8081/";
    public static ServicePlantTips createService() {

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(SERVER_IPV4)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        return retrofit.create(ServicePlantTips.class);
    }
}
