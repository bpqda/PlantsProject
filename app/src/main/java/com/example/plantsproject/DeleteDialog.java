package com.example.plantsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteDialog extends DialogFragment {
    private Context context;
    private Plant plant;
    private String content;
    private boolean deleteAll;
    private PlantAdapter adapter;

    DeleteDialog(Context context, Plant plant, String content, boolean deleteAll, PlantAdapter adapter) {
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
        builder.setTitle(R.string.delete)
                .setMessage(content)
                .setPositiveButton("Да", (dialog, id) -> {
                    DBPlants plantsDB = new DBPlants(context);
                    if(deleteAll) {
                        plantsDB.deleteAll();
                        adapter.setArrayMyData(plantsDB.selectAll());
                        adapter.notifyDataSetChanged();
                    } else {
                        plantsDB.delete(plant.getId());
                        NotificationScheduler.cancelReminder(context, AlarmReceiver.class);
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                    }

                });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());
        return builder.create();
    }
}
