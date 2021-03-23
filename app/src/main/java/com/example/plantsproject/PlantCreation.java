package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class PlantCreation extends AppCompatActivity {
    Button backBtn, createBtn, dialogBtn;
    TextView plantName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_creation);

        backBtn = findViewById(R.id.backBtn);
        createBtn = findViewById(R.id.createBtn);
        plantName = findViewById(R.id.plantName);
        dialogBtn = findViewById(R.id.dialogBtn);

// Выбор периодичности полива
      dialogBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
// Listener для TimePicker
              TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

                  @Override
                  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //TODO: сохранить выбранное время
                  }
              };
// Создание диалогового окна
              TimePickerDialog timePickerDialog = new TimePickerDialog(PlantCreation.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                      timeSetListener, 0, 0, true);

// Показ окна
              timePickerDialog.show();
          }
      });

      //кнопка отмены
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlantCreation.this, MainActivity.class);
                startActivity(i);
            }
        });
        //кнопка создания растения
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plant plant = new Plant(plantName.getText().toString(), 3, 3, 3);
                Intent i = new Intent("plant", plant);
                i.putExtra("plantName", plantName.getText().toString());
                //TODO: сделать watering, feeding и тд, создать растение
                startActivity(i);
            }
        });

    }
}
