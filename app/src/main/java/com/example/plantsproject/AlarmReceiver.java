package com.example.plantsproject;
;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            Plant plant = (Plant) intent.getSerializableExtra("plant");
       //     if (intent.getStringExtra("some") == null)
       //         System.out.println("NULL");
       // else System.out.println(intent.getStringExtra("some"));
            NotificationScheduler.showNotification(context, MainActivity.class, plant);
    }

}