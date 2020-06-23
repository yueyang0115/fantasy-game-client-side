package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.UnitViewHolder;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class UnitInfoAdapter extends UnitAdapter {

    public UnitInfoAdapter(Context context, List<Unit> objects) {
        super(context, objects);
        TAG = "UnitInfoAdapter";
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(BaseViewHolder baseViewHolder, Unit unit, int position){
        // Populate the data into the template view using the data object
        UnitViewHolder viewHolder = (UnitViewHolder) baseViewHolder;
        viewHolder.baseText.setText("ID: "+ unit.getId());
        viewHolder.unitHp.setText("HP: "+ unit.getHp());
        viewHolder.unitAtk.setText("ATK: "+ unit.getAtk());
        viewHolder.unitSpeed.setText("SPD: "+ unit.getSpeed());
        setImageByPosition(viewHolder.image, position, new Drawable[]{getDrawableByName(unit.getName())}, highlightedPosition);
    }

}
