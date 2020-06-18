package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public abstract class BuildingAdapter extends HighlightAdapter<Building> {

    BuildingAdapter(Context context, List<Building> objects) {
        super(context, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Building building = getItem(position);
        final BuildingViewHolder buildingViewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            buildingViewHolder = new BuildingViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.building_layout, parent, false);
            findView(buildingViewHolder, convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(buildingViewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            buildingViewHolder = (BuildingViewHolder) convertView.getTag();
        }

        setView(buildingViewHolder, building, position);
        // Return the completed view to render on screen
        return convertView;
    }

    protected abstract void findView(BuildingViewHolder viewHolder, View convertView);

    protected abstract void setView(BuildingViewHolder viewHolder, Building building, int position);
}
