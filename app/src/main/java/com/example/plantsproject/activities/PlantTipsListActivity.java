package com.example.plantsproject.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.plantsproject.server.MyRetrofit;
import com.example.plantsproject.R;
import com.example.plantsproject.server.ServicePlantTips;
import com.example.plantsproject.entitys.Plant;
import com.example.plantsproject.entitys.PlantTip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantTipsListActivity extends AppCompatActivity {

    ListView list;
    PlantTipAdapter adapter;
    EditText plantTipName;
    List<PlantTip> plantTips;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_tips_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = findViewById(R.id.list);
        plantTipName = findViewById(R.id.plantTipName);
        back = findViewById(R.id.back);
        fillPlantTips();

        back.setOnClickListener(v -> {

            Intent i = new Intent(PlantTipsListActivity.this, MainActivity.class);
            startActivity(i);
        });

        list.setOnItemClickListener((parent, view, position, id) -> {
            Plant selectedPlant = (Plant) adapter.getItem(position);

            Intent i = new Intent(PlantTipsListActivity.this, CreationActivity.class);
            i.putExtra("plant", selectedPlant);
            startActivity(i);

        });
        plantTipName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    fillPlantTips();
                }else {
                    searchItem(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void searchItem(String textToSearch){
        Iterator<PlantTip> iter = plantTips.iterator();
        while (iter.hasNext()) {
            PlantTip p = iter.next();
            if (!p.getName().toLowerCase().contains(textToSearch.toLowerCase()))
                iter.remove();
        }
        adapter.notifyDataSetChanged();
    }

    public void fillPlantTips() {
        ServicePlantTips service = MyRetrofit.createService();
        Call<List<PlantTip>> call = service.getAllPlants();
        call.enqueue(new Callback<List<PlantTip>>() {
            @Override
            public void onResponse(Call<List<PlantTip>> call, Response<List<PlantTip>> response) {
                plantTips = response.body();
                Collections.sort(plantTips, (o1, o2) -> o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()));
                adapter = new PlantTipAdapter(getBaseContext(), plantTips);
                list.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<PlantTip>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PlantTipsListActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

class PlantTipAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<PlantTip> plants;

    PlantTipAdapter(Context ctx, List<PlantTip> array) {
        inflater = LayoutInflater.from(ctx);
        setArrayMyData(array);
    }

    void setArrayMyData(List<PlantTip> arrayMyData) {
        this.plants = arrayMyData;
    }

    @Override
    public int getCount() {
        return plants.size();
    }

    @Override
    public Object getItem(int i) {
        return plants.get(i).toPlant();
    }

    @Override
    public long getItemId(int i) {
        PlantTip plant = plants.get(i);
        if (plant != null) {
            return plant.getId();
        }
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.plant_tip_list, null);
        }
        TextView name  = view.findViewById(R.id.textViewPlantTip);

        PlantTip plant = plants.get(i);
        name.setText(plant.getName().toLowerCase());

        return view;
    }

}

