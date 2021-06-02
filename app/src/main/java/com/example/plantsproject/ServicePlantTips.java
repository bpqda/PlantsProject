package com.example.plantsproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicePlantTips {
    @GET("/database/getallplants/")
    Call<List<PlantTip>> getAllPlants();

    @GET("/database/getplant/{name}")
    Call<PlantTip> getPlantTipById(@Query("name") String name);

}
