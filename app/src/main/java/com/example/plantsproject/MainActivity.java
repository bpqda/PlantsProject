package com.example.plantsproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Toolbar toolbar;
    PlantAdapter adapter;
    DBPlants plants;
    LinearLayout layout;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = findViewById(R.id.toolbar);
        list = findViewById(R.id.list);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.layout);
        plants = new DBPlants(this);

        adapter = new PlantAdapter(this, plants.selectAll());
        list.setAdapter(adapter);
        img = new ImageView(MainActivity.this);
        img.setImageResource(R.drawable.empty_list_pic);
        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        img.setLayoutParams(imageViewLayoutParams);

        if(list.getCount()==0) {
            changeListToImage(list, img, layout);
        }

        list.setOnItemClickListener((parent, view, position, id) -> {
            Plant selectedPlant = (Plant) adapter.getItem(position);
            PlantDialog dialog = new PlantDialog(selectedPlant, this, adapter);
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, PlantCreation.class);
            startActivityForResult(i, RESULT_OK);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                updateList();
                return true;
            case R.id.action_delete:
                //NotificationScheduler.cancelReminder(this, AlarmReceiver.class);
                DeleteDialog dialog = new DeleteDialog(this, null,
                        "Вы действительно хотите удалить все растения?",
                        true, adapter);
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "dialog");
                if (getIntent().hasExtra("deletedAll")) {
                    changeListToImage(list, img, layout);
                }
                return true;
        }
            return super.onOptionsItemSelected(item);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            updateList();
        }
    }

    private void updateList () {
        adapter.setArrayMyData(plants.selectAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }
    private void changeListToImage (ListView lis, ImageView im, LinearLayout lay) {
        lay.removeView(lis);
        lay.addView(im);
    }

}

    class PlantAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<Plant> plants;

        public PlantAdapter(Context ctx, ArrayList<Plant> array) {
            inflater = LayoutInflater.from(ctx);
            setArrayMyData(array);
        }

        public void setArrayMyData(ArrayList<Plant> arrayMyData) {
            this.plants = arrayMyData;
        }

        @Override
        public int getCount() {
            return plants.size();
        }

        @Override
        public Object getItem(int i) {
            return plants.get(i);
        }

        @Override
        public long getItemId(int i) {
            Plant plant = plants.get(i);
            if (plant != null) {
                return plant.getId();
            }
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = inflater.inflate(R.layout.plant_list, null);
            }
            TextView name = view.findViewById(R.id.nameTxt);
            TextView watering = view.findViewById(R.id.plantWatering);
            TextView feeding = view.findViewById(R.id.plantFeeding);
            TextView spraying = view.findViewById(R.id.plantSpraying);

            Plant plant = plants.get(i);
            name.setText(plant.getName());
            if(plant.getWatering()!=0) {
                watering.setText("Полив\n" + plant.getWatering());
            } else {
                watering.setText("Полив\n-");
            }
            if(plant.getFeeding()!=0) {
                feeding.setText("Удобрение\n" + plant.getFeeding());
            } else {
                feeding.setText("Удобрение\n-");
            }
            if(plant.getSpraying()!=0) {
                spraying.setText("Опрыскивание\n" + plant.getSpraying());
            } else {
                spraying.setText("Опрыскивание\n-");
            }
            return view;

        }
    }
