package com.example.plantsproject.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.notifications.DateDefiner;
import com.example.plantsproject.R;
import com.example.plantsproject.activities.InfoPlantActivity;
import com.example.plantsproject.activities.MainActivity;
import com.example.plantsproject.entitys.Plant;

public class PlantDialog extends DialogFragment {
    private Plant plant;
    private Context context;

    public PlantDialog(Plant plant, Context context) {
        this.plant = plant;
        this.context = context;
    }

    TextView name;
    TextView watering;
    TextView feeding;
    TextView spraying;
    TextView actions;
    Button water;
    Button feed;
    Button spray;
    LinearLayout btnLay;
    Space space1;
    Space space2;
    LinearLayout perLay;
    DBPlants plants;
    DateDefiner def;
    LinearLayout waterLay;
    LinearLayout feedLay;
    LinearLayout sprayLay;

    public void initPlant() {
        name.setText(plant.getName());
        if (plant.getWatering() != 0) {
            if (plant.getLastMilWat() != 0) {
                watering.setText(def.defineDate(plant.getLastMilWat()));
            } else {
                watering.setText(context.getResources().getString(R.string.no));
            }
        } else {
            perLay.removeView(waterLay);
            btnLay.removeView(water);
            btnLay.removeView(space1);
        }
        if (plant.getFeeding() != 0) {
            if (plant.getLastMilFeed() != 0) {
                feeding.setText(def.defineDate(plant.getLastMilFeed()));
            } else {
                feeding.setText(context.getResources().getString(R.string.no));
            }
        } else {
            perLay.removeView(feedLay);
            btnLay.removeView(feed);
            btnLay.removeView(space1);
        }
        if (plant.getSpraying() != 0) {
            if (plant.getLastMilSpray() != 0) {
                spraying.setText(def.defineDate(plant.getLastMilSpray()));
            } else {
                spraying.setText(context.getResources().getString(R.string.no));
            }
        } else {
            perLay.removeView(sprayLay);
            btnLay.removeView(spray);
            btnLay.removeView(space2);
        }
        if (plant.getWatering() == 0 && plant.getFeeding() == 0 && plant.getSpraying() == 0) {
            actions.setText(R.string.actions_are_disabled);
            btnLay.removeView(btnLay);
            perLay.removeView(perLay);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        def = new DateDefiner(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_plant, null);

        name = view.findViewById(R.id.name);
        watering = view.findViewById(R.id.watering);
        feeding = view.findViewById(R.id.feeding);
        spraying = view.findViewById(R.id.spraying);
        actions = view.findViewById(R.id.actions);

        water = view.findViewById(R.id.water);
        feed  = view.findViewById(R.id.feed);
        spray = view.findViewById(R.id.spray);

        btnLay = view.findViewById(R.id.btnLayout);
        space1 = view.findViewById(R.id.space1);
        space2 = view.findViewById(R.id.space2);
        perLay = view.findViewById(R.id.periodLay);

        plants = new DBPlants(context);

        waterLay = view.findViewById(R.id.wateringLay);
        feedLay = view.findViewById(R.id.feedingLay);
        sprayLay = view.findViewById(R.id.sprayingLay);
        initPlant();


        //Запись даты последнего полива
        water.setOnClickListener(v -> {
            water.setOnClickListener(v13 -> Toast.makeText(context, getString(R.string.already_watered), Toast.LENGTH_SHORT).show());

            plant.setLastMilWat(System.currentTimeMillis());
            plants.update(plant);

            watering.setText(def.defineDate(plant.getLastMilWat()));

        });

        //Запись даты последнего удобрения
        feed.setOnClickListener(v -> {
            feed.setOnClickListener(v12 -> Toast.makeText(context, getString(R.string.already_feeded), Toast.LENGTH_SHORT).show());
            plant.setLastMilFeed(System.currentTimeMillis());
            plants.update(plant);
            feeding.setText(def.defineDate(plant.getLastMilFeed()));

        });

        //Запись даты последнего опрыскивания
        spray.setOnClickListener(v -> {
            spray.setOnClickListener(v1 -> Toast.makeText(context, getString(R.string.already_sprayed), Toast.LENGTH_SHORT).show());
            plant.setLastMilSpray(System.currentTimeMillis());
            plants.update(plant);
            spraying.setText(def.defineDate(plant.getLastMilSpray()));
        });

        //Сохранение и обновление
        builder.setView(view);
        builder.setNeutralButton(getString(R.string.save), (dialog, which) -> {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        });

        //Переход к активности с информацией о растении
        builder.setNegativeButton(getString(R.string.more), (dialog, which) -> {
            Intent i = new Intent(context, InfoPlantActivity.class);
            i.putExtra("plant", plant);
            startActivity(i);
        });

        builder.setCancelable(false);
        return builder.create();

    }

    @Override
    public void onResume() {
        super.onResume();
        initPlant();
    }
}
