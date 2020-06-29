package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.TerritoryViewHolder;
import com.example.fantasyclient.model.Territory;

import java.util.List;

/**
 * This is a TerritoryAdapter class which shows all information of territories
 */
public class TerritoryInfoAdapter extends TerritoryAdapter {

    public TerritoryInfoAdapter(Context context, List<Territory> objects) {
        super(context, objects);
        TAG = "TerritoryInfoAdapter";
    }

    //set views based on the data object information
    @SuppressLint("SetTextI18n")
    @Override
    protected void setView(BaseViewHolder baseViewHolder, Territory territory, int position) {
        TerritoryViewHolder viewHolder = (TerritoryViewHolder) baseViewHolder;
        // Populate the data into the template view using the data object
        viewHolder.baseText.setText("Type: "+territory.getTerrainType());
        viewHolder.coordX.setText("X: "+territory.getCoord().getX());
        viewHolder.coordY.setText("Y: "+territory.getCoord().getY());
        viewHolder.terrTame.setText("Tame: "+territory.getTame());
        // no need to select data object, not add frame here
        viewHolder.image.setImageDrawable(getDrawableByName(territory.getTerrainType()));
    }
}
