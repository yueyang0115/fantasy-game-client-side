package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.TerritoryViewHolder;
import com.example.fantasyclient.model.Territory;

import java.util.List;

/**
 * This is an ElementAdapter class contains array of territories
 */
public abstract class TerritoryAdapter extends ElementAdapter<Territory> {

    //initialize the territory layout
    TerritoryAdapter(Context context, List<Territory> objects) {
        super(context, objects, R.layout.territory_layout);
    }

    @Override
    protected TerritoryViewHolder getViewHolder() {
        return new TerritoryViewHolder();
    }

    //find views of TerritoryViewHolder
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
}
