package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public abstract class UnitAdapter extends HighlightAdapter<Unit> {

    UnitAdapter(Context context, List<Unit> objects) {
        super(context, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Unit unit = getItem(position);
        final UnitViewHolder unitViewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            unitViewHolder = new UnitViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.unit_layout, parent, false);
            findView(unitViewHolder, convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(unitViewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            unitViewHolder = (UnitViewHolder) convertView.getTag();
        }

        setView(unitViewHolder, unit, position);
        // Return the completed view to render on screen
        return convertView;
    }

    protected abstract void findView(UnitViewHolder viewHolder, View convertView);

    protected abstract void setView(UnitViewHolder viewHolder, Unit unit, int position);
}
