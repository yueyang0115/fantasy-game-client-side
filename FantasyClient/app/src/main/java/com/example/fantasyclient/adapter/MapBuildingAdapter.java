package com.example.fantasyclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.WorldCoord;

public class MapBuildingAdapter extends MapAdapter<Building> {

    private static String TAG = "MapBuildingAdapter";

    // Constructor
    public MapBuildingAdapter(Context context, WorldCoord coord) {
        super(context, coord);
    }

    @Override
    protected Drawable[] getImageDrawables(ImageView imageView, int position, Building building) {
        return new Drawable[]{
                getDrawableByName(building.getName())
        };
    }
}