package com.example.plantsproject;

import java.io.Serializable;

public class Plant implements Serializable {
    String name;
    String notes;
    long watering;
    long feeding;
    long spraying;
    long id;
    String creationDate;
    String lastW;
    String lastF;
    String lastS;
    long lastMilWat;
    long lastMilFeed;
    long lastMilSpray;


    public Plant(long id, String name, String notes, int watering, int feeding, int spraying, String creationDate, String lastW, String lastF, String lastS, long lastMilWat, long lastMilFeed, long lastMilSpray) {
        this.name = name;
        this.notes = notes;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.id = id;
        this.creationDate = creationDate;
        this.lastW = lastW;
        this.lastF = lastF;
        this.lastS = lastS;
        this.lastMilWat = lastMilWat;
        this.lastMilFeed = lastMilFeed;
        this.lastMilSpray = lastMilSpray;
    }

    public long getLastMilWat() {
        return lastMilWat;
    }

    public void setLastMilWat(long lastMilWat) {
        this.lastMilWat = lastMilWat;
    }

    public long getLastMilFeed() {
        return lastMilFeed;
    }

    public void setLastMilFeed(long lastMilFeed) {
        this.lastMilFeed = lastMilFeed;
    }

    public long getLastMilSpray() {
        return lastMilSpray;
    }

    public void setLastMilSpray(long lastMilSpray) {
        this.lastMilSpray = lastMilSpray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWatering() {
        return watering;
    }
    public long getFeeding() {
        return feeding;
    }
    public long getSpraying() {
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

    public String getAction() {
        Plant plant = this;
        String action = "";
        if(plant.getWatering()!=0) {
            action = "полив";
        }
        if(plant.getFeeding()!=0) {
            action = action +  "  удобрение";
        }
        if(plant.getSpraying()!=0) {
            action = action + "  опрыскивание";
        }
        return action;
    }

    public String getLastW() {
        return lastW;
    }

    public void setLastW(String lastW) {
        this.lastW = lastW;
    }

    public String getLastF() {
        return lastF;
    }

    public void setLastF(String lastF) {
        this.lastF = lastF;
    }

    public String getLastS() {
        return lastS;
    }

    public void setLastS(String lastS) {
        this.lastS = lastS;
    }
}
