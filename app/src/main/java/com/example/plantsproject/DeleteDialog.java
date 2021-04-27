package com.example.plantsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteDialog extends DialogFragment {
    Context context;
    Plant plant;

    public DeleteDialog(Context context, Plant plant) {
        this.context = context;
        this.plant = plant;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);

        //builder.set
        builder.setCancelable(true);
        return builder.create();
    }
}
