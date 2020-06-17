package com.example.fantasyclient.adapter;

import android.content.Context;

import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.WorldCoord;

public class MapBuildingAdapter extends MapAdapter<Building> {

    private static String TAG = "MapBuildingAdapter";

    // Constructor
    public MapBuildingAdapter(Context context, WorldCoord coord) {
        super(context, coord);
    }

    @Override
    protected int getCachedImageID(WorldCoord coord) {
        return getImageID(imageMap.get(coord).getName());
    }
}