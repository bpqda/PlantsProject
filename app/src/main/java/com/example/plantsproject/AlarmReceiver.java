package com.example.plantsproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            String plantName = intent.getStringExtra("plantName");
            String plantActions =  intent.getStringExtra("plantActions");
            if (plantName!=null && plantActions!=null)
            NotificationScheduler.showNotification(context, MainActivity.class, plantName, plantActions);
            //
    }

}