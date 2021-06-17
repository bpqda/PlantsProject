package com.example.plantsproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.plantsproject.R;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.entitys.Plant;

public class AutoWateringActivity extends AppCompatActivity {
    EditText ip, port;
    Button save;
    Plant plant;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_watering);

        ip = findViewById(R.id.ip);
        port = findViewById(R.id.port);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        DBPlants db = new DBPlants(this);
        plant = db.select( getIntent().getLongExtra("plant", -1));
        String url = plant.getUrl();
        System.out.println(url + "<---------------------");
        if (url != null && url.length() > 0) {
            url = url.replace("http://", "");
            String[] urlParts = url.split
                    (":");
            ip.setText(urlParts[0]);
            port.setText(urlParts[1]);
        }
        save.setOnClickListener(v -> {
            String portStr = port.getText().toString();

            if (portStr.equals("")) {
                portStr = "80";
            }
            if (!(ip.getText().toString().equals(""))) {
                plant.setUrl("http://" + ip.getText().toString().replace(" ", "") + ":" + portStr);
                db.update(plant);
            }
            onBackPressed();
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
