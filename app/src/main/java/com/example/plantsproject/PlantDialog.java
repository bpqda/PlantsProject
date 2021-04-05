package com.example.plantsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PlantDialog extends DialogFragment {
    Plant plant;

    public PlantDialog(Plant plant) {
        this.plant = plant;
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

        name.setText(plant.getName());
        watering.setText(plant.getWatering() + "");
        feeding.setText(plant.getFeeding() + "");
        spraying.setText(plant.getSpraying() + "");


        builder.setView(view);

        builder.setPositiveButton("Редактировать", (dialog, which) -> {
            Intent i = new Intent(getActivity(), PlantCreation.class);
            i.putExtra("plant", plant);
            startActivity(i);
        });
        builder.setNeutralButton("Отмена", (dialog, which) -> {
            return;
        });
        builder.setNegativeButton("Удалить растение", (dialog, which) -> {

        });

        builder.setCancelable(true);
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }
}
