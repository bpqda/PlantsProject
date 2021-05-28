package com.example.plantsproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class InfoPlantActivity extends AppCompatActivity {

    Button edit, delete;
    TextView name, creationDate, watering, feeding, spraying, notes;
    Plant plant;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_plant);
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

       FloatingActionButton search = findViewById(R.id.fab);
       search.setOnClickListener(v -> {
           if (isOnline()) {
               Intent i = new Intent(InfoPlantActivity.this, WebInfoActivity.class);
               i.putExtra("plant", plant);
               startActivity(i);
           } else {
               Snackbar.make(v,getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
               //Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
           }
       });


        name.setText(plant.getName());
        creationDate.setText(plant.getCreationDate());

        if (plant.getWatering() != 0)
            watering.setText(String.valueOf(plant.getWatering()));
        else
            watering.setText(getText(R.string.disabled));

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
            Intent j = new Intent(InfoPlantActivity.this, CreationActivity.class);
            j.putExtra("plant", plant);
            startActivity(j);
        });
        delete.setOnClickListener(v -> {
            DeleteDialog dialog = new DeleteDialog(this, plant,
                    getString(R.string.sure2) + plant.getName() + " ?",
                    false, null);
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");
        });
        back.setOnClickListener(v -> {
            Intent i = new Intent(InfoPlantActivity.this, MainActivity.class);
            startActivity(i);
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}


