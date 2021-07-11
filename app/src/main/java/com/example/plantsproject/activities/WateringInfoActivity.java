package com.example.plantsproject.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.entitys.Plant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.example.plantsproject.R;

public class WateringInfoActivity extends AppCompatActivity {
    private Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watering_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> onBackPressed());

        DBPlants db = new DBPlants(this);

        //получение создающегося растения
        if (getIntent().getLongExtra("plantID", -1) > 0) {
            plant = db.select(getIntent().getLongExtra("plantID", -1));
        } else if (getIntent().getSerializableExtra("plant") != null) {
            plant = (Plant) getIntent().getSerializableExtra("plant");
        }

        String url = plant.getUrl();
        if (url == null) {
            url = "";
        }

        //если растение обновляется, то в поля ввода вводится старый ip
        EditText ip = findViewById(R.id.ip);
        EditText port = findViewById(R.id.port);
        if (url.length() > 0) {
            url = url.replace("http://", "");
            String[] urlParts = url.split
                    (":");
            ip.setText(urlParts[0]);
            port.setText(urlParts[1]);
        }
        SeekBar sb = findViewById(R.id.seekBar);
        sb.setProgress(plant.getDefaultAutoWatering());

        //Кнопка сохранения
        Button save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            String portStr = port.getText().toString();

            //Значение порта по умолчанию
            if (portStr.equals("")) {
                portStr = "80";
            }

            //Обновление поля url у нового растения
            if (!(ip.getText().toString().equals(""))) {
                plant.setUrl("http://" + ip.getText().toString().replace(" ", "") + ":" + portStr);
                plant.setDefaultAutoWatering(sb.getProgress());

                //Обновление растения в бд
                if (plant.getId() > 0) {
                    db.update(plant);
                    onBackPressed();
                    return;
                }
                Intent i = new Intent(WateringInfoActivity.this, CreationActivity.class);
                i.putExtra("plant", plant);
                startActivity(i);
            }
            finish();
        });
    }
}
