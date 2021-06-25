package com.example.plantsproject.server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    public static ServicePlantTips createService() {

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://192.168.0.104:8080/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        return retrofit.create(ServicePlantTips.class);
    }
}
