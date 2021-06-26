package com.example.plantsproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.plantsproject.R;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.entitys.Plant;

/*ВВОД IP АДРЕСА РЕЛЕ ДЛЯ АВТОПОЛИВА*/

public class AutoWateringActivity extends AppCompatActivity {
    private EditText ip, port;
    private Button save;
    private Plant plant;
    private ImageButton back;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_watering);

        ip = findViewById(R.id.ip);
        port = findViewById(R.id.port);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        DBPlants db = new DBPlants(this);

        //получение создающегося растения
        if(getIntent().getLongExtra("plantID", -1)>0) {
            plant = db.select(getIntent().getLongExtra("plantID", -1));
        } else if (getIntent().getSerializableExtra("plant")!=null){
            plant = (Plant) getIntent().getSerializableExtra("plant");
        }
        url = plant.getUrl();

        //если растение обновляется, то в поля ввода вводится старый ip
        if (url != null && url.length() > 0) {
            url = url.replace("http://", "");
            String[] urlParts = url.split
                    (":");
            ip.setText(urlParts[0]);
            port.setText(urlParts[1]);
        }

        //Кнопка сохранения
        save.setOnClickListener(v -> {
            String portStr = port.getText().toString();

            //Значение порта по умолчанию
            if (portStr.equals("")) {
                portStr = "80";
            }
            //Обновление поля url у растения
            if (!(ip.getText().toString().equals(""))) {
                plant.setUrl("http://" + ip.getText().toString().replace(" ", "") + ":" + portStr);
            }
            //Обновление растения в бд
            if(plant.getId()>0) {
                db.update(plant);
            }
            //Обратно к созданию растения
            else {
                Intent i = new Intent(AutoWateringActivity.this, CreationActivity.class);
                i.putExtra("plant", plant);
                startActivity(i);
            }
            onBackPressed();
        });
        back.setOnClickListener(v -> onBackPressed());

    }
}
