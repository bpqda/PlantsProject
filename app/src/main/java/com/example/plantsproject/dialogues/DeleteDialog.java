package com.example.plantsproject.dialogues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.plantsproject.activities.InfoPlantActivity;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.R;
import com.example.plantsproject.activities.MainActivity;
import com.example.plantsproject.entitys.Plant;
import com.example.plantsproject.notifications.AlarmReceiver;
import com.example.plantsproject.notifications.NotificationScheduler;

/*ДИАЛОГОВОЕ ОКНО ДЛЯ УДАЛЕНИЯ РАСТЕНИЙ*/

public class DeleteDialog extends DialogFragment {
    private Context context;
    private long plantID;
    private String content;

    public DeleteDialog(Context context, long plantID, String content) {
        this.context = context;
        this.plantID = plantID;
        this.content = content;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle(R.string.delete)
                .setMessage(content)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    DBPlants plantsDB = new DBPlants(context);
                    //Удаление всех растений
                    if(plantID<0) {
                        plantsDB.deleteAll();
                        ((MainActivity)getActivity()).initList();
                    }
                    //Удаление одного растения
                    else {
                        plantsDB.delete(plantID);
                        getActivity().onBackPressed();
                    }

                });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());

        return builder.create();
    }
}
