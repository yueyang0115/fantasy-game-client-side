package com.example.fantasyclient.adapter;

import android.content.Context;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.UnitViewHolder;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public abstract class UnitAdapter extends ElementAdapter<Unit> {

    UnitAdapter(Context context, List<Unit> objects) {
        super(context, objects, R.layout.unit_layout);
    }

    @Override
    protected UnitViewHolder getViewHolder() {
        return new UnitViewHolder();
    }
}
