package com.example.fantasyclient.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.example.fantasyclient.adapter.UnitInfoAdapter;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class SoldierListFragment extends ElementListFragment<Unit> {

    //activity which contains this fragment
    protected OnSoldierSelectedListener listener;

    public SoldierListFragment(List<Unit> list) {
        super(list);
    }

    /**
     * This is an interface for activity to implement
     * to realize data communication between activity and fragment
     */
    public interface OnSoldierSelectedListener {
        void onSoldierSelected(Unit unit);
    }

    /**
     * This method stores touched activity as listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSoldierSelectedListener) {
            listener = (OnSoldierSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement SoldierListFragment.OnSoldierSelectedListener");
        }
    }

    @Override
    protected void initAdapter(){
        adapter = new UnitInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onSoldierSelected((Unit) parent.getItemAtPosition(position));
            }
        });
    }
}
