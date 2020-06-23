package com.example.fantasyclient.adapter;

import android.content.Context;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BuildingViewHolder;
import com.example.fantasyclient.adapter.viewholder.TerritoryViewHolder;
import com.example.fantasyclient.model.Building;
import com.example.fantasyclient.model.Territory;

import java.util.List;

public abstract class TerritoryAdapter extends ElementAdapter<Territory> {

    TerritoryAdapter(Context context, List<Territory> objects) {
        super(context, objects, R.layout.territory_layout);
    }

    @Override
    protected TerritoryViewHolder getViewHolder() {
        return new TerritoryViewHolder();
    }
}
