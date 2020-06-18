package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.Inventory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryArrayAdapter extends InventoryAdapter {

    //Map to store numbers of items to act on
    private Map<Integer,Integer> itemMap = new HashMap<>();
    private static final String TAG = "InventoryArrayAdapter";

    public InventoryArrayAdapter(Context context, List<Inventory> objects) {
        super(context, objects);
    }

    public Map<Integer,Integer> getItemMap(){
        return itemMap;
    }

    public void clearMap(){
        itemMap.clear();
        Log.d(TAG, "Clear Map");
    }

    protected void findView(InventoryViewHolder viewHolder, View convertView) {
        // Lookup view for data population
        viewHolder.inventoryName = (TextView) convertView.findViewById(R.id.itemName);
        viewHolder.inventoryCost = (TextView) convertView.findViewById(R.id.itemCost);
        viewHolder.inventoryAmount = (TextView) convertView.findViewById(R.id.itemAmount);
        viewHolder.inventoryNumPicker = (NumberPicker) convertView.findViewById(R.id.itemNum);
    }

    @SuppressLint("SetTextI18n")
    protected void setView(final InventoryViewHolder viewHolder, final Inventory inventory, int position) {
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
        viewHolder.inventoryName.setText("Name:" + name);
        viewHolder.inventoryCost.setText("Cost: " + cost);
        viewHolder.inventoryAmount.setText("Amount: " + inventory.getAmount());
        viewHolder.inventoryNumPicker.setMaxValue(inventory.getAmount());
        viewHolder.inventoryNumPicker.setMinValue(0);
        viewHolder.inventoryNumPicker.setValue(0);
        viewHolder.inventoryNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                itemMap.put(inventory.getId(), viewHolder.inventoryNumPicker.getValue());
            }
        });
    }
}
