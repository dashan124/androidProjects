package com.example.dashan.uihome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    List<Folders> mFolders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFolders=new ArrayList<>();
        mFolders.add(new Folders("Bank","Categoire","Description",R.drawable.facebook));
        mFolders.add(new Folders("Bank","Categoire","Description",R.drawable.google));
        mFolders.add(new Folders("Bank","Categoire","Description",R.drawable.cercleshape));
        mFolders.add(new Folders("Bank","Categoire","Description",R.drawable.ic_action_user));
        mFolders.add(new Folders("Bank","Categoire","Description",R.drawable.ic_wifi_black_24dp));
        mFolders.add(new Folders("Bank","Categoire","Description",R.drawable.ic_control_point_black_24dp));
        mFolders.add(new Folders("Bank","Categoire","Description",R.drawable.ic_attach_file_black_24dp));

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myadapter=new RecyclerViewAdapter(this,mFolders);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(myadapter);

    }
}
