package com.example.fantasyclient.helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fantasyclient.model.Item;

import java.util.List;

public class ItemArrayAdapter extends ArrayAdapter<Item> {

    public ItemArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Item> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
