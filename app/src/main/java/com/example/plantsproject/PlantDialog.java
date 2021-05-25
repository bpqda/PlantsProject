package com.example.plantsproject;

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

public class PlantDialog extends DialogFragment {
    private Plant plant;
    private Context context;

    PlantDialog(Plant plant, Context context) {
        this.plant = plant;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

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

        DateDefiner def = new DateDefiner("dd/MM/yyyy\t\t\t\tHH:mm");
        DBPlants plants = new DBPlants(context);

        LinearLayout waterLay = view.findViewById(R.id.wateringLay);
        LinearLayout feedLay = view.findViewById(R.id.feedingLay);
        LinearLayout sprayLay = view.findViewById(R.id.sprayingLay);

        name.setText(plant.getName());
        if(plant.getWatering()!=0) {
            watering.setText(plant.getLastW());
        } else {
            perLay.removeView(waterLay);
            btnLay.removeView(water);
            btnLay.removeView(space1);
        }
        if(plant.getFeeding()!=0) {
            feeding.setText(plant.getLastF());
        } else {
            perLay.removeView(feedLay);
            btnLay.removeView(feed);
            btnLay.removeView(space1);
        }
        if(plant.getSpraying()!=0) {
            spraying.setText(plant.getLastS());
        } else {
            perLay.removeView(sprayLay);
            btnLay.removeView(spray);
            btnLay.removeView(space2);
        }
        if(plant.getWatering()==0&&plant.getFeeding()==0&&plant.getSpraying()==0) {
            actions.setText(R.string.actions_are_disabled);
            btnLay.removeView(btnLay);
            perLay.removeView(perLay);
        }

        water.setOnClickListener(v -> {
            water.setOnClickListener(v13 -> Toast.makeText(context, "Уже полито", Toast.LENGTH_SHORT).show());

            plant.setLastW(def.defineDate());
            plant.setLastMilWat(System.currentTimeMillis());
            plants.update(plant);
            watering.setText(plant.getLastW());

        });
        feed.setOnClickListener(v -> {
            feed.setOnClickListener(v12 -> Toast.makeText(context, "Уже удобрено", Toast.LENGTH_SHORT).show());

            plant.setLastF(def.defineDate());
            plant.setLastMilFeed(System.currentTimeMillis());
            plants.update(plant);
            feeding.setText(plant.getLastF());
        });
        spray.setOnClickListener(v -> {
            spray.setOnClickListener(v1 -> Toast.makeText(context, "Уже опрыскано", Toast.LENGTH_SHORT).show());

            plant.setLastS(def.defineDate());
            plant.setLastMilSpray(System.currentTimeMillis());
            plants.update(plant);
            spraying.setText(plant.getLastS());
        });

        builder.setView(view);
        builder.setNeutralButton("Сохранить", (dialog, which) -> {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        });
        builder.setNegativeButton("Больше информации", (dialog, which) -> {
            Intent i = new Intent(context, InfoPlantActivity.class);
            i.putExtra("plant", plant);
            startActivity(i);
        });

        builder.setCancelable(false);
        return builder.create();

    }

}
