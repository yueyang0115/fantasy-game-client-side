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
    protected void setView(BaseViewHolder baseViewHolder, Unit unit, int position){
        // Populate the data into the template view using the data object
        UnitViewHolder viewHolder = (UnitViewHolder) baseViewHolder;
        viewHolder.baseText.setVisibility(View.GONE);
        viewHolder.unitAtk.setVisibility(View.GONE);
        viewHolder.unitHp.setVisibility(View.GONE);
        viewHolder.unitSpeed.setVisibility(View.GONE);
        viewHolder.image.setImageDrawable(getDrawableByName(unit.getName()));
    }
}
