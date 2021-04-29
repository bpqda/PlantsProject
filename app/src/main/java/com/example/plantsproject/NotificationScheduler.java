package com.example.plantsproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import static android.content.Context.ALARM_SERVICE;

public class NotificationScheduler {

    public static final int REMINDER_REQUEST_CODE=100;

    public static void showNotification(Context context, Class<?> cls, Plant plant) {
        Intent notificationIntent = new Intent(context, cls);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                REMINDER_REQUEST_CODE,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "CHANNEL_ID")
                        .setSmallIcon(R.drawable.plantpicture)
                        .setContentTitle("Требуется уход за растением")
      //                  .setContentText("Растение" + plant.getName() + "требует " + plant.getAction())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .addAction(R.drawable.plant, "Открыть", pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(REMINDER_REQUEST_CODE, builder.build());
    }

    public static void cancelReminder(Context context,Class<?> cls) {

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void setReminder(Context context,Class<?> cls, long period, Plant plant) {

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        intent1.putExtra("plant", plant);
        intent1.putExtra("some", "123");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + period*1000, period*1000, pendingIntent);

        Toast.makeText(context, "Уведомления установлены", Toast.LENGTH_SHORT).show();

    }

}

