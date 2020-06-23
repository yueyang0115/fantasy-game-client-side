package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.BuildingViewHolder;
import com.example.fantasyclient.model.Building;

import java.util.List;

public class BuildingInfoAdapter extends BuildingAdapter {

    public BuildingInfoAdapter(Context context, List<Building> objects) {
        super(context, objects);
        TAG = "BuildingArrayAdapter";
    }

    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView){
        // Lookup view for data population
        BuildingViewHolder viewHolder = (BuildingViewHolder) baseViewHolder;
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.buildingName);
        viewHolder.buildingCost = (TextView) convertView.findViewById(R.id.buildingCost);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.buildingImg);
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(BaseViewHolder baseViewHolder, Building building, int position){
        // Populate the data into the template view using the data object
        BuildingViewHolder viewHolder = (BuildingViewHolder) baseViewHolder;
        viewHolder.baseText.setText("Name: "+ building.getName());
        viewHolder.buildingCost.setText("Cost: "+ building.getCost());
        setImageByPosition(viewHolder.image, position, new Drawable[]{getDrawableByName(building.getName())}, highlightedPosition);
    }

}
