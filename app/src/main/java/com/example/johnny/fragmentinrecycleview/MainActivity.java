package com.example.johnny.fragmentinrecycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RV_Freag_Adapter adapter;
    List<ImageModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.fragment_recycleview);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(layoutManager);

        //seting data
        data.add(new ImageModel(R.drawable.bad_idea,"this is mySource",
                "this is first shit .. bla bla bla bla qbla ablab alb alb alba"));
        data.add(new ImageModel(R.drawable.shit,"this is mySource",
                "this is 2nd shit "));
        data.add(new ImageModel(R.drawable.ic_launcher_background,"this is mySource",
                "this is 3rd shit .. bla bla "));
        data.add(new ImageModel(R.drawable.shit,"this is mySource",
                "this is 4th shit .. bla bla bla bla qbla ablab alb alb alba  qbla ablab alb alb alba"));

        adapter = new RV_Freag_Adapter(MainActivity.this,data);
        recyclerView.setAdapter(adapter);
    }
}
