package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.*;

import java.util.List;

public class UnitArrayAdapter extends UnitAdapter {

    private static final String TAG = "UnitArrayAdapter";

    public UnitArrayAdapter(Context context, List<Unit> objects) {
        super(context, objects);
    }

    @Override
    protected void findView(UnitViewHolder viewHolder, View convertView){
        // Lookup view for data population
        viewHolder.unitID = (TextView) convertView.findViewById(R.id.unitID);
        viewHolder.unitHp = (TextView) convertView.findViewById(R.id.unitHp);
        viewHolder.unitAtk = (TextView) convertView.findViewById(R.id.unitAtk);
        viewHolder.unitSpeed = (TextView) convertView.findViewById(R.id.unitSpeed);
        viewHolder.unitImg = (ImageView) convertView.findViewById(R.id.unitImg);
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(UnitViewHolder viewHolder, Unit unit, int position){
        // Populate the data into the template view using the data object
        viewHolder.unitID.setText("ID: "+ unit.getId());
        viewHolder.unitHp.setText("HP: "+ unit.getHp());
        viewHolder.unitAtk.setText("ATK: "+ unit.getAtk());
        viewHolder.unitSpeed.setText("SPD: "+ unit.getSpeed());
        setImageByPosition(viewHolder.unitImg, position, new Drawable[]{getDrawableByName(unit.getName())}, currPosition);
    }

}
