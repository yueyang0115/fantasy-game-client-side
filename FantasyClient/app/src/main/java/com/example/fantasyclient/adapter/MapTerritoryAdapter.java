package com.example.fantasyclient.adapter;

import android.content.Context;

import com.example.fantasyclient.model.Territory;
import com.example.fantasyclient.model.WorldCoord;

public class MapTerritoryAdapter extends MapAdapter<Territory> {

    private static String TAG = "MapTerritoryAdapter";

    public MapTerritoryAdapter(Context context, WorldCoord coord) {
        super(context, coord);
    }

    @Override
    protected int getCachedImageID(WorldCoord coord) {
        return getImageID(imageMap.get(coord).getTerrainType());
    }
}