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

public class WaterActivity extends AppCompatActivity {

    ImageButton autoWater;
    ImageButton back;
    String url;
    SeekBar seekBar;
    TextView waterOb;
    LinearLayout linearLayout;
    EditText ssid;
    ProgressBar progressBar;
    String period;
    boolean answer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        autoWater = findViewById(R.id.autoWater);
        back = findViewById(R.id.back);
        seekBar = findViewById(R.id.seekBar);
        waterOb = findViewById(R.id.waterOb);
        linearLayout = findViewById(R.id.linearLayout);
        ssid = findViewById(R.id.editText);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        waterOb.setText(seekBar.getProgress() + " " + getString(R.string.sec));

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        String ssidStr = info.getSSID();
        if (!ssidStr.equals("<unknown ssid>")) {
            ssid.setText(info.getSSID().replace("\"", ""));
        }
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

        url = getIntent().getExtras().getString("url");
        period = String.valueOf(seekBar.getProgress() * 10);
        autoWater.setOnClickListener(v -> {

            MyTask task = new MyTask();
            task.execute();
            //Thread t = new Thread(() -> {
            //    executeHttpRequest(url, period, this);
            //});
            //t.start();

        });
        back.setOnClickListener(v -> onBackPressed());
    }

    public void executeHttpRequest(String targetURL, String period) {
        HttpURLConnection connection = null;

        try {

            URL yahoo = new URL(targetURL + "/cm?cmnd=Backlog%20Power%20on%3BDelay%20" + period + "%3BPower1%20off");
            URLConnection yc = yahoo.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            answer = false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
                executeHttpRequest(url, period);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            if(!answer) {
                Toast.makeText(WaterActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}