package com.example.fantasyclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.fantasyclient.MainActivity;
import com.example.fantasyclient.R;
import com.example.fantasyclient.helper.AdapterHelper;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;
import java.util.List;

public abstract class UnitAdapter extends ArrayAdapter<Unit> {

    private int currPosition = 0;

    //View lookup cache
    static class ViewHolder {
        TextView unitID, unitHp, unitAtk, unitSpeed;
        ImageView unitImg;
    }

    public UnitAdapter(Context context, List<Unit> objects) {
        super(context, 0, new ArrayList<>(objects));
    }

    /**
     * This method distinguish specific territories from others:
     * 1. center territory
     * @param imageView target image view to set
     * @param position position of the image view
     * @param imageID image resource to set
     */
    void setImageByPosition(ImageView imageView, int position, int imageID){
        AdapterHelper.setImageByPosition(this.getContext(), imageView, position, imageID, currPosition);
    }

    public void setCurrPosition(int position){
        currPosition = position;
    }
}
