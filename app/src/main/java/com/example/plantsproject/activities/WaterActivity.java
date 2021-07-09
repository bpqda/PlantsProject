package com.example.plantsproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantsproject.R;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.entitys.Plant;
import com.example.plantsproject.notifications.AlarmReceiver;
import com.example.plantsproject.notifications.NotificationScheduler;
import com.example.plantsproject.server.MyRetrofit;
import com.example.plantsproject.server.ServicePlantTips;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*ВКЛЮЧЕНИЕ АВТОПОЛИВА*/

public class WaterActivity extends AppCompatActivity {

    String url;
    SeekBar seekBar;
    ProgressBar progressBar;
    int period;
    boolean httpAnswerIsTrue;
    DBPlants db;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        ImageButton autoWater = findViewById(R.id.autoWater);
        TextView waterOb = findViewById(R.id.waterOb);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        //Получение ip адреса и обильности полива по умолчанию
        db = new DBPlants(getBaseContext());
        plant = db.select(getIntent().getExtras().getLong("plantID"));

        url = plant.getUrl();
        TextView urlTextView = findViewById(R.id.textView16);
        urlTextView.setText(getString(R.string.url) + " " + url);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(plant.getDefaultAutoWatering());

        //Автоопределение SSID
        EditText ssid = findViewById(R.id.editText);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        String ssidStr = info.getSSID();
        if (!ssidStr.equals("<unknown ssid>")) {
            ssid.setText(info.getSSID().replace("\"", ""));
        }

        //Настройка длительности полива
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterOb.setText(seekBar.getProgress() + " " + getString(R.string.sec));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        waterOb.setText(seekBar.getProgress() + " " + getString(R.string.sec));

        //Автополив
        autoWater.setOnClickListener(v -> {
            MyTask task = new MyTask();
            task.execute();

        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());
    }

    //Отправка Http-запроса к реле
    public void executeHttpRequest(String targetURL) {
        HttpURLConnection connection = null;
        period = seekBar.getProgress();

        try {
            //запрос
            URL urlToWater = new URL(targetURL + "/cm?cmnd=Backlog%20Power%20on%3BDelay%20" + period * 10 + "%3BPower1%20off");
            URLConnection urlConnection = urlToWater.openConnection();

            //получение http ответа
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            urlConnection.getInputStream()));

            String inputLine = bufferedReader.readLine();
            if(inputLine.equals("{}")) {
                httpAnswerIsTrue = true;
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
            period = seekBar.getProgress();

        }

        @Override
        protected Void doInBackground(Void... params) {
            executeHttpRequest(url);
            try {
                Thread.sleep(period * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            if (!httpAnswerIsTrue) {
                Toast.makeText(WaterActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                return;
            }

            //Если растение полили, то дата последнего полива обновляется
            NotificationScheduler.showNotificationWatered(getBaseContext());
            plant.setLastMilWat(System.currentTimeMillis());
            db.update(plant);

        }
    }
}