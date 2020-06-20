package com.example.fantasyclient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.example.fantasyclient.adapter.BuildingArrayAdapter;
import com.example.fantasyclient.drawable.TextDrawable;
import com.example.fantasyclient.model.Building;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends BaseActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_image_layout);
        findView();
    }

    @Override
    protected void findView(){
        imageView = findViewById(R.id.unitImg);
        imageView.setImageResource(R.drawable.pickachu_back);
        //imageView.setImageDrawable(new TextDrawable(getResources(),"A"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                builder.setTitle("title");
                // add a radio button list
                List<Building> buildingList = new ArrayList<>();
                buildingList.add(new Building("shop"));
                BuildingArrayAdapter adapter = new BuildingArrayAdapter(TestActivity.this, buildingList);

                int checkedItem = 1; // cow
                builder.setSingleChoiceItems(adapter, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user checked an item
                    }
                });
                // add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                    }
                });
                builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
