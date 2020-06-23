package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;

import java.util.List;

public abstract class ElementAdapter<T> extends HighlightAdapter<T> {

    private final int layout;

    ElementAdapter(Context context, List<T> objects, int layout) {
        super(context, objects);
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final T unit = getItem(position);
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

        setView(viewHolder, unit, position);
        // Return the completed view to render on screen
        return convertView;
    }

    protected abstract BaseViewHolder getViewHolder();

    protected abstract void findView(BaseViewHolder viewHolder, View convertView);

    protected abstract void setView(BaseViewHolder viewHolder, T t, int position);
}
