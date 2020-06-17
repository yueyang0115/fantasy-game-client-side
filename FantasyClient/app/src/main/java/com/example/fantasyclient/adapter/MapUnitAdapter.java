package com.example.fantasyclient.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.fantasyclient.model.Unit;
import com.example.fantasyclient.model.WorldCoord;

public class MapUnitAdapter extends MapAdapter<Unit> {

    private static String TAG = "MapUnitAdapter";

    // Constructor
    public MapUnitAdapter(Context context, WorldCoord coord) {
        super(context, coord);
    }

    @Override
    protected Drawable[] getImageDrawables(ImageView imageView, int position, Unit unit) {
        return new Drawable[]{
                getDrawableByName(unit.getName()),
        };
    }
}