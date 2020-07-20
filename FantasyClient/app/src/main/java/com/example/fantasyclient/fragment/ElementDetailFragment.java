package com.example.fantasyclient.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.fantasyclient.R;

import java.util.ArrayList;
import java.util.Collections;

public abstract class ElementDetailFragment<T> extends ElementFragment<T> {

    protected TextView elementDescription;

    public ElementDetailFragment(T t) {
        super(new ArrayList<>(Collections.singletonList(t)));
    }

    @Override
    protected void initAdapter(){}

    @Override
    protected void initView(View v){
        super.initView(v);
        elementDescription = (TextView) v.findViewById(R.id.elementDescription);
    };

    protected abstract void setListener();
}
