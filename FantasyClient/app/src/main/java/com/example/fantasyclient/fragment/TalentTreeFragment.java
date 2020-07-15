package com.example.fantasyclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class TalentTreeFragment extends Fragment {

    private SkillInfoAdapter skillInfoAdapter;
    private ListView skillListView;
    private List<Skill> skillList = new ArrayList<>();
    private Skill currSkill;
    private Button btnLearn, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_talent_tree, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        initAdapter();
        initView(view);
    }

    private void initAdapter(){
        skillInfoAdapter = new SkillInfoAdapter(getContext(), skillList);
    }

    private void initView(View v){
        skillListView = (ListView) v.findViewById(R.id.skillList);
        skillListView.setAdapter(skillInfoAdapter);

        btnLearn = (Button) v.findViewById(R.id.button_learn);
        btnCancel = (Button) v.findViewById(R.id.button_cancel);
    }

    private void setOnClickListener(){
        skillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currSkill = (Skill) parent.getItemAtPosition(position);
                selectSkill(position);
            }
        });
        btnLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void selectSkill(int position){
        skillInfoAdapter.setHighlightedPosition(position);
        updateSkillAdapter();
    }

    private void updateSkillAdapter(){
        skillInfoAdapter.notifyDataSetChanged();
    }
}
