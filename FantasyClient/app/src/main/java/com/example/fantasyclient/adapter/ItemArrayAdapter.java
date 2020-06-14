package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemArrayAdapter extends ArrayAdapter<Inventory> {

    //Map to store numbers of items to act on
    private Map<Integer,Integer> itemMap = new HashMap<>();
    private static final String TAG = "ItemArrayAdapter";

    //View lookup cache
    private static class ViewHolder{
        TextView itemName, itemCost, itemAmount;
        NumberPicker itemNumPicker;
    }

    public ItemArrayAdapter(Context context, List<Inventory> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Inventory inventory = getItem(position);
        final ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
            // Lookup view for data population
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.itemCost = (TextView) convertView.findViewById(R.id.itemCost);
            viewHolder.itemAmount = (TextView) convertView.findViewById(R.id.itemAmount);
            viewHolder.itemNumPicker = (NumberPicker) convertView.findViewById(R.id.itemNum);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        assert inventory != null;
        viewHolder.itemName.setText("Name:"+inventory.getDBItem().getName());
        viewHolder.itemCost.setText("Cost: "+ inventory.getDBItem().getCost());
        viewHolder.itemAmount.setText("Amount: "+ inventory.getAmount());
        viewHolder.itemNumPicker.setMaxValue(inventory.getAmount());
        viewHolder.itemNumPicker.setMinValue(0);
        viewHolder.itemNumPicker.setValue(0);
        viewHolder.itemNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                itemMap.put(inventory.getId(),viewHolder.itemNumPicker.getValue());
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

    public Map<Integer,Integer> getItemMap(){
        return itemMap;
    }

    public void clearMap(){
        itemMap.clear();
        Log.d(TAG, "Clear Map");
    }
}
