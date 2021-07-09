package com.example.plantsproject.entitys;

import android.content.Context;

import com.example.plantsproject.R;

import java.io.Serializable;

public class Plant implements Serializable {
    private long id;
    private String name;
    private long watering;
    private long feeding;
    private long spraying;
    private String notes;
    private String creationDate;
    private long lastMilWat;
    private long lastMilFeed;
    private long lastMilSpray;
    private int photo;
    private String url;
    private int defaultAutoWatering;

    public Plant(long id, String name, String notes, long watering,
                 long feeding, long spraying,
                 String creationDate, long lastMilWat, long lastMilFeed,
                 long lastMilSpray, int photo, String url,
                 int defaultAutoWatering) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.watering = watering;
        this.feeding = feeding;
        this.spraying = spraying;
        this.creationDate = creationDate;
        this.lastMilWat = lastMilWat;
        this.lastMilFeed = lastMilFeed;
        this.lastMilSpray = lastMilSpray;
        this.photo = photo;
        this.url = url;
        this.defaultAutoWatering = defaultAutoWatering;
    }

    public int getDefaultAutoWatering() {
        return defaultAutoWatering;
    }

    public void setDefaultAutoWatering(int defaultAutoWatering) {
        this.defaultAutoWatering = defaultAutoWatering;
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

    public String getAction(Context c) {
        Plant plant = this;
        String action = "";
        if(plant.getWatering()!=0) {
            action = c.getResources().getString(R.string.need_watering);
        }
        if(plant.getFeeding()!=0) {
            action += c.getResources().getString(R.string.need_feeding);
        }
        if(plant.getSpraying()!=0) {
            action +=c.getResources().getString(R.string.need_spraying);
        }
        return action;
    }

    public void setId(long id) {
        this.id = id;
    }
}
