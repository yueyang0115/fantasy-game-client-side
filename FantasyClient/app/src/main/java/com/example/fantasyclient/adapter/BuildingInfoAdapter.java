package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.BuildingViewHolder;
import com.example.fantasyclient.model.Building;

import java.util.List;

/**
 * This is a BuildingAdapter class which shows all information of buildings
 */
public class BuildingInfoAdapter extends BuildingAdapter {

    public BuildingInfoAdapter(Context context, List<Building> objects) {
        super(context, objects);
        TAG = "BuildingArrayAdapter";
    }

    //set views based on the data object information
    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(BaseViewHolder baseViewHolder, Building building, int position){
        // cast BaseViewHolder to BuildingViewHolder
        BuildingViewHolder viewHolder = (BuildingViewHolder) baseViewHolder;
        // Populate the data into the template view using the data object
        viewHolder.baseText.setText("Name: "+ building.getName());
        viewHolder.buildingCost.setText("Cost: "+ building.getCost());
        // add frame to selected image
        setImageByPosition(viewHolder.image, position, new Drawable[]{getDrawableByName(building.getName())}, highlightedPosition);
    }

}
