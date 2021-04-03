package com.example.plantsproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Toolbar toolbar;
    PlantAdapter adapter;
    DBPlants dbConnector;
    public DBPlants plants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        list = findViewById(R.id.list);
        setSupportActionBar(toolbar);

        plants = new DBPlants(MainActivity.this);
        dbConnector = new DBPlants(this);
        adapter = new PlantAdapter(this, dbConnector.selectAll());
        list.setAdapter(adapter);

        //Не знаю зачем это нужно
        //registerForContextMenu(list);

        Intent i = new Intent();
        Plant plant = i.getParcelableExtra("plant");


        plants.insert(plant.getName(), plant.getWatering(), plant.getFeeding(), plant.getSpraying());
        //plants.insert(i.getStringExtra("name"), i.getIntExtra("watering", 0), 3, 4);


        //кнопка для создания растений
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PlantCreation.class);
                //startActivityForResult(i, 1);
                startActivity(i);
            }
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
                return true;
            case R.id.action_delete:
                plants.deleteAll();
                //list.invalidateViews();
                //adapter.notifyDataSetChanged();
                return true;
        }
            return super.onOptionsItemSelected(item);
        }

    }


    class PlantAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<Plant> plants;

        public PlantAdapter(Context ctx, ArrayList<Plant> array) {
            inflater = LayoutInflater.from(ctx);
            setArrayMyData(array);
        }

        public ArrayList<Plant> getArrayMyData() {
            return plants;
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
            watering.setText("Полив:" + plant.getWatering() + "");
            feeding.setText("Удобрение" + plant.getFeeding() + "");
            spraying.setText("Опрыскивание:" + plant.getSpraying() + "");

            return view;

        }
    }
