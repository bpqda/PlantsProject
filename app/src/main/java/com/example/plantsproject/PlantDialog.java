package com.example.plantsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PlantDialog extends DialogFragment {
    Plant plant;
    Context context;
   PlantAdapter adapter;

    public PlantDialog(Plant plant) {
        this.plant = plant;
    }
    public PlantDialog(Plant plant, Context context, PlantAdapter adapter) {
        this(plant);
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
        TextView notes = view.findViewById(R.id.notes);

        name.setText(plant.getName());
        if(plant.getWatering()!=0) {
            watering.setText("Периодичность полива:   " + plant.getWatering()+ " дней");
        } else {
            watering.setText("Периодичность полива:   -");
        }
        if(plant.getFeeding()!=0) {
            feeding.setText("Периодичность удобрения:   " + plant.getFeeding()+ " дней");
        } else {
            feeding.setText("Периодичность удобрения:   -");
        }
        if(plant.getSpraying()!=0) {
            spraying.setText("Периодичность опрыскивания:   " + plant.getSpraying()+ " дней");
        } else {
            spraying.setText("Периодичность опрыскивания:   -");
        }
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
            DBPlants plants= new DBPlants(context);
            adapter.setArrayMyData(plants.selectAll());
            adapter.notifyDataSetChanged();
        });

        builder.setCancelable(true);
        return builder.create();
    }
}
