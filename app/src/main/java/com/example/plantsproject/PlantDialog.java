package com.example.plantsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
    Context context;

    public PlantDialog(Plant plant) {
        this.plant = plant;
    }
    public PlantDialog(Plant plant, Context context) {
        this(plant);
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
        TextView notes = view.findViewById(R.id.notes);

        name.setText(plant.getName());
        watering.setText("Переодичность полива:   "+plant.getWatering());
        feeding.setText("Переодичность удобрения   "+plant.getFeeding());
        spraying.setText("Переодичность опрыскивания  "+plant.getSpraying());
        notes.setText(plant.getNotes());


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
            DBPlants db = new DBPlants(context);
            db.delete(plant.getId());
        });

        builder.setCancelable(true);
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }
}
