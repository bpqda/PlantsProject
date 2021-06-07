package com.example.plantsproject;

public class PlantTip {

    int id;

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

   private String name;
   private int watering;
   private int feeding;
   private int spraying;
   private String notes;

    String getName() {
        return name;
    }

    PlantTip(String name, int watering, int feeding, int spraying, String notes) {
        this.name = name;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.notes = notes;
    }

    void setName(String name) {
        this.name = name;
    }

    int getWatering() {
        return watering;
    }

    int getFeeding() {
        return feeding;
    }


    int getSpraying() { return spraying; }


    String getNotes() {
        return notes;
    }

}
