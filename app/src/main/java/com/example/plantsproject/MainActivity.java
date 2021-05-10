package com.example.plantsproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

import java.util.ArrayList;

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
            adapter.changeListToImage(list, img, layout);
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
                adapter.update(this);
                return true;
            case R.id.action_delete:
                NotificationScheduler.cancelReminder(this, AlarmReceiver.class);
                DeleteDialog dialog = new DeleteDialog(this, null,
                        getString(R.string.sure),
                        true,
                        adapter);
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "dialog");
                return true;
        }
            return super.onOptionsItemSelected(item);
        }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(this);
    }

}

    class PlantAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<Plant> plants;

        PlantAdapter(Context ctx, ArrayList<Plant> array) {
            inflater = LayoutInflater.from(ctx);
            setArrayMyData(array);
        }

        private void setArrayMyData(ArrayList<Plant> arrayMyData) {
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
            TextView stateWater = view.findViewById(R.id.stateWater);
            TextView stateFeed = view.findViewById(R.id.stateFeed);
            TextView stateSpray = view.findViewById(R.id.stateSpray);

            Plant plant = plants.get(i);
            name.setText(plant.getName());

            if (plant.getWatering()!=0 && System.currentTimeMillis() > plant.getLastMilWat() + plant.getWatering() * 1000*60*60*24) {
                stateWater.setText(R.string.need_w);
                stateFeed.setText("");
            }
            if(plant.getSpraying()!=0 && System.currentTimeMillis() > plant.getLastMilSpray() + plant.getSpraying() * 1000*60*60*24) {
                stateFeed.setText("");
                stateSpray.setText(R.string.need_s);
            }

            if (plant.getFeeding()!=0 && System.currentTimeMillis() > plant.getLastMilFeed() + plant.getFeeding() * 1000*60*60*24)
                    stateFeed.setText(R.string.need_f);

            return view;
        }
        void update(Context ctx) {
            PlantAdapter adapter = this;
            DBPlants plantsDB = new DBPlants(ctx);
            adapter.setArrayMyData(plantsDB.selectAll());
            adapter.notifyDataSetChanged();
        }
        void changeListToImage (ListView lis, ImageView im, LinearLayout lay) {
            lay.removeView(lis);
            lay.addView(im);
        }

    }
