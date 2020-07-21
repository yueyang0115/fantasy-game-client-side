package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fantasyclient.R;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.InventoryInfoAdapter;
import com.example.fantasyclient.model.Inventory;

public class InventoryDetailFragment extends ElementDetailFragment<Inventory> {

    Button buttonLearn;

    public InventoryDetailFragment(Inventory inventory) {
        super(inventory);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unit_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Unit unit = (Unit) list.get(0);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.skillListLayout, new SkillListFragment(unit.getSkills(),unit));
        ft.commit();*/
    }

    @Override
    protected void initAdapter(){
        adapter = new InventoryInfoAdapter(getContext(), list);
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        buttonLearn = (Button) v.findViewById(R.id.buttonLearn);
    }

    @Override
    protected void setListener(){
        buttonLearn.setOnClickListener(v -> activityListener.doServiceFunction((SocketService socketService)->{
            //socketService.enqueue(new MessagesC2S(new LevelUpRequestMessage("start", ((Unit)list.get(0)).getId())));
        }));
    }
}
