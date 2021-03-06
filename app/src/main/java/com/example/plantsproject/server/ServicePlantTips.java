package com.example.plantsproject.server;

import com.example.plantsproject.entitys.PlantTip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicePlantTips {
    @GET("/getallplants/")
    Call<ArrayList<PlantTip>> getAllPlants();

    @GET("/getplant/")
    Call<PlantTip> getPlantTipByName(@Query("name") String name);

}
