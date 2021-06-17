package com.example.plantsproject.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.notifications.DateDefiner;
import com.example.plantsproject.server.MyRetrofit;
import com.example.plantsproject.R;
import com.example.plantsproject.server.ServicePlantTips;
import com.example.plantsproject.entitys.Plant;
import com.example.plantsproject.entitys.PlantTip;
import com.example.plantsproject.notifications.AlarmReceiver;
import com.example.plantsproject.notifications.NotificationScheduler;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreationActivity extends AppCompatActivity {

    TextView plantName, notes, wInf, fInf, sInf, tipsTxt;
    ImageButton checkName, back, left, right;
    ImageSwitcher photoView;
    CheckBox wCB, fCB, sCB;
    SeekBar wSB, fSB, sSB;
    long plantID;
    private boolean update, fromPlantTipsList;
    Plant plant;
    Intent i;
    CollapsingToolbarLayout toolbarLayout;
    int position = 0;
    static private int[] photos = {R.drawable.plant_default, R.drawable.plant_green, R.drawable.plant_orange, R.drawable.plant_red};
    Button create2, autoWatering;
    String url;
    DateDefiner def;
    FloatingActionButton create;
    DBPlants plants;
    public void initPlant() {
        if (getIntent().hasExtra("plant")) {
            plant = (Plant) getIntent().getSerializableExtra("plant");
            fromPlantTipsList = getIntent().getBooleanExtra("from_plantTipsList", false);
            plant = plants.select(plant.getId());
            plantName.setText(plant.getName());
            notes.setText(plant.getNotes());
            setPlantParameters((int) plant.getWatering(), wCB, wInf, wSB);
            setPlantParameters((int) plant.getFeeding(), fCB, fInf, fSB);
            setPlantParameters((int) plant.getSpraying(), sCB, sInf, sSB);
            plantID = plant.getId();

            if (plantID <= 0) {
                url = plant.getUrl();
                update = false;
            } else {
                create.setImageResource(R.drawable.ic_check_black_24dp);
                create.setRotation(0);
                create2.setText(getString(R.string.edit));
                toolbarLayout.setTitle(getString(R.string.edit));
                update = true;
            }
        } else {
            plantID = -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPlant();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        setTheme(R.style.AppTheme);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         create = findViewById(R.id.fab);

        toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(getString(R.string.plant_creation_bar));

        plantName = findViewById(R.id.plantTipName);
        notes = findViewById(R.id.notes);
        checkName = findViewById(R.id.checkName);
        back = findViewById(R.id.back);
        tipsTxt = findViewById(R.id.tips);
        photoView = findViewById(R.id.photo);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        create2 = findViewById(R.id.save);
        autoWatering = findViewById(R.id.autoWatering);

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
        def = new DateDefiner(this, true);

         plants = new DBPlants(this);

        checkName.setOnClickListener(view -> searchPlant(plantName.getText().toString()));

        initPlant();

        photoView.setFactory(() -> {
            ImageView imageView = new ImageView(getBaseContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new
                    ImageSwitcher.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            return imageView;
        });
        photoView.setImageResource(photos[0]);

        right.setOnClickListener(v -> {
            position++;
            if (position > photos.length - 1) {
                position = 0;
            }
            photoView.setImageResource(photos[position]);
        });
        left.setOnClickListener(v -> {
            position--;
            if (position < 0) {
                position = photos.length - 1;
            }
            photoView.setImageResource(photos[position]);
        });


        View.OnClickListener createListener = v -> {
            if (plantName.getText().toString().equals("")) {
                Toast.makeText(getBaseContext(), getString(R.string.input_name), Toast.LENGTH_SHORT).show();
                return;
            }

            if (update) {
                plant = new Plant(plantID,
                        plantName.getText().toString(),
                        notes.getText().toString(),
                        wSB.getProgress(),
                        fSB.getProgress(),
                        sSB.getProgress(),
                        def.defineDate(),
                        plant.getLastW(), plant.getLastF(), plant.getLastS(), plant.getLastMilWat(), plant.getLastMilFeed(),
                        plant.getLastMilSpray(), photos[position], url);

                plants.update(plant);

                NotificationScheduler.cancelReminder(getBaseContext(), AlarmReceiver.class);
                setPlantReminder(plant);

                i = new Intent(CreationActivity.this, InfoPlantActivity.class);
                i.putExtra("plant", plant);
                startActivity(i);
                return;
            }
            if (fromPlantTipsList) {
                plant = new Plant(0,
                        plantName.getText().toString(),
                        notes.getText().toString(),
                        wSB.getProgress(),
                        fSB.getProgress(),
                        sSB.getProgress(),
                        def.defineDate(),
                        plant.getLastW(), plant.getLastF(), plant.getLastS(),
                        plant.getLastMilWat(), plant.getLastMilFeed(), plant.getLastMilSpray(), photos[position], url);

            } else {
                plant = new Plant(0, plantName.getText().toString(),
                        notes.getText().toString(),
                        wSB.getProgress(),
                        fSB.getProgress(),
                        sSB.getProgress(),
                        def.defineDate(),
                        getString(R.string.no), getString(R.string.no), getString(R.string.no),
                        0, 0, 0, photos[position], url);
            }
            plants.insert(plant);
            System.out.println(plant.getId());
            setPlantReminder(plant);
            i = new Intent(CreationActivity.this, MainActivity.class);
            startActivity(i);
        };

        create.setOnClickListener(createListener);
        create2.setOnClickListener(createListener);
        autoWatering.setOnClickListener(v -> {
            Intent intent = new Intent(CreationActivity.this, AutoWateringActivity.class);
            intent.putExtra("plant", plantID);
            startActivity(intent);
        });

        back.setOnClickListener(v -> {
            onBackPressed();
        });

        plantName.setOnEditorActionListener((v, actionId, event) -> {
            checkName.callOnClick();
            return true;
        });
        plantName.setOnClickListener(view -> {
            plantName.setText("");
            plantName.setOnClickListener(null);
        });
    }

    private void searchPlant(String str) {

        tipsTxt.setText(getString(R.string.searching));
        ServicePlantTips service = MyRetrofit.createService();

        Call<PlantTip> call = service.getPlantTipByName(str);
        call.enqueue(new Callback<PlantTip>() {
            @Override
            public void onResponse(Call<PlantTip> call, Response<PlantTip> response) {
                PlantTip plantTip = response.body();
                if (plantTip != null) {
                    wSB.setProgress(plantTip.getWatering());
                    fSB.setProgress(plantTip.getFeeding());
                    sSB.setProgress(plantTip.getSpraying());
                    notes.setText(plantTip.getNotes());
                    tipsTxt.setText(getString(R.string.recommend_uxod));
                } else {
                    tipsTxt.setText(getString(R.string.plant_not_found));
                }
            }

            @Override
            public void onFailure(Call<PlantTip> call, Throwable t) {
                t.printStackTrace();
                tipsTxt.setText(getString(R.string.error));
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
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications", true)) {
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
}