package com.example.plantsproject;

import java.io.Serializable;

class Plant implements Serializable {
    private String name;
    private String notes;
    private long watering;
    private long feeding;
    private long spraying;
    private long id;
    private String creationDate;
    private String lastW;
    private String lastF;
    private String lastS;
    private long lastMilWat;
    private long lastMilFeed;
    private long lastMilSpray;

    Plant(long id, String name, String notes, int watering, int feeding, int spraying, String creationDate, String lastW, String lastF, String lastS, long lastMilWat, long lastMilFeed, long lastMilSpray) {
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

    long getLastMilWat() {
        return lastMilWat;
    }

    void setLastMilWat(long lastMilWat) {
        this.lastMilWat = lastMilWat;
    }

    long getLastMilFeed() {
        return lastMilFeed;
    }

    void setLastMilFeed(long lastMilFeed) {
        this.lastMilFeed = lastMilFeed;
    }

    long getLastMilSpray() {
        return lastMilSpray;
    }

    void setLastMilSpray(long lastMilSpray) {
        this.lastMilSpray = lastMilSpray;
    }

    String getName() {
        return name;
    }

    long getWatering() {
        return watering;
    }
    long getFeeding() {
        return feeding;
    }
    long getSpraying() {
        return spraying;
    }

    long getId() {
        return id;
    }

    String getNotes() {
        return notes;
    }
    String getCreationDate() {
        return creationDate;
    }

    String getAction() {
        Plant plant = this;
        String action = "";
        if(plant.getWatering()!=0) {
            action = "полива";
        }
        if(plant.getFeeding()!=0) {
            action += ", удобрения";
        }
        if(plant.getSpraying()!=0) {
            action +=", опрыскивания";
        }
        return action;
    }

    String getLastW() {
        return lastW;
    }

    void setLastW(String lastW) {
        this.lastW = lastW;
    }

    String getLastF() {
        return lastF;
    }

    void setLastF(String lastF) {
        this.lastF = lastF;
    }

    String getLastS() {
        return lastS;
    }

    void setLastS(String lastS) {
        this.lastS = lastS;
    }

    String convertNameToURLform() {
        if(name.contains(" ")) {
            name.replace(" ", "_");
        }
        return name.toLowerCase();
    }
}
