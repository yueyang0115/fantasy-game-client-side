package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.FragmentTransaction;

import com.example.fantasyclient.R;
import com.example.fantasyclient.SocketService;
import com.example.fantasyclient.adapter.UnitInfoAdapter;
import com.example.fantasyclient.json.LevelUpRequestMessage;
import com.example.fantasyclient.json.MessagesC2S;
import com.example.fantasyclient.model.Skill;
import com.example.fantasyclient.model.Unit;

import java.util.ArrayList;

public class UnitDetailFragment extends ElementDetailFragment<Unit> implements SkillListFragment.SkillSelector {

    Button buttonLearn;
    ImageView imageWeapon;

    public UnitDetailFragment(Unit unit) {
        super(unit);
        TAG = "UnitDetailFragment";
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
        Unit unit = list.get(0);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.skillListLayout, new SkillListFragment(new ArrayList<>(unit.getSkills()),unit, this));
        ft.commit();
    }

    @Override
    protected void initAdapter(){
        adapter = new UnitInfoAdapter(getContext(), list);
    }

    @Override
    protected void initView(View v) {
        super.initView(v);
        Unit unit = list.get(0);
        buttonLearn = (Button) v.findViewById(R.id.buttonLearn);
        imageWeapon = (ImageView) v.findViewById(R.id.imageWeapon);
        if(unit.getWeapon()!=null) {
            imageWeapon.setImageDrawable(getDrawableByName(list.get(0).getWeapon().getItem_class()));
        }
    }

    @Override
    protected void setListener(){
        buttonLearn.setOnClickListener(v
                -> activityListener.doServiceFunction((SocketService socketService)
                -> socketService.enqueue(new MessagesC2S(new LevelUpRequestMessage("start", list.get(0).getId())))));
    }

    @Override
    public void doWithSelectedSkill(Skill skill) {
        //do nothing here if click on learned skill
        //but can know which skill is selected
    }

}
