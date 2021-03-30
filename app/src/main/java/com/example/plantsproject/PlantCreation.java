package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class PlantCreation extends AppCompatActivity {
    Button dialogBtn;
    TextView plantName;
    ImageButton back, create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_creation);
        setTheme(R.style.AppTheme);

        back = findViewById(R.id.back);
        create = findViewById(R.id.create);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlantCreation.this, MainActivity.class);
                startActivity(i);
            }
        });
        //кнопка создания растения
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plant plant = new Plant(0, plantName.getText().toString(), 3, 3, 3);
                Intent i = new Intent(PlantCreation.this, MainActivity.class);
                i.putExtra("plant", plant);
                //TODO: сделать watering, feeding и тд, создать растение
                startActivity(i);
            }
        });

    }
}
