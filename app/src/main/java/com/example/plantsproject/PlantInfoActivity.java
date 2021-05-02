package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity {
    Button edit, delete;
    TextView name, creationDate;
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

        plant = (Plant) getIntent().getSerializableExtra("plant");
                name.setText(plant.getName());
                creationDate.setText(plant.getCreationDate());

        edit.setOnClickListener(v -> {
            Intent j = new Intent(PlantInfoActivity.this, PlantCreation.class);
            j.putExtra("plant", plant);
            startActivity(j);
        });
        delete.setOnClickListener(v -> {
            DeleteDialog dialog = new DeleteDialog(this, plant,
                    "Вы уверены что хотите удалить растение "+ plant.getName() + "?",
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
