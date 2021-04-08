package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlantCreation extends AppCompatActivity {
    TextView plantName;
    TextView notes;
    ImageButton back, create, checkName;
    CheckBox wCB;
    SeekBar wSB;
    TextView wInf;
    CheckBox fCB;
    SeekBar fSB;
    TextView fInf;
    CheckBox sCB;
    SeekBar sSB;
    TextView sInf;
    long plantID;
    private boolean add;
    TextView tipsTxt;

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
               tipsTxt.setText(plant.toString());
               setPlantParameters(plant.getWatering(), wCB, wInf, wSB);
               setPlantParameters(plant.getFeeding(), fCB, fInf, fSB);
               setPlantParameters(plant.getWatering(), sCB, sInf, sSB);
           } else {
               tipsTxt.setText("Растение не найдено");
           }
        });

//Если MainActivity передала в intent растение для редактирования, то поля заполняются значениями
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

//кнопка создания растения
        create.setOnClickListener(v -> {
            Plant plant =new Plant(plantID,
                    plantName.getText().toString(), notes.getText().toString(),
                    wSB.getProgress(),
                    fSB.getProgress(),
                    sSB.getProgress());
            if(add==true) {
                plants.update(plant);
            } else {
                plants.insert(plant.getName(), plant.getNotes(), plant.getWatering(), plant.getFeeding(), plant.getSpraying());
            }
            NotificationScheduler.setReminder(this, MainActivity.class, plant.getWatering());
            finish();
        });

        //кнопка отмены
        back.setOnClickListener(v -> {
            finish();
        });
    }

    //Установка листенеров и цветов на SeekBar, TextView, CheckBox.
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
