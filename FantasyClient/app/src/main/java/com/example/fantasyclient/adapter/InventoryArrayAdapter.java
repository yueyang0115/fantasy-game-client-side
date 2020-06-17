package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemName);
        viewHolder.itemCost = (TextView) convertView.findViewById(R.id.itemCost);
        viewHolder.itemAmount = (TextView) convertView.findViewById(R.id.itemAmount);
        viewHolder.itemNumPicker = (NumberPicker) convertView.findViewById(R.id.itemNum);
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
        viewHolder.itemName.setText("Name:" + name);
        viewHolder.itemCost.setText("Cost: " + cost);
        viewHolder.itemAmount.setText("Amount: " + inventory.getAmount());
        viewHolder.itemNumPicker.setMaxValue(inventory.getAmount());
        viewHolder.itemNumPicker.setMinValue(0);
        viewHolder.itemNumPicker.setValue(0);
        viewHolder.itemNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                itemMap.put(inventory.getId(), viewHolder.itemNumPicker.getValue());
            }
        });
    }
}
