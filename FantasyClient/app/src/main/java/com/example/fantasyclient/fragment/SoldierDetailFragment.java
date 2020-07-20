package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.R;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.UnitInfoAdapter;
import com.example.fantasyclient.json.LevelUpRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.Unit;

public class SoldierDetailFragment extends ElementDetailFragment<Unit> {

    Button buttonLearn;

    public SoldierDetailFragment(Unit unit) {
        super(unit);
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
        Unit unit = (Unit) list.get(0);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.skillListLayout, new SkillListFragment(unit.getSkills(),unit));
        ft.commit();
    }

    @Override
    protected void initAdapter(){
        adapter = new UnitInfoAdapter(getContext(), list);
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        buttonLearn = (Button) v.findViewById(R.id.buttonLearn);
    }

    @Override
    protected void setListener(){
        buttonLearn.setOnClickListener(v -> listener.doServiceFunction((SocketService socketService)->{
            socketService.enqueue(new MessagesC2S(new LevelUpRequestMessage("start", ((Unit)list.get(0)).getId())));
        }));
    }
}
