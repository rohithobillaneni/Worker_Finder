package com.example.worker_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WorkerDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details);
        Intent from=getIntent();
        ((TextView)findViewById(R.id.name)).setText(from.getStringExtra("name"));
        ((TextView)findViewById(R.id.work)).setText(from.getStringExtra("work"));
        ((TextView)findViewById(R.id.cost)).setText("cost : "+from.getStringExtra("cost"));
        ((TextView)findViewById(R.id.address)).setText(from.getStringExtra("address"));
        ((TextView)findViewById(R.id.phone)).setText(from.getStringExtra("phone"));
    }
}
