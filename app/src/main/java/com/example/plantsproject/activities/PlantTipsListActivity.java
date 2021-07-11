package com.example.plantsproject.activities;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.plantsproject.adapters.PlantTipAdapter;
import com.example.plantsproject.server.MyRetrofit;
import com.example.plantsproject.R;
import com.example.plantsproject.server.ServicePlantTips;
import com.example.plantsproject.entitys.PlantTip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Response;

/*СПРАВОЧНИК РАСТЕНИЙ*/

public class PlantTipsListActivity extends AppCompatActivity {
    private PlantTipAdapter adapter;
    private ArrayList<PlantTip> plantTips;
    private RecyclerView list;
    private ProgressBar progressBar;
    private boolean isPlantTipsListFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_tips_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Настройка RecyclerView
        list = findViewById(R.id.list);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(layoutManager);

        //Заполнение RecyclerView
        AsyncRequest a = new AsyncRequest();
        a.execute();

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        SearchView searchView = findViewById(R.id.searchView);
        search(searchView);

    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                localSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                localSearch(newText);
                if (newText.equals("") && plantTips != null) {
                    adapter.setMyData(plantTips);
                }
                return true;
            }

            private void localSearch(String str) {
                if (plantTips != null)
                    adapter.getFilter().filter(str);
                else
                    Toast.makeText(PlantTipsListActivity.this, getString(R.string.no_internet),
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Запрос на сервер для получения всех растений оттуда
    public void fillPlantTipsAsync() {
        ServicePlantTips service = MyRetrofit.createService();
        Call<ArrayList<PlantTip>> call = service.getAllPlants();
        try {

            plantTips = call.execute().body();
            Collections.sort(plantTips, (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
            isPlantTipsListFull = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class AsyncRequest extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fillPlantTipsAsync();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            if (isPlantTipsListFull) {
                adapter = new PlantTipAdapter(getBaseContext(), plantTips);
                list.setAdapter(adapter);
                return;
            }
            Toast.makeText(getBaseContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

}


