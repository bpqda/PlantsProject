package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PlantCreation extends AppCompatActivity {
    TextView plantName, notes, wInf, fInf, sInf, tipsTxt;
    ImageButton back, create, checkName;
    CheckBox wCB, fCB, sCB;
    SeekBar wSB, fSB, sSB;
    long plantID;
    private boolean add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_creation);
        setTheme(R.style.AppTheme);

        back = findViewById(R.id.back);
        create = findViewById(R.id.create);
        plantName = findViewById(R.id.nameTxt);
        notes = findViewById(R.id.notes);
        checkName = findViewById(R.id.checkName);
        tipsTxt = findViewById(R.id.tips);
        wCB = findViewById(R.id.wateringCB);
        wSB = findViewById(R.id.wateringSB);
        wInf = findViewById(R.id.wateringInf);
        setAllListeners(wCB, wSB, wInf, ContextCompat.getColor(PlantCreation.this, R.color.blue));
        wCB.setChecked(false);
        fCB = findViewById(R.id.feedingCB);
        fSB = findViewById(R.id.feedingSB);
        fInf = findViewById(R.id.feedingInf);
        setAllListeners(fCB, fSB, fInf, ContextCompat.getColor(PlantCreation.this, R.color.yellow));
        fCB.setChecked(false);
        sCB = findViewById(R.id.sprayingCB);
        sSB = findViewById(R.id.sprayingSB);
        sInf = findViewById(R.id.sprayingInf);
        setAllListeners(sCB, sSB, sInf, ContextCompat.getColor(PlantCreation.this, R.color.colorMain));
        sCB.setChecked(false);

        DBPlants plants = new DBPlants(this);
        DBTips tips = new DBTips(this);

        checkName.setOnClickListener(view -> {
           Plant plant = tips.findString(plantName.getText().toString());
           if(plant!=null) {
               tipsTxt.setText("Рекомендуемый уход установлен.\nВы можете его отредактировать");
               setPlantParameters(plant.getWatering(), wCB, wInf, wSB);
               setPlantParameters(plant.getFeeding(), fCB, fInf, fSB);
               setPlantParameters(plant.getWatering(), sCB, sInf, sSB);
               notes.setText(plant.getNotes());
           } else {
               wCB.setChecked(false);
               fCB.setChecked(false);
               sCB.setChecked(false);
               wSB.setProgress(0);
               fSB.setProgress(0);
               sSB.setProgress(0);
               notes.setText("");
               tipsTxt.setText("Растение не найдено");
           }
        });

        if(getIntent().hasExtra("plant")) {
            Plant plant = (Plant) getIntent().getSerializableExtra("plant");
            plantName.setText(plant.getName());
            notes.setText(plant.getNotes());
            setPlantParameters(plant.getWatering(), wCB, wInf, wSB);
            setPlantParameters(plant.getFeeding(), fCB, fInf, fSB);
            setPlantParameters(plant.getSpraying(), sCB, sInf, sSB);
            plantID = plant.getId();
            add = true;

        } else {
            plantID = -1;
        }

        create.setOnClickListener(v -> {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate= formatter.format(date);
            Plant plant =new Plant(plantID,
                    plantName.getText().toString(), notes.getText().toString(),
                    wSB.getProgress(),
                    fSB.getProgress(),
                    sSB.getProgress(), strDate);
            if(add)
                plants.update(plant);
             else
                plants.insert(plant.getName(), plant.getNotes(), plant.getWatering(), plant.getFeeding(), plant.getSpraying(), strDate);

            if(plant.getWatering()!=0)
                {NotificationScheduler.setReminder(this, AlarmReceiver.class, plant.getWatering(), plant);}
            else {}
            //if(plant.getSpraying()!=0)
            //{NotificationScheduler.setReminder(this, AlarmReceiver.class, plant.getFeeding());}
            //else {}
            //if(plant.getSpraying()!=0)
            //{NotificationScheduler.setReminder(this, AlarmReceiver.class, plant.getSpraying());}
            //else {}

            finish();
        });

        back.setOnClickListener(v -> {
            finish();
        });
    }

    private void setAllListeners(CheckBox cb, final SeekBar sb, final TextView txt, final int color) {

       sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    txt.setText(String.valueOf(seekBar.getProgress()));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
        cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                sb.setEnabled(true);
                txt.setText(String.valueOf(sb.getProgress()));
                sb.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            } else {
                sb.getProgressDrawable().setColorFilter(ContextCompat.getColor(PlantCreation.this, R.color.gray), PorterDuff.Mode.MULTIPLY);
                sb.setProgress(0);
                txt.setText("---");
                sb.setEnabled(false);
            }
        });
    }

    private void setPlantParameters(int parameter, CheckBox cb, TextView tv, SeekBar sb) {
        if(parameter!=0){
            cb.setChecked(true);
            tv.setText(parameter+"");
            sb.setProgress(parameter);
        } else {
            cb.setChecked(false);
            tv.setText("---");
        }
    }
    }


