package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.TerritoryViewHolder;
import com.example.fantasyclient.model.Territory;

import java.util.List;

public class TerritoryInfoAdapter extends TerritoryAdapter {

    public TerritoryInfoAdapter(Context context, List<Territory> objects) {
        super(context, objects);
        TAG = "TerritoryInfoAdapter";
    }

    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView) {
        // Lookup view for data population
        TerritoryViewHolder viewHolder = (TerritoryViewHolder) baseViewHolder;
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.terrainType);
        viewHolder.coordX = (TextView) convertView.findViewById(R.id.x);
        viewHolder.coordY = (TextView) convertView.findViewById(R.id.y);
        viewHolder.terrTame = (TextView) convertView.findViewById(R.id.tame);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.terrainImg);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setView(BaseViewHolder baseViewHolder, Territory territory, int position) {
        // Populate the data into the template view using the data object
        TerritoryViewHolder viewHolder = (TerritoryViewHolder) baseViewHolder;
        viewHolder.baseText.setText("Type: "+territory.getTerrainType());
        viewHolder.coordX.setText("X: "+territory.getCoord().getX());
        viewHolder.coordY.setText("Y: "+territory.getCoord().getY());
        viewHolder.terrTame.setText("Tame: "+territory.getTame());
        viewHolder.image.setImageDrawable(getDrawableByName(territory.getTerrainType()));
    }
}
