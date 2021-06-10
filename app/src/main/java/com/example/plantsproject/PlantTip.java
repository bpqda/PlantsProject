package com.example.plantsproject;

import java.io.Serializable;

public class PlantTip implements Serializable {

    int id;

    public PlantTip(int id, String name, int watering, int feeding, int spraying, String notes) {
        this.id = id;
        this.name = name;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.notes = notes;
    }

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

    @Override
    public String toString() {
        return "PlantTip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", watering=" + watering +
                ", feeding=" + feeding +
                ", spraying=" + spraying +
                ", notes='" + notes + '\'' +
                '}';
    }
    Plant toPlant() {
        return new Plant(0, name, notes, watering,
                feeding, spraying, "", "", "", "", 0, 0, 0);
    }

}
