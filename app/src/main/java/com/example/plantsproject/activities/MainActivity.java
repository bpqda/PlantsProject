package com.example.plantsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.dialogues.DeleteDialog;
import com.example.plantsproject.dialogues.PlantDialog;
import com.example.plantsproject.R;
import com.example.plantsproject.entitys.Plant;
import com.example.plantsproject.notifications.AlarmReceiver;
import com.example.plantsproject.notifications.NotificationScheduler;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView list;
    Toolbar toolbar;
    PlantAdapter adapter;
    DBPlants plants;
    LinearLayout layout;
    ImageView img;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = findViewById(R.id.toolbar);
        list = findViewById(R.id.list);
        setSupportActionBar(toolbar);
        layout = findViewById(R.id.layout);
        searchView = findViewById(R.id.searchView);
        plants = new DBPlants(this);
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);

        setSupportActionBar(bottomAppBar);

        adapter = new PlantAdapter(this, plants.selectAll());
        list.setAdapter(adapter);
        img = new ImageView(MainActivity.this);
        img.setImageResource(R.drawable.empty_list_pic);
        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        img.setLayoutParams(imageViewLayoutParams);

        if (list.getCount() == 0) {
            adapter.changeListToImage(list, img, layout);
        }

        list.setOnItemClickListener((parent, view, position, id) -> {
            Plant selectedPlant = adapter.getItem(position);
            PlantDialog dialog = new PlantDialog(selectedPlant, this);
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");

        });

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Locale myLocale = new Locale(PreferenceManager.getDefaultSharedPreferences(this).getString("language", "en"));
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, CreationActivity.class);
            startActivityForResult(i, RESULT_OK);

        });

        bottomAppBar.setNavigationOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, PlantTipsListActivity.class);
            startActivity(i);
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchItem(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchItem(newText);
                return true;
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
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_delete:
                NotificationScheduler.cancelReminder(this, AlarmReceiver.class);
                DeleteDialog dialog = new DeleteDialog(this, null,
                        getString(R.string.sure),
                        true);
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "dialog");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void searchItem(String textToSearch){
        ArrayList<Plant> array = plants.selectAll();
        Iterator<Plant> iter = array.iterator();
        while (iter.hasNext()) {
            Plant p = iter.next();
            if (!p.getName().toLowerCase().contains(textToSearch.toLowerCase())) {
                iter.remove();
            }
            adapter.setArrayMyData(array);
            adapter.notifyDataSetChanged();
        }
    }
}

class PlantAdapter extends BaseAdapter {
    private Context context;
    private List<Plant> plants;

    PlantAdapter(Context context, List<Plant> list ) {
        this.context = context;
        this.plants = list;
    }

    void setArrayMyData(List<Plant> arrayMyData) {
        this.plants = arrayMyData;
    }

    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Plant getItem(int position) {
        return plants.get(position);
    }

    //@Override
    //public Object getItem(int i) {
    //    return plants.get(i);
    //}

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
            view = LayoutInflater.from(context).inflate(R.layout.plant_list, viewGroup, false);
        }
        TextView name = view.findViewById(R.id.plantTipName);
        TextView stateWater = view.findViewById(R.id.stateWater);
        TextView stateFeed = view.findViewById(R.id.stateFeed);
        TextView stateSpray = view.findViewById(R.id.stateSpray);
        LinearLayout lay = view.findViewById(R.id.linLay);
        ImageView photo = view.findViewById(R.id.imageView);


        Plant plant = plants.get(i);
        name.setText(plant.getName());
        photo.setImageResource(plant.getPhoto());
        //photo.setImageDrawable(view.getResources().getDrawable(plant.getPhoto()));

        if (plant.getWatering() != 0 && System.currentTimeMillis() > plant.getLastMilWat() + plant.getWatering() * 1000 * 60 * 60 * 24) {
            stateWater.setText(R.string.need_w);
            lay.setBackgroundResource(R.drawable.plant_needsmth_background);
            stateFeed.setText("");
        }
        if (plant.getSpraying() != 0 && System.currentTimeMillis() > plant.getLastMilSpray() + plant.getSpraying() * 1000 * 60 * 60 * 24) {
            stateFeed.setText("");
            lay.setBackgroundResource(R.drawable.plant_needsmth_background);
            stateSpray.setText(R.string.need_s);
        }

        if (plant.getFeeding() != 0 && System.currentTimeMillis() > plant.getLastMilFeed() + plant.getFeeding() * 1000 * 60 * 60 * 24) {
            stateFeed.setText(R.string.need_f);
            lay.setBackgroundResource(R.drawable.plant_needsmth_background);
        }

        return view;
    }

    void changeListToImage(ListView lis, ImageView im, LinearLayout lay) {
        lay.removeView(lis);
        lay.addView(im);
    }

}
