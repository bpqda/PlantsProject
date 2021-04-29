package com.example.plantsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteDialog extends DialogFragment {
    Context context;
    Plant plant;
    String content;
    int actionID;
    PlantAdapter adapter;

    public DeleteDialog(Context context, Plant plant, String content, int actionID, PlantAdapter adapter) {
        this.context = context;
        this.plant = plant;
        this.content = content;
        this.actionID = actionID;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Удаление растения")
                .setMessage(content)
                .setPositiveButton("Да", (dialog, id) -> {
                    DBPlants plants = new DBPlants(context);

                    switch (actionID) {
                        case 100:
                            plants.deleteAll();
                            adapter.setArrayMyData(plants.selectAll());
                            adapter.notifyDataSetChanged();
                            return;
                        case 200:
                            plants.delete(plant.getId());
                            Intent i = new Intent(context, MainActivity.class);
                            startActivity(i);
                            return;
                    }

                });
        builder.setNegativeButton("Нет", (dialog, which) -> {
            dialog.cancel();
        });
        return builder.create();
    }
}
