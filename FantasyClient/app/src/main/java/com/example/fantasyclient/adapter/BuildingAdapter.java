package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.BuildingViewHolder;
import com.example.fantasyclient.model.Building;

import java.util.List;

/**
 * This is an ElementAdapter class contains array of buildings
 */
public abstract class BuildingAdapter extends ElementAdapter<Building> {

    //initialize the building layout
    BuildingAdapter(Context context, List<Building> objects) {
        super(context, objects, R.layout.building_layout);
    }

    @Override
    protected BuildingViewHolder getViewHolder() {
        return new BuildingViewHolder();
    }

    //find views of BuildingViewHolder
    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView){
        // Lookup view for data population
        BuildingViewHolder viewHolder = (BuildingViewHolder) baseViewHolder;
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.buildingName);
        viewHolder.buildingCost = (TextView) convertView.findViewById(R.id.buildingCost);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.buildingImg);
    }
}
