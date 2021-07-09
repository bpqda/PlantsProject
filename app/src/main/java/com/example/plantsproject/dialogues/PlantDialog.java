package com.example.plantsproject.dialogues;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.plantsproject.activities.InfoPlantActivity;
import com.example.plantsproject.activities.MainActivity;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.notifications.DateDefiner;
import com.example.plantsproject.R;
import com.example.plantsproject.entitys.Plant;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class PlantDialog extends BottomSheetDialogFragment {

    private Plant plant;
    private Context context;

    public PlantDialog(Plant plant, Context context) {
        this.plant = plant;
        this.context = context;
    }

    private BottomSheetDialog bottomSheetDialog;
    private TextView name, watering, feeding, spraying, actions;
    private Button water, feed, spray;
    private LinearLayout btnLay;
    private Space space1, space2;
    private DateDefiner def;
    private LinearLayout waterLay, feedLay, sprayLay, perLay;
    private DBPlants plants;

    private void initPlant() {
        name.setText(plant.getName());
        if (plant.getWatering() != 0) {

            if (plant.getLastMilWat() != 0)
                watering.setText(def.defineDate(plant.getLastMilWat()));
            else
                watering.setText(context.getResources().getString(R.string.no));

        } else
            removeButton(waterLay, water, space1);

        if (plant.getFeeding() != 0) {

            if (plant.getLastMilFeed() != 0)
                feeding.setText(def.defineDate(plant.getLastMilFeed()));
            else
                feeding.setText(context.getResources().getString(R.string.no));

        } else
            removeButton(feedLay, feed, space1);

        if (plant.getSpraying() != 0) {

            if (plant.getLastMilSpray() != 0)
                spraying.setText(def.defineDate(plant.getLastMilSpray()));
            else
                spraying.setText(context.getResources().getString(R.string.no));

        } else
            removeButton(sprayLay, spray, space2);

        if (plant.getWatering() == 0 && plant.getFeeding() == 0 && plant.getSpraying() == 0) {
            actions.setText(R.string.actions_are_disabled);
            btnLay.removeView(btnLay);
            perLay.removeView(perLay);
        }
    }

    private void removeButton(LinearLayout lay, Button btn, Space space) {
        perLay.removeView(lay);
        btnLay.removeView(btn);
        btnLay.removeView(space);

    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        plants = new DBPlants(context);
        def = new DateDefiner(context);

        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.dialog_plant);

        name = bottomSheetDialog.findViewById(R.id.name);
        watering = bottomSheetDialog.findViewById(R.id.watering);
        feeding = bottomSheetDialog.findViewById(R.id.feeding);
        spraying = bottomSheetDialog.findViewById(R.id.spraying);
        actions = bottomSheetDialog.findViewById(R.id.actions);

        water = bottomSheetDialog.findViewById(R.id.water);
        feed = bottomSheetDialog.findViewById(R.id.feed);
        spray = bottomSheetDialog.findViewById(R.id.spray);

        btnLay = bottomSheetDialog.findViewById(R.id.btnLayout);
        space1 = bottomSheetDialog.findViewById(R.id.space1);
        space2 = bottomSheetDialog.findViewById(R.id.space2);
        perLay = bottomSheetDialog.findViewById(R.id.periodLay);

        waterLay = bottomSheetDialog.findViewById(R.id.wateringLay);
        feedLay = bottomSheetDialog.findViewById(R.id.feedingLay);
        sprayLay = bottomSheetDialog.findViewById(R.id.sprayingLay);
        initPlant();

        ImageButton close = bottomSheetDialog.findViewById(R.id.imageButton);
        close.setOnClickListener(v -> bottomSheetDialog.dismiss());

        Button moreInfoBtn = bottomSheetDialog.findViewById(R.id.more);


        //Запись даты последнего полива
        water.setOnClickListener(v -> {
            plant.setLastMilWat(System.currentTimeMillis());
            setNewDate(water, watering, R.string.already_watered);
        });

        //Запись даты последнего удобрения
        feed.setOnClickListener(v -> {
            plant.setLastMilFeed(System.currentTimeMillis());
            setNewDate(feed, feeding, R.string.already_feeded);
        });

        //Запись даты последнего опрыскивания
        spray.setOnClickListener(v -> {
            plant.setLastMilSpray(System.currentTimeMillis());
            setNewDate(spray, spraying, R.string.sprayed);
        });

        moreInfoBtn.setOnClickListener(v -> {
            Intent i = new Intent(context, InfoPlantActivity.class);
            i.putExtra("plantID", plant.getId());
            context.startActivity(i);
        });
        bottomSheetDialog.setOnDismissListener(dialogInterface ->
            ((MainActivity) context).initList());

    }
    private void setNewDate(Button btn, TextView textView, int string ) {
        btn.setOnClickListener(v1 -> Toast.makeText(context, context.getString(string), Toast.LENGTH_SHORT).show());
        plants.update(plant);
        textView.setText(def.defineDate(plant.getLastMilSpray()));
    }

    //Показ диалога
    public void showDialog() {
        setupDialog(bottomSheetDialog, R.style.MyBottomDialogStyle);
        bottomSheetDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        initPlant();
    }

    @Override
    public int getTheme() {
        return R.style.MyBottomDialogStyle;
    }

}
