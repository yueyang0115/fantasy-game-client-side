package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fantasyclient.R;

import java.util.List;

public abstract class ElementListFragment<T> extends ElementFragment<T> {

    protected ElementSelector<T> elementListener;

    public ElementListFragment(List<T> list) {
        super(list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_element_list, container, false);
    }
}
