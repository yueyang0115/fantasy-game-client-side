package com.example.fantasyclient.helper;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.fantasyclient.R;

import java.util.Arrays;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    // Keep all Images in array
    private Integer[] ImageArray = {R.drawable.base00};

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return ImageArray.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(110, 165));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(ImageArray[position]);
        return imageView;
    }

    public void initImage(){
        ImageArray = new Integer[150];
        Arrays.fill(ImageArray,R.drawable.base00);
    }

    public void updateImage(int pos, int source){
        ImageArray[pos]=source;
    }
}