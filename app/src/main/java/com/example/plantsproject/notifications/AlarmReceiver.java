package com.example.plantsproject.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.plantsproject.activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            String plantName = intent.getStringExtra("plantName");
            String plantActions =  intent.getStringExtra("plantActions");
            long id =  intent.getLongExtra("id", -1);
            
            NotificationScheduler.showNotification(context, MainActivity.class, plantName, plantActions, id);
    }

}