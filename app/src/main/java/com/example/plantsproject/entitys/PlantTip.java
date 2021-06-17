package com.example.plantsproject.entitys;

import java.io.Serializable;

public class PlantTip implements Serializable{

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

   private String name;
   private int watering;
   private int feeding;
   private int spraying;
   private String notes;

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

    public int getFeeding() {
        return feeding;
    }


    public int getSpraying() { return spraying; }


    public String getNotes() {
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
    public Plant toPlant() {
        return new Plant(0, name, notes, watering,
                feeding, spraying, "", "", "", "", 0, 0, 0, 0, "");
    }


}
