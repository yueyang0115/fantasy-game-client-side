package com.example.fantasyclient.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fantasyclient.R;
import com.example.fantasyclient.adapter.viewholder.BaseViewHolder;
import com.example.fantasyclient.adapter.viewholder.SkillViewHolder;
import com.example.fantasyclient.model.Skill;

import java.util.List;

public abstract class SkillAdapter extends ElementAdapter<Skill> {

    //initialize the skill layout
    SkillAdapter(Context context, List<Skill> objects) {
        super(context, objects, R.layout.element_skill);
    }

    @Override
    protected BaseViewHolder getViewHolder() {
        return new SkillViewHolder();
    }

    @Override
    protected void findView(BaseViewHolder baseViewHolder, View convertView) {
        SkillViewHolder viewHolder = (SkillViewHolder) baseViewHolder;
        viewHolder.baseText = (TextView) convertView.findViewById(R.id.skillName);
        viewHolder.skillDamage = (TextView) convertView.findViewById(R.id.skillDamage);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.skillImg);
    }

    @Override
    protected void setView(BaseViewHolder viewHolder, Skill skill, int position) {

    }
}
