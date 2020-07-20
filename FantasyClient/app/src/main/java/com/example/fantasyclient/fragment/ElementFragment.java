package com.example.fantasyclient.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.ElementAdapter;

import java.util.List;

public abstract class ElementFragment<T> extends BaseFragment {

    protected List<T> list;
    protected ListView listView;
    protected ElementAdapter<T> adapter;

    public ElementFragment(List<T> list) {
        this.list = list;
    }

    protected abstract void initAdapter();

    protected void initView(View v){
        listView = (ListView) v.findViewById(R.id.elementList);
        listView.setAdapter(adapter);
    };

    protected abstract void setListener();
}
