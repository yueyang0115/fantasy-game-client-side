package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.InventoryViewHolder;
import com.example.fantasyclient.model.Inventory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryPickerAdapter extends InventoryAdapter {

    //Map to store numbers of items to act on
    private Map<Integer,Integer> itemMap = new HashMap<>();

    public InventoryPickerAdapter(Context context, List<Inventory> objects) {
        super(context, objects);
        TAG = "InventoryPickerAdapter";
    }

    public Map<Integer,Integer> getItemMap(){
        return itemMap;
    }

    public void clearMap(){
        itemMap.clear();
        Log.d(TAG, "Clear Map");
    }

    protected void findView(BaseViewHolder baseViewHolder, View convertView) {
        // Lookup view for data population
        InventoryViewHolder viewHolder = (InventoryViewHolder)baseViewHolder; 
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.itemName);
        viewHolder.inventoryCost = (TextView) convertView.findViewById(R.id.itemCost);
        viewHolder.inventoryAmount = (TextView) convertView.findViewById(R.id.itemAmount);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.itemImage);
        viewHolder.inventoryNumPicker = (NumberPicker) convertView.findViewById(R.id.itemNum);
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
        } catch (Exception e) {
            Log.e(TAG,"Json object deserialization fails");
        }
        final InventoryViewHolder viewHolder = (InventoryViewHolder)baseViewHolder;
        viewHolder.baseText.setText("Name:" + name);
        viewHolder.inventoryCost.setText("Cost: " + cost);
        viewHolder.inventoryAmount.setText("Amount: " + inventory.getAmount());
        viewHolder.image.setImageDrawable(getDrawableByName(name));
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
