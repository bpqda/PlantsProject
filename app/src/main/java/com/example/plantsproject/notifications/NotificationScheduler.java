package com.example.plantsproject.notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.plantsproject.R;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.entitys.Plant;

import static android.content.Context.ALARM_SERVICE;

/*УВЕДОМЛЕНИЯ*/

public class NotificationScheduler {

    private static final int REMINDER_REQUEST_CODE = 100;
    private static final String CHANNEL_ID = "CHANNEL_ID";


    //Создание уведомления
    public static void showNotification(Context context, Class<?> cls, long id) {

        //Если растения нет в бд, то уведомление не показывается
        DBPlants db = new DBPlants(context);
        Plant plant = db.select(id);
        if (plant == null) {
            return;
        }

        //Для весии Android 8+
        createNotificationChannel(context);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                REMINDER_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        //Создание уведомления
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.plant_picture)
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setContentText(context.getResources().getString(R.string.plant) + " " + plant.getName() + " " + context.getResources().getString(R.string.need) + " " + plant.getAction(context))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .addAction(R.drawable.plant_default, context.getResources().getString(R.string.open), pendingIntent)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getResources().getString(R.string.plant) + " "
                                + plant.getName() + " " +
                                context.getResources().getString(R.string.need) + " " + plant.getAction(context)))
                        .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(REMINDER_REQUEST_CODE, builder.build());

    }
    public static void showNotificationWatered(Context context) {
        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.plant_picture)
                .setContentTitle(context.getString(R.string.autowatered))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.build();
    }


    //Выключение уведомлений
    public static void cancelReminder(Context context, Class<?> cls) {

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

    //Установка периодических уведомлений
    public static void setReminder(Context context, Class<?> cls, long period, Plant plant) {

        ComponentName receiver = new ComponentName(context, cls);

        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        intent1.putExtra("plantName", plant.getName());
        intent1.putExtra("plantActions", plant.getAction(context));
        intent1.putExtra("id", plant.getId());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                REMINDER_REQUEST_CODE,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + period * 1000 * 60 * 60 * 24,
                period * 1000 * 60 * 60 * 24,
                pendingIntent);

        Toast.makeText(context, R.string.notifs_setted, Toast.LENGTH_SHORT).show();
    }

    //Поддержка уведомлений для весии Android 8+
    private static void createNotificationChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

