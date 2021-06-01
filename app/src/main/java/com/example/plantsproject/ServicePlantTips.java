package com.example.plantsproject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServicePlantTips {
    @GET("/database/getallplants/")
    Call<List<PlantTip>> getAllPlants();

    @GET("/database/getallplants/")
    public Call<ArrayList<PlantTip>> getPlantTips();
}
