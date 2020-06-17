package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.Inventory;

import java.util.List;

public abstract class InventoryAdapter extends HighlightAdapter<Inventory> {

    static class InventoryViewHolder {
        TextView itemName, itemCost, itemAmount;
        NumberPicker itemNumPicker;
    }

    InventoryAdapter(Context context, List<Inventory> objects) {
        super(context, objects);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Inventory inventory = getItem(position);
        final InventoryViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new InventoryViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
            findView(viewHolder, convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (InventoryViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        assert inventory != null;
        setView(viewHolder, inventory, position);

        // Return the completed view to render on screen
        return convertView;
    }

    protected abstract void findView(InventoryViewHolder viewHolder, View convertView);

    protected abstract void setView(InventoryViewHolder viewHolder, Inventory inventory, int position);
}
