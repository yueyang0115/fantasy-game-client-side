package com.example.fantasyclient.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.ElementAdapter;
import com.example.fantasyclient.adapter.HighlightAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementFragment<T> extends BaseFragment {

    protected List<T> list = new ArrayList<>();
    protected ListView listView;
    protected ElementAdapter<T> adapter;

    public ElementFragment(List<T> list) {
        updateByList(list);
    }

    protected abstract void initAdapter();

    protected void initView(View v){
        listView = (ListView) v.findViewById(R.id.elementList);
        listView.setAdapter(adapter);
    };

    protected abstract void setListener();

    public void updateByElement(T t){
        if(list.contains(t)){
            list.set(list.indexOf(t), t);
        }
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void updateByList(List<T> list){
        this.list.clear();
        this.list.addAll(list);
    }

    protected void updateAdapter(HighlightAdapter adapter, List object){
        adapter.clear();
        adapter.addAll(object);
        adapter.notifyDataSetChanged();
    }
}
