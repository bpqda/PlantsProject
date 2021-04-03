package com.example.plantsproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlantCreation extends AppCompatActivity {
    EditText plantName;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_creation);
        setTheme(R.style.AppTheme);

        back = findViewById(R.id.back);
        create = findViewById(R.id.create);
        plantName = findViewById(R.id.nameTxt);

        wCB = findViewById(R.id.wCB);
        wSB = findViewById(R.id.wSB);
        wInf = findViewById(R.id.wInf);
        setAllListeners(wCB, wSB, wInf, ContextCompat.getColor(PlantCreation.this, R.color.blue));
        fCB = findViewById(R.id.feedingCB);
        fSB = findViewById(R.id.feedingSB);
        fInf = findViewById(R.id.feedingInf);
        setAllListeners(fCB, fSB, fInf, ContextCompat.getColor(PlantCreation.this, R.color.blue));
        sCB = findViewById(R.id.sprayingCB);
        sSB = findViewById(R.id.sprayingSB);
        sInf = findViewById(R.id.sprayingInf);
        setAllListeners(sCB, sSB, sInf, ContextCompat.getColor(PlantCreation.this, R.color.blue));


        //wSB.getProgressDrawable().setColorFilter(ContextCompat.getColor(PlantCreation.this, R.color.gray), PorterDuff.Mode.MULTIPLY);
        //wInf.setText("---");




        setAllListeners(wCB, wSB, wInf, ContextCompat.getColor(PlantCreation.this, R.color.blue));

     wSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                wInf.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

      //кнопка отмены
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlantCreation.this, MainActivity.class);
                startActivity(i);
            }
        });
        //кнопка создания растения
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plant plant = new Plant(plantName.getText().toString(), wSB.getProgress(), fSB.getProgress(), sSB.getProgress());
                Intent i = new Intent(PlantCreation.this, MainActivity.class);
                //i.putExtra("plant", plant);
                //i.putExtra("name", plantName.getText().toString());
                //i.putExtra("watering", wSB.getProgress());
                //i.putExtra("feeding", fSB.getProgress());
                //i.putExtra("spraying", sSB.getProgress());
                startActivity(i);
            }
        });

    }
    public void setAllListeners(CheckBox cb, final SeekBar sb, final TextView txt, final int color) {

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

        cb.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    txt.setText(String.valueOf(sb.getProgress()));

                    sb.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                } else {
                    sb.getProgressDrawable().setColorFilter(ContextCompat.getColor(PlantCreation.this, R.color.gray), PorterDuff.Mode.MULTIPLY);
                    txt.setText("---");
                }
            }
        });
    }



}
