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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DateDefiner def = new DateDefiner(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_plant, null);

        TextView name = view.findViewById(R.id.name);
        TextView watering = view.findViewById(R.id.watering);
        TextView feeding = view.findViewById(R.id.feeding);
        TextView spraying = view.findViewById(R.id.spraying);
        TextView actions = view.findViewById(R.id.actions);

        Button water = view.findViewById(R.id.water);
        Button feed = view.findViewById(R.id.feed);
        Button spray = view.findViewById(R.id.spray);

        LinearLayout btnLay = view.findViewById(R.id.btnLayout);
        Space space1 = view.findViewById(R.id.space1);
        Space space2 = view.findViewById(R.id.space2);
        LinearLayout perLay = view.findViewById(R.id.periodLay);

        DBPlants plants = new DBPlants(context);

        LinearLayout waterLay = view.findViewById(R.id.wateringLay);
        LinearLayout feedLay = view.findViewById(R.id.feedingLay);
        LinearLayout sprayLay = view.findViewById(R.id.sprayingLay);

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

        water.setOnClickListener(v -> {
            water.setOnClickListener(v13 -> Toast.makeText(context, getString(R.string.already_watered), Toast.LENGTH_SHORT).show());

            plant.setLastMilWat(System.currentTimeMillis());
            plants.update(plant);

            watering.setText(def.defineDate(plant.getLastMilWat()));

        });
        feed.setOnClickListener(v -> {
            feed.setOnClickListener(v12 -> Toast.makeText(context, getString(R.string.already_feeded), Toast.LENGTH_SHORT).show());

            plant.setLastMilFeed(System.currentTimeMillis());
            plants.update(plant);

            feeding.setText(def.defineDate(plant.getLastMilFeed()));

        });
        spray.setOnClickListener(v -> {
            spray.setOnClickListener(v1 -> Toast.makeText(context, getString(R.string.already_sprayed), Toast.LENGTH_SHORT).show());

            plant.setLastMilSpray(System.currentTimeMillis());
            plants.update(plant);
            spraying.setText(def.defineDate(plant.getLastMilSpray()));
        });

        builder.setView(view);
        builder.setNeutralButton(getString(R.string.save), (dialog, which) -> {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        });
        builder.setNegativeButton(getString(R.string.more), (dialog, which) -> {
            Intent i = new Intent(context, InfoPlantActivity.class);
            i.putExtra("plant", plant);
            startActivity(i);
        });

        builder.setCancelable(false);
        return builder.create();

    }

}