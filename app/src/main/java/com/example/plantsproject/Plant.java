package com.example.plantsproject;

public class Plant {
    String name = "Мое растение";
    int watering;
    int feeding;
    int spraying;

    public Plant(String name, int watering, int feeding, int spraying) {
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
}
