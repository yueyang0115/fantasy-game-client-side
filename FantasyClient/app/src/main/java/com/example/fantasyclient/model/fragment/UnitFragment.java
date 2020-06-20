package com.example.fantasyclient.model.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fantasyclient.R;

public class UnitFragment extends Fragment {
    private ImageView unitImg;
    private TextView hpTextView, atkTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_unit, container, false);
        unitImg = v.findViewById(R.id.unitImg);
        hpTextView = v.findViewById(R.id.unitHp);
        atkTextView = v.findViewById(R.id.unitAtk);
        return v;
    }
}
