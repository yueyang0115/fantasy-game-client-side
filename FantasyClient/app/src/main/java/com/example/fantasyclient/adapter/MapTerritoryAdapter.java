package com.example.fantasyclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.fantasyclient.drawable.TextDrawable;
import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.WorldCoord;

public class MapTerritoryAdapter extends MapAdapter<Territory> {

    private static String TAG = "MapTerritoryAdapter";

    public MapTerritoryAdapter(Context context, WorldCoord coord) {
        super(context, coord);
    }

    @Override
    protected Drawable[] getImageDrawables(ImageView imageView, int position, Territory territory) {
        return new Drawable[]{
                getDrawableByName(territory.getTerrainType()),
                //new TextDrawable(getContext().getResources(), Integer.toString(territory.getTame()))
        };
    }
}