package com.example.plantsproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.plantsproject.R;
import com.example.plantsproject.activities.CreationActivity;
import com.example.plantsproject.entitys.Plant;

import java.util.List;

/*Адаптер для ListView в MainActivity*/

public class PlantAdapter extends BaseAdapter {
    private Context context;
    private List<Plant> plants;

    public PlantAdapter(Context context, List<Plant> list) {
        this.context = context;
        this.plants = list;
    }

    public void setMyData(List<Plant> arrayMyData) {
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

    @Override
    public long getItemId(int i) {
        Plant plant = plants.get(i);
        if (plant != null) {
            return plant.getId();
        }
        return 0;
    }

    private LinearLayout lay;
    private TextView stateFeed;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.plant_list, viewGroup, false);
        }
        stateFeed = view.findViewById(R.id.stateFeed);
        lay = view.findViewById(R.id.linLay);

        Plant plant = plants.get(i);

        TextView name = view.findViewById(R.id.plantTipName);
        name.setText(plant.getName());

        ImageView photo = view.findViewById(R.id.imageView);
        photo.setImageResource(plant.getPhoto());

        //Если растению нужно что-то, то его фон в списке станет желтым
        TextView stateWater = view.findViewById(R.id.stateWater);
        if (plant.getWatering() != 0 && System.currentTimeMillis() > plant.getLastMilWat() + plant.getWatering() * 1000 * 60 * 60 * 24)
            setPlantStateNeedSomething(stateWater, R.string.need_w);

        TextView stateSpray = view.findViewById(R.id.stateSpray);
        if (plant.getSpraying() != 0 && System.currentTimeMillis() > plant.getLastMilSpray() + plant.getSpraying() * 1000 * 60 * 60 * 24)
        setPlantStateNeedSomething(stateSpray, R.string.need_s);

        if (plant.getFeeding() != 0 && System.currentTimeMillis() > plant.getLastMilFeed() + plant.getFeeding() * 1000 * 60 * 60 * 24)
            setPlantStateNeedSomething(stateFeed, R.string.need_f);

        return view;
    }

    private void setPlantStateNeedSomething(TextView state, int strResId) {
        stateFeed.setText("");
        state.setText(context.getString(strResId));
        lay.setBackgroundResource(R.drawable.plant_needsmth_background);
    }

    //Если список пустой, то он меняется на картинку
    public void changeListToImage(ListView lis, LinearLayout lay, Context context) {
        lay.removeView(lis);

        ConstraintLayout constraintLayout = (ConstraintLayout) View.inflate(context, R.layout.empty, null);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));
        ImageView im = constraintLayout.findViewById(R.id.imageView4);
        im.setImageResource(R.drawable.empty_list_pic);

        lay.addView(constraintLayout);

        //При нажатии на экран MainActivity переход в CreationActivity
        lay.setOnClickListener(view -> {
            Intent i = new Intent(context, CreationActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }
}