package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.MainActivity;
import com.example.fantasyclient.R;
import com.example.fantasyclient.model.*;

import java.util.ArrayList;
import java.util.List;

public class UnitImageAdapter extends ArrayAdapter<Unit> {

    private static final String TAG = "SoldierArrayAdapter";

    //View lookup cache
    private static class ViewHolder{
        TextView unitID, unitHp, unitAtk;
        ImageView unitImg;
    }

    public UnitImageAdapter(Context context, List<Unit> objects) {
        super(context, 0, new ArrayList<>(objects));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Unit unit = getItem(position);
        final ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.unit_image_layout, parent, false);
            // Lookup view for data population
            viewHolder.unitImg = (ImageView) convertView.findViewById(R.id.unitImg);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        assert unit != null;
        viewHolder.unitImg.setImageResource(MainActivity.getImageID(getContext(),unit.getName()));
        // Return the completed view to render on screen
        return convertView;
    }
}
