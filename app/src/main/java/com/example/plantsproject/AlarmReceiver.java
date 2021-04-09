package com.example.plantsproject;
;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            NotificationScheduler.showNotification(context, MainActivity.class, "Требуется уход за растением", "Зайти в приложение");
    }

}