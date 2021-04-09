package com.example.plantsproject;

import java.io.Serializable;

public class Plant implements Serializable {
    String name;
    String notes;
    int watering;
    int feeding;
    int spraying;
    long id;

    public Plant(long id, String name, String notes, int watering, int feeding, int spraying) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
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

}
