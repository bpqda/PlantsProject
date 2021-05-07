package com.example.plantsproject;
;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            String plantName = intent.getStringExtra("plantName");
            String plantActions =  intent.getStringExtra("plantActions");
            NotificationScheduler.showNotification(context, MainActivity.class, plantName, plantActions);
    }

}