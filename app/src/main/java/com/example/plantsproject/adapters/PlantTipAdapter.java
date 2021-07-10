package com.example.plantsproject.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.plantsproject.R;
import com.example.plantsproject.activities.CreationActivity;
import com.example.plantsproject.entitys.Plant;
import com.example.plantsproject.entitys.PlantTip;

import java.util.ArrayList;

/*Адаптер для списка растений из справочника*/

public class PlantTipAdapter extends RecyclerView.Adapter<PlantTipAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<PlantTip> filteredList = new ArrayList<>();
    private Context context;
    private ArrayList<PlantTip> arrayList;

    public PlantTipAdapter(Context context, ArrayList<PlantTip> arrayList) {
        this.arrayList = arrayList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    public void setMyData (ArrayList<PlantTip> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public PlantTipAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.plant_tip_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantTipAdapter.ViewHolder holder, int position) {
        PlantTip plantTip = arrayList.get(position);

        holder.name.setText(plantTip.getName());
        holder.image.setImageResource(R.drawable.ic_info_outline_black_24dp);

        holder.itemView.setOnClickListener(v -> {
            Plant selectedPlant = plantTip.toPlant();
            Intent i = new Intent(context, CreationActivity.class);
            i.putExtra("plant", selectedPlant);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final ImageView image;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textViewPlantTip);
            image = view.findViewById(R.id.imageView);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filteredList = arrayList;
                } else {
                    ArrayList<PlantTip> filteredList2 = new ArrayList<>();
                    for (PlantTip p : arrayList) {
                        if (p.getName().toLowerCase().contains(charString.toLowerCase()))
                            filteredList2.add(p);
                    }
                    filteredList = filteredList2;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayList = (ArrayList<PlantTip>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }


}