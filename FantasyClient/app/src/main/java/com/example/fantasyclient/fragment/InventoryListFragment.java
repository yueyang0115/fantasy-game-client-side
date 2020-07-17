package com.example.fantasyclient.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.example.fantasyclient.adapter.InventoryInfoAdapter;
import com.example.fantasyclient.model.Inventory;

import java.util.List;

public class InventoryListFragment extends ElementListFragment<Inventory> {

    //activity which contains this fragment
    protected OnInventorySelectedListener listener;

    public InventoryListFragment(List<Inventory> list) {
        super(list);
    }

    /**
     * This is an interface for activity to implement
     * to realize data communication between activity and fragment
     */
    public interface OnInventorySelectedListener {
        void onInventorySelected(Inventory inventory);
    }

    /**
     * This method stores touched activity as listener
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInventorySelectedListener) {
            listener = (OnInventorySelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement InventoryListFragment.OnInventorySelectedListener");
        }
    }

    @Override
    protected void initAdapter(){
        adapter = new InventoryInfoAdapter(getContext(), list);
    }

    @Override
    protected void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onInventorySelected((Inventory) parent.getItemAtPosition(position));
            }
        });
    }
}
