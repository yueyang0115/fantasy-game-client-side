package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.fantasyclient.MainActivity;
import com.example.fantasyclient.R;
import com.example.fantasyclient.model.*;

import java.util.List;

public class UnitImageAdapter extends UnitAdapter {

    private static final String TAG = "UnitImageAdapter";

    public UnitImageAdapter(Context context, List<Unit> objects) {
        super(context, objects);
    }

    @Override
    protected void findView(UnitViewHolder viewHolder, View convertView){
        // Lookup view for data population
        viewHolder.unitImg = (ImageView) convertView.findViewById(R.id.unitImg);
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void setView(UnitViewHolder viewHolder, Unit unit, int position){
        // Populate the data into the template view using the data object
        viewHolder.unitImg.setImageDrawable(getDrawableByName(unit.getName()));
    }
}
