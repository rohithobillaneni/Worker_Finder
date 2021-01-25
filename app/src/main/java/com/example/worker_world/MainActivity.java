package com.example.worker_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    public static DatabaseReference databaseworkers;
    public static Set<String> works;
    public static List<Worker> workers;
    private final static int LOCATION_REQUEST_CODE = 23;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_REQUEST_CODE);
        workers = new ArrayList<>();
        works= new HashSet<String>();

        databaseworkers = FirebaseDatabase.getInstance().getReference("workers");
        databaseworkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workers.clear();
                works.clear();
                for (DataSnapshot workersnapshot : dataSnapshot.getChildren()) {
                    Worker worker = workersnapshot.getValue(Worker.class);
                    works.add(worker.getWork());
                    workers.add(worker);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void register(View view) {
        Intent i = new Intent(this, Work_Register.class);
        startActivity(i);
    }

    public void searchwork(View view) {
        Intent i = new Intent(this, SearchWork.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "location permission need", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                            LOCATION_REQUEST_CODE);
                }
                return;
            }
        }
    }


}
