package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.InventoryViewHolder;
import com.example.fantasyclient.model.Inventory;

import java.util.List;

/**
 * This is an ElementAdapter class contains array of inventories
 */
public abstract class InventoryAdapter extends ElementAdapter<Inventory> {

    //initialize the inventory layout
    InventoryAdapter(Context context, List<Inventory> objects) {
        super(context, objects, R.layout.element_inventory);
    }

    @Override
    protected InventoryViewHolder getViewHolder() {
        return new InventoryViewHolder();
    }

    //find views of InventoryViewHolder
    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView) {
        // Lookup view for data population
        InventoryViewHolder viewHolder = (InventoryViewHolder)baseViewHolder;
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.itemName);
        viewHolder.inventoryCost = (TextView) convertView.findViewById(R.id.itemCost);
        viewHolder.inventoryAmount = (TextView) convertView.findViewById(R.id.itemAmount);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.itemImage);
        viewHolder.inventoryNumPicker = (NumberPicker) convertView.findViewById(R.id.itemNum);
    }
}
