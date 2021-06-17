package com.example.plantsproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaterActivity extends AppCompatActivity {

    ImageButton autoWater;
    ImageButton back;
    String url;
    SeekBar seekBar;
    TextView waterOb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        autoWater = findViewById(R.id.autoWater);
        back = findViewById(R.id.back);
        back = findViewById(R.id.back);
        seekBar = findViewById(R.id.seekBar);
        waterOb = findViewById(R.id.waterOb);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waterOb.setText(seekBar.getProgress() + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        url = getIntent().getExtras().getString("url");

        autoWater.setOnClickListener(v -> {
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    String path = String.valueOf(seekBar.getProgress() * 10);
                    executeHttpRequest(url, path);

                }
            });
            t.start();
        });
        back.setOnClickListener(v -> onBackPressed());
    }
    public static void executeHttpRequest(String targetURL, String path) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL yahoo = new URL(targetURL + "/cm?cmnd=Backlog%20Power%20on%3BDelay%20" + path + "%3BPower1%20off");
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
            //return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}