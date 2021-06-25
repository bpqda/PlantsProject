package com.example.plantsproject.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.R;
import com.example.plantsproject.activities.MainActivity;
import com.example.plantsproject.entitys.Plant;
import com.example.plantsproject.notifications.AlarmReceiver;
import com.example.plantsproject.notifications.NotificationScheduler;

public class DeleteDialog extends DialogFragment {
    private Context context;
    private Plant plant;
    private String content;
    private boolean deleteAll;
    //private PlantAdapter adapter;

    public DeleteDialog(Context context, Plant plant, String content, boolean deleteAll) {
        this.context = context;
        this.plant = plant;
        this.content = content;
        this.deleteAll = deleteAll;
        //this.adapter = adapter;
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
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                    } else {
                        plantsDB.delete(plant.getId());
                        //NotificationScheduler.cancelReminder(context, AlarmReceiver.class);
                        Intent i = new Intent(context, MainActivity.class);
                        startActivity(i);
                    }

                });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());
        return builder.create();
    }
}