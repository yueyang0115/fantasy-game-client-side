package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.MainActivity;
import com.example.fantasyclient.R;
import com.example.fantasyclient.model.*;

import java.util.List;

public class UnitArrayAdapter extends UnitAdapter {

    private static final String TAG = "UnitArrayAdapter";

    public UnitArrayAdapter(Context context, List<Unit> objects) {
        super(context, objects);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Unit unit = getItem(position);
        final ViewHolder unitViewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            unitViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.unit_layout, parent, false);
            // Lookup view for data population
            unitViewHolder.unitID = (TextView) convertView.findViewById(R.id.unitID);
            unitViewHolder.unitHp = (TextView) convertView.findViewById(R.id.unitHp);
            unitViewHolder.unitAtk = (TextView) convertView.findViewById(R.id.unitAtk);
            unitViewHolder.unitSpeed = (TextView) convertView.findViewById(R.id.unitSpeed);
            unitViewHolder.unitImg = (ImageView) convertView.findViewById(R.id.unitImg);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(unitViewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            unitViewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        assert unit != null;
        unitViewHolder.unitID.setText("ID: "+ unit.getId());
        unitViewHolder.unitHp.setText("HP: "+ unit.getHp());
        unitViewHolder.unitAtk.setText("ATK: "+ unit.getAtk());
        unitViewHolder.unitSpeed.setText("SPD: "+ unit.getSpeed());
        setImageByPosition(unitViewHolder.unitImg, position, MainActivity.getImageID(getContext(),unit.getName()));
        //viewHolder.unitImg.setImageResource(MainActivity.getImageID(getContext(),unit.getName()));
        // Return the completed view to render on screen
        return convertView;
    }

}
