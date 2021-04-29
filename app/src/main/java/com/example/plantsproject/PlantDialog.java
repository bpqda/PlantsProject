package com.example.plantsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class PlantDialog extends DialogFragment {
    Plant plant;
    Context context;
    PlantAdapter adapter;

    public PlantDialog(Plant plant, Context context, PlantAdapter adapter) {
        this.plant = plant;
        this.context = context;
        this.adapter = adapter;
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
        Button water = view.findViewById(R.id.water);
        Button feed = view.findViewById(R.id.feed);
        Button spray = view.findViewById(R.id.spray);
        DateDefiner def = new DateDefiner("dd/MM/yyyy HH:mm");


        name.setText(plant.getName());
        if(plant.getWatering()!=0) {
            watering.setText("Полито:  " + plant.getLastW());
        } else {
            watering.setText("Полив отключено");
        }
        if(plant.getFeeding()!=0) {
            feeding.setText("Удобрено:   " + plant.getLastF());
        } else {
            feeding.setText("Удобрение отключено");
        }
        if(plant.getSpraying()!=0) {
            spraying.setText("Опрыскано   " + plant.getLastS());
        } else {
            spraying.setText("Опрыскивание отключено");
        }

        water.setOnClickListener(v -> {
            plant.setLastW(def.defineDate());
            watering.setText("Полито:  " + plant.getLastW());
        });
        feed.setOnClickListener(v -> {
            plant.setLastF(def.defineDate());
            feeding.setText("Удобрено:   " + plant.getLastF());
        });
        spray.setOnClickListener(v -> {
            plant.setLastS(def.defineDate());
            spraying.setText("Опрыскано   " + plant.getLastS());
        });

        builder.setView(view);
        builder.setNeutralButton("Отмена", (dialog, which) -> {
            return;
        });
        builder.setNegativeButton("Больше информации", (dialog, which) -> {
            Intent i = new Intent(context, PlantInfoActivity.class);
            i.putExtra("plant", plant);
            startActivity(i);
        });

        builder.setCancelable(true);
        return builder.create();
    }
}
