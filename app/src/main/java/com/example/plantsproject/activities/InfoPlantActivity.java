package com.example.plantsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

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

    private Button edit, delete, autoWater;
    private TextView name, creationDate, watering, feeding, spraying, notes, url;
    private Plant plant;
    private ImageButton back;
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
        photo = findViewById(R.id.imageView2);
        url = findViewById(R.id.url);
        autoWater = findViewById(R.id.autoWater);

        //получение растения, выбранного в списке
        plant = (Plant) getIntent().getSerializableExtra("plant");
        urlStr = plant.getUrl();
        initPlant();

        //Кнопка для поиска растения в Википедии
        FloatingActionButton search = findViewById(R.id.fab);
        search.setOnClickListener(v -> {
            if (isOnline()) {
                Intent i = new Intent(InfoPlantActivity.this, WebInfoActivity.class);
                i.putExtra("plantID", plant.getId());
                startActivity(i);
            } else {
                Snackbar.make(v, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Кнопка для перехода в активность с автополивом
        autoWater.setOnClickListener(v -> {
            if (urlStr.equals("")) {
                Snackbar.make(v, getString(R.string.no_url), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();                return;
            }
            Intent i = new Intent(InfoPlantActivity.this, WaterActivity.class);
            i.putExtra("url", urlStr);
            i.putExtra("plantID", plant.getId());
            startActivity(i);
        });

        //Остальные кнопки
        edit.setOnClickListener(v -> {
            Intent j = new Intent(InfoPlantActivity.this, CreationActivity.class);
            j.putExtra("plant", plant);
            startActivity(j);
        });
        delete.setOnClickListener(v -> {
            DeleteDialog dialog = new DeleteDialog(this, plant,
                    getString(R.string.sure2) + " " + plant.getName() + "?",
                    false);
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");
        });
        back.setOnClickListener(v -> {
            Intent i = new Intent(InfoPlantActivity.this, MainActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initPlant();
    }

    //Проверяет, включен ли интернет на телефоне
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}


