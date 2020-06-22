package com.example.fantasyclient.adapter;

import android.content.Context;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BuildingViewHolder;
import com.example.fantasyclient.model.Building;

import java.util.List;

public abstract class BuildingAdapter extends ElementAdapter<Building> {

    BuildingAdapter(Context context, List<Building> objects) {
        super(context, objects, R.layout.building_layout);
    }

    @Override
    protected BuildingViewHolder getViewHolder() {
        return new BuildingViewHolder();
    }
}
