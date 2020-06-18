package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class BuildingArrayAdapter extends BuildingAdapter {

    private static final String TAG = "UnitArrayAdapter";

    public BuildingArrayAdapter(Context context, List<Building> objects) {
        super(context, objects);
    }

    @Override
    protected void findView(BuildingViewHolder viewHolder, View convertView){
        // Lookup view for data population
        viewHolder.buildingName = (TextView) convertView.findViewById(R.id.buildingName);
        viewHolder.buildingCost = (TextView) convertView.findViewById(R.id.buildingCost);
        viewHolder.buildingImg = (ImageView) convertView.findViewById(R.id.buildingImg);
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(BuildingViewHolder viewHolder, Building building, int position){
        // Populate the data into the template view using the data object
        viewHolder.buildingName.setText("Name: "+ building.getName());
        //viewHolder.buildingCost.setText("HP: "+ building.getCost());
        viewHolder.buildingImg.setImageDrawable(getDrawableByName(building.getName()));
    }

}
