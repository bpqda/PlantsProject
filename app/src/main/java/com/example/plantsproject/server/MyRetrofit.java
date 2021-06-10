package com.example.plantsproject.server;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    public static ServicePlantTips createService() {

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://10.0.2.2:8080/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        return retrofit.create(ServicePlantTips.class);
    }
}
