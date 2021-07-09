package com.example.plantsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.dialogues.DeleteDialog;
import com.example.plantsproject.R;
import com.example.plantsproject.entitys.Plant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/*ПОДРОБНАЯ ИНФОРМАЦИЯ О РАСТЕНИИ*/

public class InfoPlantActivity extends AppCompatActivity {

    private TextView name, creationDate, watering, feeding, spraying, notes, url;
    private Plant plant;
    private ImageView photo;
    private String urlStr;

    public void initPlant() {
        photo.setImageResource(plant.getPhoto());
        if (urlStr != null && !urlStr.equals(""))
            url.setText(urlStr);
        else
            url.setText(getString(R.string.disabled));
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
    }
    @Override
    protected void onResume() {
        super.onResume();
        initPlant();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_plant);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        name = findViewById(R.id.name);
        creationDate = findViewById(R.id.creationDate);
        watering = findViewById(R.id.watering);
        feeding = findViewById(R.id.feeding);
        spraying = findViewById(R.id.spraying);
        notes = findViewById(R.id.notes);
        photo = findViewById(R.id.imageView2);
        url = findViewById(R.id.url);
        Button autoWater = findViewById(R.id.autoWater);
        DBPlants db = new DBPlants(this);

        //получение растения, выбранного в списке
        long id = getIntent().getLongExtra("plantID", -1);
        plant = db.select(id);
        urlStr = plant.getUrl();
        initPlant();

        //Кнопка для поиска растения в Википедии
        FloatingActionButton search = findViewById(R.id.fab);
        search.setOnClickListener(v -> {
            if (isOnline()) {
                Intent i = new Intent(InfoPlantActivity.this, WebInfoActivity.class);
                i.putExtra("plantName", plant.getName());
                startActivity(i);
            } else {
                Snackbar.make(v, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.no_internet), null).show();
            }
        });

        //В активность с автополивом
        autoWater.setOnClickListener(v -> {
            if (urlStr.equals("")) {
                Snackbar.make(v, getString(R.string.no_url), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.no_url), null).show();
                return;
            }
            Intent i = new Intent(InfoPlantActivity.this, WaterActivity.class);
            i.putExtra("plantID", plant.getId());
            startActivity(i);
        });

        Button edit = findViewById(R.id.edit);
        edit.setOnClickListener(v -> {
            Intent j = new Intent(InfoPlantActivity.this, CreationActivity.class);
            j.putExtra("plantID", plant.getId());
            startActivity(j);
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            DeleteDialog dialog = new DeleteDialog(this, plant.getId(),
                    getString(R.string.sure2) + " " + plant.getName() + "?");
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");
        });

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

    }

    //Проверяет, включен ли интернет на телефоне
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}


