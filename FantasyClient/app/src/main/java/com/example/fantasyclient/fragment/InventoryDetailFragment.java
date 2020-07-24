package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.R;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.InventoryInfoAdapter;
import com.example.fantasyclient.json.InventoryRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.Inventory;
import com.example.fantasyclient.model.Unit;

import java.util.List;

public class InventoryDetailFragment extends ElementDetailFragment<Inventory> implements UnitListFragment.UnitSelector {

    Button buttonUse, buttonDrop;
    NumberPicker numberPicker;
    int amount = 0;
    List<Unit> targetList;
    Unit targetUnit;

    public InventoryDetailFragment(Inventory inventory, List<Unit> targetList) {
        super(inventory);
        this.targetList = targetList;
        targetUnit = this.targetList.get(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.skillListLayout, new UnitListFragment(targetList, this));
        ft.commit();
    }

    @Override
    protected void initAdapter(){
        adapter = new InventoryInfoAdapter(getContext(), list);
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        Inventory inventory = list.get(0);
        buttonUse = (Button) v.findViewById(R.id.buttonUse);
        buttonDrop = (Button) v.findViewById(R.id.buttonDrop);
        numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(inventory.getAmount());
        numberPicker.setMinValue(0);
        numberPicker.setValue(0);
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            amount = numberPicker.getValue();
        });
    }

    @Override
    protected void setListener(){
        buttonUse.setOnClickListener(v -> {
            activityListener.doServiceFunction((SocketService socketService)->{
                socketService.enqueue(new MessagesC2S(new InventoryRequestMessage("use", list.get(0).getId(), targetUnit.getId())));
            });
        });
    }

    @Override
    public void doWithSelectedUnit(Unit unit) {
        targetUnit = unit;
    }
}
