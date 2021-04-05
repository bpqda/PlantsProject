package com.example.plantsproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Toolbar toolbar;
    PlantAdapter adapter;
    DBPlants plants;
    final int ADD_ACTIVITY = 0;
    final int UPDATE_ACTIVITY = 1;
    final int DELETE_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        list = findViewById(R.id.list);
        setSupportActionBar(toolbar);

        plants = new DBPlants(this);

        adapter = new PlantAdapter(this, plants.selectAll());
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Plant selectedPlant = (Plant) adapter.getItem(position);
            PlantDialog dialog = new PlantDialog(selectedPlant);
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");
        });

        //кнопка для создания растений
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, PlantCreation.class);
            startActivityForResult(i, ADD_ACTIVITY);
            updateList();
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
                updateList();
                return true;
        }
            return super.onOptionsItemSelected(item);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Plant plant = (Plant) data.getSerializableExtra("plant");

            if (requestCode == UPDATE_ACTIVITY) {
                plants.update(plant);
            } else if (requestCode==ADD_ACTIVITY){
                plants.insert(plant.getName(), plant.getWatering(), plant.getFeeding(), plant.getSpraying());
            }
            updateList();
        }
    }

    private void updateList () {
        adapter.setArrayMyData(plants.selectAll());
        adapter.notifyDataSetChanged();
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
            watering.setText("Полив\n" + plant.getWatering());
            feeding.setText("Удобрение\n" + plant.getFeeding());
            spraying.setText("Опрыскивание\n" + plant.getSpraying());

            return view;

        }
    }
