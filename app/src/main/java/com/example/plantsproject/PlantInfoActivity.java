package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity {
    Button edit, delete;
    TextView name;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        name = findViewById(R.id.name);

        Intent i = new Intent();
        Plant plant = (Plant) getIntent().getSerializableExtra("plant");
                name.setText(plant.getName());

        edit.setOnClickListener(v -> {
            Intent j = new Intent(PlantInfoActivity.this, PlantCreation.class);
            j.putExtra("plant", plant);
            startActivity(j);
        });
        delete.setOnClickListener(v -> {
            DBPlants plants = new DBPlants(this);
            plants.delete(plant.getId());
            Intent k = new Intent(PlantInfoActivity.this, MainActivity.class);
            startActivity(k);
        });

    }
}
