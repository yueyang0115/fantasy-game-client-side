package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.UnitViewHolder;
import com.example.fantasyclient.model.Unit;

import java.util.List;

/**
 * This is a UnitAdapter which shows all information of units
 */
public class UnitInfoAdapter extends UnitAdapter {

    public UnitInfoAdapter(Context context, List<Unit> objects) {
        super(context, objects);
        TAG = "UnitInfoAdapter";
    }

    //set views based on the data object information
    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(BaseViewHolder baseViewHolder, Unit unit, int position){
        UnitViewHolder viewHolder = (UnitViewHolder) baseViewHolder;
        // Populate the data into the template view using the data object
        viewHolder.baseText.setText("ID: "+ unit.getId());
        viewHolder.unitHp.setText("HP: "+ unit.getHp());
        viewHolder.unitAtk.setText("ATK: "+ unit.getAtk());
        viewHolder.unitSpeed.setText("SPD: "+ unit.getSpeed());
        // add frame to selected image
        setImageByPosition(viewHolder.image, position, new Drawable[]{getDrawableByName(unit.getName())});
    }

}
