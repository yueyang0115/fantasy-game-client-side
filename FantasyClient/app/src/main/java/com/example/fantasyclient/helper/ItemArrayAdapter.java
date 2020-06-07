package com.example.fantasyclient.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.Item;
import com.example.fantasyclient.model.ItemPack;

import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<ItemPack> {

    public ItemArrayAdapter(Context context, List<ItemPack> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemPack itemPack = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        // Lookup view for data population
        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView itemCost = (TextView) convertView.findViewById(R.id.itemCost);
        TextView itemAmount = (TextView) convertView.findViewById(R.id.itemAmount);
        NumberPicker itemNum = (NumberPicker)convertView.findViewById(R.id.itemNum);
        // Populate the data into the template view using the data object
        itemName.setText(itemPack.getItem().getName());
        itemCost.setText(Integer.toString(itemPack.getItem().getCost()));
        itemAmount.setText(itemPack.getAmount());
        itemNum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
