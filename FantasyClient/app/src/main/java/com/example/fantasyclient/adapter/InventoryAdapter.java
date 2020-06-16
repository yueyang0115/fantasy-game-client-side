package com.example.fantasyclient.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.helper.AdapterHelper;
import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;
import java.util.List;

public abstract class InventoryAdapter extends ArrayAdapter<Inventory> {

    private int currPosition = 0;

    static class ViewHolder{
        TextView itemName, itemCost, itemAmount;
        NumberPicker itemNumPicker;
    }

    public InventoryAdapter(Context context, List<Inventory> objects) {
        super(context, 0, new ArrayList<>(objects));
    }

    /**
     * This method distinguish specific territories from others:
     * 1. center territory
     * @param imageView target image view to set
     * @param position position of the image view
     * @param imageID image resource to set
     */
    void setImageByPosition(ImageView imageView, int position, int imageID){
        AdapterHelper.setImageByPosition(this.getContext(), imageView, position, imageID, currPosition);
    }

    public void setCurrPosition(int position){
        currPosition = position;
    }
}
