package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.InventoryViewHolder;
import com.example.fantasyclient.model.Inventory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InventoryPickerAdapter extends InventoryAdapter {

    //Map to store numbers of items to act on
    private List<Inventory> selectedItems = new ArrayList<>();

    public InventoryPickerAdapter(Context context, List<Inventory> objects) {
        super(context, objects, R.layout.element_inventory_picker);
        TAG = "InventoryPickerAdapter";
    }

    public List<Inventory> getSelectedItems(){
        return selectedItems;
    }

    public void clearMap(){
        selectedItems.clear();
        Log.d(TAG, "Clear Map");
    }

    protected void findView(BaseViewHolder baseViewHolder, View convertView) {
        // Lookup view for data population
        super.findView(baseViewHolder,convertView);
        InventoryViewHolder viewHolder = (InventoryViewHolder)baseViewHolder;
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
        viewHolder.inventoryNumPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int amount = viewHolder.inventoryNumPicker.getValue();
            if(selectedItems.contains(inventory)){
                selectedItems.get(selectedItems.indexOf(inventory)).setAmount(amount);
            }
            else {
                selectedItems.add(new Inventory(inventory, amount));
            }
        });
    }
}
