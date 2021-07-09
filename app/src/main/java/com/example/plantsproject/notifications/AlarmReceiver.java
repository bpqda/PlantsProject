package com.example.plantsproject.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.plantsproject.activities.MainActivity;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.entitys.Plant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/*ПОКАЗ УВЕДОМЛЕНИЙ*/

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra("id", -1);

        //Показ уведомления, id передается чтобы потом проверить растение на существование в бд
        NotificationScheduler.showNotification(context, MainActivity.class, id);

        DBPlants db = new DBPlants(context);
        Plant plant = db.select(id);

        //Автополив
        if (plant != null && plant.getWatering() > 0 && !plant.getUrl().equals("")) {
            HttpURLConnection connection = null;

            //Отправка http запроса
            try {
                URL urlToWater = new URL(plant.getUrl() + "/cm?cmnd=Backlog%20Power%20on%3BDelay%20" + plant.getDefaultAutoWatering()*10 + "%3BPower1%20off");
                URLConnection urlConnection = urlToWater.openConnection();

                //получение http ответа
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                urlConnection.getInputStream()));

                bufferedReader.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}