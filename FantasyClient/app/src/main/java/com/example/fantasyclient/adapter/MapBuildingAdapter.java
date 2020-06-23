package com.example.fantasyclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.WorldCoord;

public class MapBuildingAdapter extends MapAdapter<Building> {

    // Constructor
    public MapBuildingAdapter(Context context, WorldCoord coord) {
        super(context, coord);
        TAG = "MapBuildingAdapter";
    }

    @Override
    protected Drawable[] getImageDrawables(ImageView imageView, int position, Building building) {
        return new Drawable[]{
                getDrawableByName(building.getName())
        };
    }
}