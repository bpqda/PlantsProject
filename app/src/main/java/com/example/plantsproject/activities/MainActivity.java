package com.example.plantsproject.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.plantsproject.Adapters.PlantAdapter;
import com.example.plantsproject.databases.DBPlants;
import com.example.plantsproject.dialogues.DeleteDialog;
import com.example.plantsproject.dialogues.PlantDialog;
import com.example.plantsproject.R;
import com.example.plantsproject.entitys.Plant;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/*СПИСОК ВСЕХ РАСТЕНИЙ*/
public class MainActivity extends AppCompatActivity {
    private PlantAdapter adapter;
    private DBPlants plants;
    private ListView list;

    public void initList() {
        adapter = new PlantAdapter(this, plants.selectAll());
        list.setAdapter(adapter);

        if (list.getCount() == 0) {
            LinearLayout layout = findViewById(R.id.layout);
            adapter.changeListToImage(list, layout, getBaseContext());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        plants = new DBPlants(this);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Устанавливается локализация, указанная в настройках
        Locale myLocale = new Locale(PreferenceManager.getDefaultSharedPreferences(this)
                .getString("language", "en"));
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        list = findViewById(R.id.list);
        list.setOnItemClickListener((parent, view, position, id) -> {
            PlantDialog dialog = new PlantDialog(plants.select(id), view.getContext());
            dialog.showDialog();

        });
        //При нажатии на плюс
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, CreationActivity.class);
            startActivity(i);

        });

        //Переход к справочнику растений в PlantTipList (левая кнопка в нижней панели)
        bottomAppBar.setNavigationOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, PlantTipsListActivity.class);
            startActivity(i);
        });

        SearchView searchView = findViewById(R.id.searchView);
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

        initList();
    }

    //Поиск растения в списке
    public void searchItem(String textToSearch) {
        ArrayList<Plant> array = plants.selectAll();
        Iterator<Plant> iter = array.iterator();
        while (iter.hasNext()) {
            Plant p = iter.next();
            if (!p.getName().toLowerCase().contains(textToSearch.toLowerCase())) {
                iter.remove();
            }
            adapter.setMyData(array);
            adapter.notifyDataSetChanged();
        }
    }

    //Меню (правая кнопка в нижней панели)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_delete:
                DeleteDialog dialog = new DeleteDialog(this, -1,
                        getString(R.string.sure));
                FragmentManager manager = getSupportFragmentManager();
                dialog.show(manager, "dialog");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }
}


