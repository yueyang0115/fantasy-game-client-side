package com.example.fantasyclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.fantasyclient.adapter.SkillInfoAdapter;
import com.example.fantasyclient.json.MessagesS2C;
import com.example.fantasyclient.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class SkillActivity extends BaseActivity {

    private SkillInfoAdapter skillInfoAdapter;
    private ListView skillListView;
    private List<Skill> skillList = new ArrayList<>();
    private Skill currSkill;
    private Button btnLearn, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_talent_tree);
        findView();
        initView();
        doBindService();
        getExtra();
        setOnClickListener();
    }

    @Override
    protected void findView(){
        btnLearn = (Button) findViewById(R.id.button_learn);
        btnCancel = (Button) findViewById(R.id.button_cancel);
        skillListView = (ListView) findViewById(R.id.skillList);
    }

    @Override
    protected void initView(){
        skillInfoAdapter = new SkillInfoAdapter(this, skillList);
        skillListView.setAdapter(skillInfoAdapter);
    }

    @Override
    protected void getExtra(){
        Intent intent = getIntent();
        currMessage = (MessagesS2C) intent.getSerializableExtra("CurrentMessage");
        assert currMessage != null;
        checkInventoryResult(currMessage.getInventoryResultMessage());
        checkAttributeResult(currMessage.getAttributeResultMessage());
    }

    @Override
    protected void setOnClickListener(){
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
