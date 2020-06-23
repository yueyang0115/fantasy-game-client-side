package com.example.fantasyclient.adapter;

import android.content.Context;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.InventoryViewHolder;
import com.example.fantasyclient.model.Inventory;

import java.util.List;

public abstract class InventoryAdapter extends ElementAdapter<Inventory> {

    InventoryAdapter(Context context, List<Inventory> objects) {
        super(context, objects, R.layout.item_layout);
    }

    @Override
    protected InventoryViewHolder getViewHolder() {
        return new InventoryViewHolder();
    }
}
