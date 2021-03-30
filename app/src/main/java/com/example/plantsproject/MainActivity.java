package com.example.plantsproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Toolbar toolbar;
    PlantAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        list = findViewById(R.id.list);
        setSupportActionBar(toolbar);

        //массив растений
        ArrayList<Plant> plants = new ArrayList<>();
        adapter = new PlantAdapter(this, plants);

        //Intent i = getIntent();
        //plants.add(new Plant(i.getStringExtra("plantName"), 3, 3, 3));


        // адаптер plants
        //ArrayAdapter<Plant> adapter = new ArrayAdapter<>(this, R.layout.plant_list, plants);
        // привязка массива к адаптеру
        //list.setAdapter(adapter);


        //кнопка для создания растений
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PlantCreation.class);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


class PlantAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Plant> plants;

    public PlantAdapter (Context ctx, ArrayList<Plant> array) {
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
        //TODO
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.plant_list, null);
        }
        TextView name = view.findViewById(R.id.plantName);
        TextView watering = view.findViewById(R.id.plantWatering);
        TextView feeding = view.findViewById(R.id.plantFeeding);
        TextView spraying = view.findViewById(R.id.plantSpraying);

        Plant plant = plants.get(i);
        name.setText(plant.getName());
        watering.setText(plant.getWatering());
        feeding.setText(plant.getFeeding());
        spraying.setText(plant.getSpraying());

        return view;

    }
}