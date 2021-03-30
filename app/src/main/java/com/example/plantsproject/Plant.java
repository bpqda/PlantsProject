package com.example.plantsproject;


import java.io.Serializable;

public class Plant implements Serializable {
    String name;
    int watering;
    int feeding;
    int spraying;
    long id;

    public Plant(long id, String name, int watering, int feeding, int spraying) {
        this.id = id;
        this.name = name;
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

    public void setWatering(int watering) {
        this.watering = watering;
    }

    public int getFeeding() {
        return feeding;
    }

    public void setFeeding(int feeding) {
        this.feeding = feeding;
    }

    public int getSpraying() {
        return spraying;
    }

    public void setSpraying(int spraying) {
        this.spraying = spraying;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", watering=" + watering +
                ", feeding=" + feeding +
                ", spraying=" + spraying +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
