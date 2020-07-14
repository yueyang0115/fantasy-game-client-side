package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.UnitViewHolder;
import com.example.fantasyclient.model.Unit;

import java.util.List;

/**
 * This is an ElementAdapter class contains array of units
 */
public abstract class UnitAdapter extends ElementAdapter<Unit> {

    //initialize the unit layout
    UnitAdapter(Context context, List<Unit> objects) {
        super(context, objects, R.layout.unit_layout);
    }

    @Override
    protected UnitViewHolder getViewHolder() {
        return new UnitViewHolder();
    }

    //find views of UnitViewHolder
    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView){
        // Lookup view for data population
        UnitViewHolder viewHolder = (UnitViewHolder) baseViewHolder;
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.unitID);
        viewHolder.unitLevel = (TextView) convertView.findViewById(R.id.unitLevel);
        viewHolder.unitHp = (TextView) convertView.findViewById(R.id.unitHp);
        viewHolder.unitAtk = (TextView) convertView.findViewById(R.id.unitAtk);
        viewHolder.unitSpeed = (TextView) convertView.findViewById(R.id.unitSpeed);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.unitImg);
    }
}
