package com.example.plantsproject.entitys;

import java.io.Serializable;

public class Plant implements Serializable {
    private long id;
    private String name;
    private long watering;
    private long feeding;
    private long spraying;
    private String notes;
    private String creationDate;
    private String lastW;
    private String lastF;
    private String lastS;
    private long lastMilWat;
    private long lastMilFeed;
    private long lastMilSpray;
    private int photo;
    private String url;



    public Plant(long id, String name, String notes, int watering, int feeding, int spraying,
                 String creationDate, String lastW, String lastF, String lastS,
                 long lastMilWat, long lastMilFeed, long lastMilSpray, int photo, String url) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.creationDate = creationDate;
        this.lastW = lastW;
        this.lastF = lastF;
        this.lastS = lastS;
        this.lastMilWat = lastMilWat;
        this.lastMilFeed = lastMilFeed;
        this.lastMilSpray = lastMilSpray;
        this.photo = photo;
        this.url = url;
    }
    public int getPhoto() {
        return photo;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", watering=" + watering +
                ", feeding=" + feeding +
                ", spraying=" + spraying +
                ", id=" + id +
                ", creationDate='" + creationDate + '\'' +
                ", lastW='" + lastW + '\'' +
                ", lastF='" + lastF + '\'' +
                ", lastS='" + lastS + '\'' +
                ", lastMilWat=" + lastMilWat +
                ", lastMilFeed=" + lastMilFeed +
                ", lastMilSpray=" + lastMilSpray +
                ", photo=" + photo +
                '}';
    }

    public void setPhoto(int photo) {
        this.photo = photo;
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

    public String convertNameToURLform() {
        if(name.contains(" ")) {
            name.replace(" ", "_");
        }
        return name.toLowerCase();
    }

    public void setId(long id) {
        this.id = id;
    }
}
