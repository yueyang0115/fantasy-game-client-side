package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;

import java.util.List;

/**
 * This is an HighlightAdapter class which overrides getView() function to show an array of elements
 * @param <T> the type of element in array
 */
public abstract class ElementAdapter<T> extends HighlightAdapter<T> {

    //the layout of array
    private final int layout;

    //initialize layout in constructor
    ElementAdapter(Context context, List<T> objects, int layout) {
        super(context, objects);
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final T t = getItem(position);
        final BaseViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = getViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
            findView(viewHolder, convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }else{
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (BaseViewHolder) convertView.getTag();
        }
        setView(viewHolder, t, position);
        // Return the completed view to render on screen
        return convertView;
    }

    //get different ViewHolder class based on T
    protected abstract BaseViewHolder getViewHolder();

    //find views based on T
    protected abstract void findView(BaseViewHolder viewHolder, View convertView);

    //set views based on T
    protected abstract void setView(BaseViewHolder viewHolder, T t, int position);
}
