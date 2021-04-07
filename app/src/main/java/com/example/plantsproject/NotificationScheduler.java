package com.example.plantsproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;



//https://droidmentor.com/schedule-notifications-using-alarmmanager/



public class NotificationScheduler {

    public static final int REMINDER_REQUEST_CODE=100;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void showNotification(Context context, Class<?> cls, String title, String content) {
        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//при нажатии на уведомление откроет MainActivity
//       TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//       stackBuilder.addParentStack(cls);
//       stackBuilder.addNextIntent(notificationIntent);
//создание уведомления
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "CHANNEL_ID")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(REMINDER_REQUEST_CODE, builder.build());
    }

    public static void cancelReminder(Context context,Class<?> cls) {
        // Выключение ресивера

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

    public static void setReminder(Context context,Class<?> cls, Plant plant)
    {
        Calendar calendar = Calendar.getInstance();

        //Calendar setCalendar = Calendar.getInstance();
        //setCalendar.set(Calendar.HOUR_OF_DAY, 0);
        //setCalendar.set(Calendar.MINUTE, 0);
        //setCalendar.set(Calendar.SECOND, 1);

        // cancel already scheduled reminders
        //cancelReminder(context,cls);

        //if(setCalendar.before(calendar))
        //    setCalendar.add(Calendar.DATE,1);

        // Enable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), plant.getWatering()*1000, pendingIntent);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), plant.getFeeding()*1000, pendingIntent);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), plant.getSpraying()*1000, pendingIntent);

        Toast.makeText(context, "Уведомления установлены", Toast.LENGTH_SHORT).show();


    }

}

