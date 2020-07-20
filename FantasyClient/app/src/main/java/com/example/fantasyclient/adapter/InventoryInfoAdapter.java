package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.InventoryViewHolder;
import com.example.fantasyclient.model.Inventory;

import org.json.JSONObject;

import java.util.List;

/**
 * This is an InventoryAdapter class which shows all information of inventories
 */
public class InventoryInfoAdapter extends InventoryAdapter {

    public InventoryInfoAdapter(Context context, List<Inventory> objects) {
        super(context, objects, R.layout.element_inventory);
        TAG = "InventoryInfoAdapter";
    }

    //set views based on the data object information
    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(final BaseViewHolder baseViewHolder, final Inventory inventory, int position) {
        // read data from item_properties of inventory, which is written in json
        JSONObject jsonObject;
        String name = "";
        int cost = 0;
        try {
            jsonObject = new JSONObject(inventory.getDBItem().getItem_properties());
            name = jsonObject.getString("name");
            cost = jsonObject.getInt("cost");
        } catch (Exception e) {
            Log.e(TAG,"Json object deserialization fails");
        }
        // cast BaseViewHolder to InventoryViewHolder
        final InventoryViewHolder viewHolder = (InventoryViewHolder)baseViewHolder;
        // Populate the data into the template view using the data object
        viewHolder.baseText.setText("Name:" + name);
        viewHolder.inventoryCost.setText("Cost: " + cost);
        viewHolder.inventoryAmount.setText("Amount: " + inventory.getAmount());
        // add frame to selected image
        setImageByPosition(viewHolder.image, position, new Drawable[]{getDrawableByName(name)});
    }
}
