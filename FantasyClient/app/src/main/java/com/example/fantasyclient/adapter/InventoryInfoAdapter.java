package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.InventoryViewHolder;
import com.example.fantasyclient.model.Inventory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryInfoAdapter extends InventoryAdapter {

    public InventoryInfoAdapter(Context context, List<Inventory> objects) {
        super(context, objects);
        TAG = "InventoryInfoAdapter";
    }

    @SuppressLint("SetTextI18n")
    protected void setView(final BaseViewHolder baseViewHolder, final Inventory inventory, int position) {
        JSONObject jsonObject;
        String name = "";
        int cost = 0;
        try {
            jsonObject = new JSONObject(inventory.getDBItem().getItem_properties());
            name = jsonObject.getString("name");
            cost = jsonObject.getInt("cost");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final InventoryViewHolder viewHolder = (InventoryViewHolder)baseViewHolder;
        viewHolder.baseText.setText("Name:" + name);
        viewHolder.inventoryCost.setText("Cost: " + cost);
        viewHolder.inventoryAmount.setText("Amount: " + inventory.getAmount());
        viewHolder.inventoryNumPicker.setVisibility(View.GONE);
        setImageByPosition(viewHolder.image, position, new Drawable[]{getDrawableByName(name)}, highlightedPosition);
    }
}
