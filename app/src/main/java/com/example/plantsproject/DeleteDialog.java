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
    boolean deleteAll;
    PlantAdapter adapter;

    public DeleteDialog(Context context, Plant plant, String content, boolean deleteAll, PlantAdapter adapter) {
        this.context = context;
        this.plant = plant;
        this.content = content;
        this.deleteAll = deleteAll;
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

                    if(deleteAll) {
                        plants.deleteAll();
                        Intent bool = new Intent(context, MainActivity.class);
                        bool.putExtra("deletedAll", true);
                        startActivity(bool);
                    } else {
                        plants.delete(plant.getId());
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                    }



                });
        builder.setNegativeButton("Нет", (dialog, which) -> {
            dialog.cancel();
        });
        return builder.create();
    }
}
