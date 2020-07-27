package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.UnitViewHolder;
import com.example.fantasyclient.model.Unit;

import java.util.List;

/**
 * This is a UnitAdapter which shows images of units
 */
public class UnitImageAdapter extends UnitAdapter {

    public UnitImageAdapter(Context context, List<Unit> objects) {
        super(context, objects);
        TAG = "UnitImageAdapter";
    }

    //set views based on the data object information
    @Override
    protected void setView(BaseViewHolder baseViewHolder, Unit unit, int position){
        UnitViewHolder viewHolder = (UnitViewHolder) baseViewHolder;
        // Populate the data into the template view using the data object
        viewHolder.baseText.setVisibility(View.GONE);
        viewHolder.unitAtk.setVisibility(View.GONE);
        viewHolder.unitHp.setVisibility(View.GONE);
        viewHolder.unitSpeed.setVisibility(View.GONE);
        viewHolder.unitExpBar.setVisibility(View.GONE);
        // no need to select data object, not add frame here
        viewHolder.image.setImageDrawable(getDrawableByName(unit.getName()));
    }
}
