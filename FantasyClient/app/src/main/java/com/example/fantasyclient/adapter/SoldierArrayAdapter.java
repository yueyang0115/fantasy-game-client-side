package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.MainActivity;
import com.example.fantasyclient.R;
import com.example.fantasyclient.model.ItemPack;
import com.example.fantasyclient.model.Soldier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoldierArrayAdapter extends ArrayAdapter<Soldier> {

    private static final String TAG = "SoldierArrayAdapter";

    //View lookup cache
    private static class ViewHolder{
        TextView soldierID, soldierHp, soldierAtk;
        ImageView soldierImg;
    }

    public SoldierArrayAdapter(Context context, List<Soldier> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Soldier soldier = getItem(position);
        final ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.soldier_layout, parent, false);
            // Lookup view for data population
            viewHolder.soldierID = (TextView) convertView.findViewById(R.id.soldierID);
            viewHolder.soldierHp = (TextView) convertView.findViewById(R.id.soldierHp);
            viewHolder.soldierAtk = (TextView) convertView.findViewById(R.id.soldierAtk);
            viewHolder.soldierImg = (ImageView) convertView.findViewById(R.id.soldierImg);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        assert soldier != null;
        viewHolder.soldierID.setText("ID: "+ soldier.getId());
        viewHolder.soldierHp.setText("HP: "+ soldier.getHp());
        viewHolder.soldierAtk.setText("ATK: "+ soldier.getAtk());
        viewHolder.soldierImg.setImageResource(MainActivity.getImageID(getContext(),soldier.getType()));
        // Return the completed view to render on screen
        return convertView;
    }
}
