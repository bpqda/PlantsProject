package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlantCreation extends AppCompatActivity {
    TextView plantName;
    TextView sort;
    ImageButton back, create;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_creation);
        setTheme(R.style.AppTheme);

        back = findViewById(R.id.back);
        create = findViewById(R.id.create);
        plantName = findViewById(R.id.nameTxt);
        sort = findViewById(R.id.notes);
        wCB = findViewById(R.id.wateringCB);
        wSB = findViewById(R.id.wateringSB);
        wInf = findViewById(R.id.wateringInf);
        setAllListeners(wCB, wSB, wInf, ContextCompat.getColor(PlantCreation.this, R.color.blue));
        fCB = findViewById(R.id.feedingCB);
        fSB = findViewById(R.id.feedingSB);
        fInf = findViewById(R.id.feedingInf);
        setAllListeners(fCB, fSB, fInf, ContextCompat.getColor(PlantCreation.this, R.color.yellow));
        sCB = findViewById(R.id.sprayingCB);
        sSB = findViewById(R.id.sprayingSB);
        sInf = findViewById(R.id.sprayingInf);
        setAllListeners(sCB, sSB, sInf, ContextCompat.getColor(PlantCreation.this, R.color.colorMain));

        DBPlants plants = new DBPlants(this);
        add = false;

//Если MainActivity передала в intent растение для редактирования, то поля заполняются значениями
        if(getIntent().hasExtra("plant")) {
            Plant plant = (Plant) getIntent().getSerializableExtra("plant");
            plantName.setText(plant.getName());
            sort.setText(plant.getNotes());
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
                    plantName.getText().toString(), sort.getText().toString(),
                    wSB.getProgress(),
                    fSB.getProgress(),
                    sSB.getProgress());
            if(add==true) {
                plants.update(plant);
            } else {
                plants.insert(plant.getName(), plant.getNotes(), plant.getWatering(), plant.getFeeding(), plant.getSpraying());
            }
            NotificationScheduler.setReminder(this, MainActivity.class, plant);
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
