package com.example.plantsproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreationActivity extends AppCompatActivity {

    TextView plantName, notes, wInf, fInf, sInf, tipsTxt;
    ImageButton checkName, back;
    CheckBox wCB, fCB, sCB;
    SeekBar wSB, fSB, sSB;
    long plantID;
    private boolean update;
    Plant plant;
    Intent i;
    CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        setTheme(R.style.AppTheme);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton create = findViewById(R.id.fab);

        toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(getString(R.string.plant_creation_bar));

            plantName = findViewById(R.id.nameTxt);
            notes = findViewById(R.id.notes);
            checkName = findViewById(R.id.checkName);
            back = findViewById(R.id.back);
            tipsTxt = findViewById(R.id.tips);
            wCB = findViewById(R.id.wateringCB);
            wSB = findViewById(R.id.wateringSB);
            wInf = findViewById(R.id.wateringInf);
            setAllListeners(wCB, wSB, wInf, ContextCompat.getColor(CreationActivity.this, R.color.blue));
            wCB.setChecked(false);
            fCB = findViewById(R.id.feedingCB);
            fSB = findViewById(R.id.feedingSB);
            fInf = findViewById(R.id.feedingInf);
            setAllListeners(fCB, fSB, fInf, ContextCompat.getColor(CreationActivity.this, R.color.yellow));
            fCB.setChecked(false);
            sCB = findViewById(R.id.sprayingCB);
            sSB = findViewById(R.id.sprayingSB);
            sInf = findViewById(R.id.sprayingInf);
            setAllListeners(sCB, sSB, sInf, ContextCompat.getColor(CreationActivity.this, R.color.colorMain));
            sCB.setChecked(false);

            DBPlants plants = new DBPlants(this);
            DBTips tips = new DBTips(this);

            checkName.setOnClickListener(view -> {
                fillPlantTipsDB(this);

                PlantTip planttip = tips.findString(plantName.getText().toString());
                if (planttip != null) {
                    tipsTxt.setText(getString(R.string.recommend_uxod));
                    setPlantParameters(planttip.getWatering(), wCB, wInf, wSB);
                    setPlantParameters(planttip.getFeeding(), fCB, fInf, fSB);
                    setPlantParameters(planttip.getWatering(), sCB, sInf, sSB);
                    notes.setText(planttip.getNotes());
                } else {
                    wCB.setChecked(false);
                    fCB.setChecked(false);
                    sCB.setChecked(false);
                    wSB.setProgress(0);
                    fSB.setProgress(0);
                    sSB.setProgress(0);
                    notes.setText("");
                    tipsTxt.setText(getString(R.string.plant_not_found));
                }
            });

            if (getIntent().hasExtra("plant")) {
                toolbarLayout.setTitle(getString(R.string.edit));
                plant = (Plant) getIntent().getSerializableExtra("plant");
                plantName.setText(plant.getName());
                notes.setText(plant.getNotes());
                setPlantParameters((int) plant.getWatering(), wCB, wInf, wSB);
                setPlantParameters((int) plant.getFeeding(), fCB, fInf, fSB);
                setPlantParameters((int) plant.getSpraying(), sCB, sInf, sSB);
                plantID = plant.getId();
                update = true;
            } else {
                plantID = -1;
            }

            create.setOnClickListener(v -> {
                DateDefiner def = new DateDefiner("dd/MM/yyyy");

                if (update) {
                    plant = new Plant(plantID,
                            plantName.getText().toString(),
                            notes.getText().toString(),
                            wSB.getProgress(),
                            fSB.getProgress(),
                            sSB.getProgress(),
                            def.defineDate(),
                            plant.getLastW(), plant.getLastF(), plant.getLastS(), plant.getLastMilWat(), plant.getLastMilFeed(), plant.getLastMilSpray());
                    plants.update(plant);

                    NotificationScheduler.cancelReminder(this, AlarmReceiver.class);
                    setPlantReminder(plant);

                    i = new Intent(CreationActivity.this, InfoPlantActivity.class);
                    i.putExtra("plant", plant);
                    startActivity(i);
                } else {
                    plant = new Plant(0, plantName.getText().toString(),
                            notes.getText().toString(),
                            wSB.getProgress(),
                            fSB.getProgress(),
                            sSB.getProgress(),
                            def.defineDate(),
                            getString(R.string.no), getString(R.string.no), getString(R.string.no),
                            0, 0, 0);

                    plants.insert(plantName.getText().toString(),
                            notes.getText().toString(),
                            wSB.getProgress(),
                            fSB.getProgress(),
                            sSB.getProgress(),
                            def.defineDate(),
                            getString(R.string.no), getString(R.string.no), getString(R.string.no),
                            0, 0, 0);

                    setPlantReminder(plant);

                    i = new Intent(CreationActivity.this, MainActivity.class);
                    startActivity(i);
                }

            });

        back.setOnClickListener(v -> {
            i = new Intent(CreationActivity.this, MainActivity.class);
            startActivity(i);
        });

            plantName.setOnEditorActionListener((v, actionId, event) -> {
                checkName.callOnClick();
                return true;
            });
        }

        private void fillPlantTipsDB(Context ctx) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ServicePlantTips service = retrofit.create(ServicePlantTips.class);
            Call<List<PlantTip>> call = service.getAllPlants();

            call.enqueue(new Callback<List<PlantTip>>() {
                @Override
                public void onResponse(Call<List<PlantTip>> call, Response<List<PlantTip>> response) {

                    List<PlantTip> plantTips = response.body();

                    DBTips db = new DBTips(ctx);

                    for (int i = 0; i < plantTips.size(); i++) {
                        System.out.println(plantTips.get(i).toString());

                        db.update(plantTips.get(i));

                        //if (plantName.toLowerCase().contains(plantTips.get(i).getName().toLowerCase())) {
                        //    plantTipToReturn = plantTips.get(i);
                        //}
                    }

                }

                @Override
                public void onFailure(Call<List<PlantTip>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(CreationActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

                }
            });


        }

        private void setAllListeners(CheckBox cb, final SeekBar sb, final TextView txt, final int color) {

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sb.setEnabled(true);
                txt.setText(String.valueOf(sb.getProgress()));
                sb.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            } else {
                sb.getProgressDrawable().setColorFilter(ContextCompat.getColor(CreationActivity.this, R.color.gray), PorterDuff.Mode.MULTIPLY);
                sb.setProgress(0);
                txt.setText("---");
                sb.setEnabled(false);
            }
        });
    }

        private void setPlantParameters(long parameter, CheckBox cb, TextView tv, SeekBar sb) {
        if (parameter != 0) {
            cb.setChecked(true);
            tv.setText(String.valueOf(parameter));
            sb.setProgress((int) parameter);
        } else {
            cb.setChecked(false);
            tv.setText("---");
        }
    }

        private void setPlantReminder(Plant plant) {
        if (plant.getWatering() != 0) {
            NotificationScheduler.setReminder(this, AlarmReceiver.class, plant.getWatering(), plant);
        }
        if ((plant.getFeeding() != 0)) {
            NotificationScheduler.setReminder(this, AlarmReceiver.class, plant.getFeeding(), plant);
        }
        if (plant.getSpraying() != 0) {
            NotificationScheduler.setReminder(this, AlarmReceiver.class, plant.getSpraying(), plant);
        }
    }

    }


