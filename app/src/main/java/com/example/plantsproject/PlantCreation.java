package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlantCreation extends AppCompatActivity {
    Button backBtn;
    Button createBtn;
    TextView plantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_creation);
        backBtn = findViewById(R.id.backBtn);
        createBtn = findViewById(R.id.createBtn);
        plantName = findViewById(R.id.plantName);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlantCreation.this, MainActivity.class);
                startActivity(i);
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlantCreation.this, MainActivity.class);
                i.putExtra("plantName", plantName.getText().toString());
                //TODO: сделать watering, feeding и тд.
                startActivity(i);
            }
        });
    }
}
