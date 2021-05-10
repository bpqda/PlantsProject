package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity {
    Button edit, delete;
    TextView name, creationDate, watering, feeding, spraying, notes;
    Plant plant;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        name = findViewById(R.id.name);
        back = findViewById(R.id.back);
        creationDate = findViewById(R.id.creationDate);
        watering = findViewById(R.id.watering);
        feeding = findViewById(R.id.feeding);
        spraying = findViewById(R.id.spraying);
        notes = findViewById(R.id.notes);

        plant = (Plant) getIntent().getSerializableExtra("plant");
        name.setText(plant.getName());
        creationDate.setText(plant.getCreationDate());

        if (plant.getWatering() != 0)
            watering.setText(String.valueOf(plant.getWatering()));
        else
            watering.setText(R.string.disabled);

        if (plant.getFeeding() != 0)
            feeding.setText(String.valueOf(plant.getFeeding()));
         else
            feeding.setText(R.string.disabled);

        if (plant.getSpraying() != 0)
            spraying.setText(String.valueOf(plant.getSpraying()));
        else
            spraying.setText(R.string.disabled);

            notes.setText(plant.getNotes());

            edit.setOnClickListener(v -> {
                Intent j = new Intent(PlantInfoActivity.this, PlantCreation.class);
                j.putExtra("plant", plant);
                startActivity(j);
            });
            delete.setOnClickListener(v -> {
                DeleteDialog dialog = new DeleteDialog(this, plant,
                        R.string.sure2 + plant.getName() + " ?",
                        false, null);
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "dialog");
            });
            back.setOnClickListener(v -> {
                Intent i = new Intent(PlantInfoActivity.this, MainActivity.class);
                startActivity(i);
            });

        }
    }

