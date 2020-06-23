package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.UnitViewHolder;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class UnitImageAdapter extends UnitAdapter {

    public UnitImageAdapter(Context context, List<Unit> objects) {
        super(context, objects);
        TAG = "UnitImageAdapter";
    }

    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView){
        // Lookup view for data population
        UnitViewHolder viewHolder = (UnitViewHolder) baseViewHolder;
        viewHolder.image = (ImageView) convertView.findViewById(R.id.unitImg);
    }

    @Override
    protected void setView(BaseViewHolder baseViewHolder, Unit unit, int position){
        // Populate the data into the template view using the data object
        UnitViewHolder viewHolder = (UnitViewHolder) baseViewHolder;
        viewHolder.image.setImageDrawable(getDrawableByName(unit.getName()));
    }
}
