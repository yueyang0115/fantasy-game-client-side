package com.example.fantasyclient.helper;

import android.content.Context;

import android.util.Log;
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
    private Integer[] ImageArray = {R.drawable.transparent};
    private static String TAG = "ImageHelper";

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return ImageArray.length;
    }

    public Integer getItem(int position) {
        return ImageArray[position];
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

    public void initMap(int source){
        ImageArray = new Integer[150];
        Arrays.fill(ImageArray,source);
    }

    public void updateImage(int pos, int source){
        try {
            ImageArray[pos] = source;
        } catch (Exception e) {
            Log.e(TAG,"UpdateImage fails");
            e.printStackTrace();
        }
    }
}