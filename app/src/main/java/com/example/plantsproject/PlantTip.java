package com.example.plantsproject;

public class PlantTip {

    int id;

    public PlantTip(int id, String name, int watering, int feeding, int spraying, String notes) {
        this.id = id;
        this.name = name;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String name;
    int watering;
    int feeding;
    int spraying;
    String notes;

    public String getName() {
        return name;
    }

    public PlantTip(String name, int watering, int feeding, int spraying, String notes) {
        this.name = name;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.notes = notes;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
