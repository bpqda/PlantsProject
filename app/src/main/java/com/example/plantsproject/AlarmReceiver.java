package com.example.plantsproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class AlarmReceiver extends BroadcastReceiver {
    Plant plant;

    public AlarmReceiver(Plant plant) {
        this.plant = plant;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationScheduler.showNotification(context, MainActivity.class, "Требуется уход за растением", plant.getName());
    }

}