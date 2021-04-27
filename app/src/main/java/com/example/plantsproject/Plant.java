package com.example.plantsproject;

import java.io.Serializable;

public class Plant implements Serializable {
    String name;
    String notes;
    int watering;
    int feeding;
    int spraying;
    long id;
    String creationDate;



    public Plant(long id, String name, String notes, int watering, int feeding, int spraying, String creationDate) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWatering() {
        return watering;
    }

    public int getFeeding() {
        return feeding;
    }

    public int getSpraying() {
        return spraying;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    public String getAction() {
        Plant plant = this;
        String action = "";
        if(plant.getWatering()!=0) {
            action = "полив";
        }
        if(plant.getFeeding()!=0) {
            action = action +  ", удобрение";
        }
        if(plant.getSpraying()!=0) {
            action = action + ", опрыскивание";
        }
        return action;
    }

}
