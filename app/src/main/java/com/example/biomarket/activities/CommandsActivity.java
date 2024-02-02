package com.example.biomarket.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.biomarket.R;
import com.example.biomarket.adapters.LinkAdapter;
import com.example.biomarket.database.DBProductResume;
import com.example.biomarket.models.LinkModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CommandsActivity extends AppCompatActivity {
    FirebaseFirestore db;

    RecyclerView recyclerViewLink;
    List<LinkModel> linkModelList = new ArrayList<>();
    LinkAdapter linkAdapter;
    TextView topTitle;
    View backSpace;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(R.layout.custom_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);


        View customView = actionBar.getCustomView();
        topTitle = customView.findViewById(R.id.titleOfActionBar); // Assuming this is the ID of your title TextView
        backSpace = customView.findViewById(R.id.backSpace); // Assuming this is the ID of your title TextView
        topTitle.setText("My Commands");
        backSpace.setVisibility(View.GONE);



        db = FirebaseFirestore.getInstance();

        recyclerViewLink = findViewById(R.id.recycleViewLinks);

        linkAdapter = new LinkAdapter(getApplicationContext(), linkModelList);

        recyclerViewLink.setAdapter(linkAdapter);


        DBProductResume DBProductResume = new DBProductResume();
        DBProductResume.getCommandData(linkModelList, linkAdapter);


    }
}