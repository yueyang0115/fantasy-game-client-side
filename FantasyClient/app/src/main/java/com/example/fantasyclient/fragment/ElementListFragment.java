package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.ElementAdapter;

import java.util.List;

public abstract class ElementListFragment<T> extends BaseFragment {

    protected List<T> list;
    protected ListView listView;
    protected ElementAdapter<T> adapter;

    public ElementListFragment(List<T> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_element_list, container, false);
    }

    protected abstract void initAdapter();

    protected void initView(View v){
        listView = (ListView) v.findViewById(R.id.elementList);
        listView.setAdapter(adapter);
    };

    protected abstract void setListener();
}
