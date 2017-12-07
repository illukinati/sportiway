package com.example.asus.sportiway.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.asus.sportiway.Adapter.EventAdapter;
import com.example.asus.sportiway.Adapter.HistoryAdapter;
import com.example.asus.sportiway.Model.Events;
import com.example.asus.sportiway.Model.History;
import com.example.asus.sportiway.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ArrayList<History> histories;
    RecyclerView rvHistory;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvHistory = (RecyclerView) findViewById(R.id.rvHistory);
        histories = History.createEventList(20);
        HistoryAdapter adapter = new HistoryAdapter(this, histories);
        rvHistory.setAdapter(adapter);
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
