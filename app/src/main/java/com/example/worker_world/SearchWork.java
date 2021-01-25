package com.example.worker_world;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.example.worker_world.MainActivity.workers;
import static com.example.worker_world.MainActivity.works;

public class SearchWork extends AppCompatActivity implements SearchWorkerAdapter.customClick {
    public static double curLatRad;
    public static double curLongRad;
    public static double tempLatRad;
    public static double tempLongRad;
    public static double curLat;
    public static double curLong;
    private  static AutoCompleteTextView search;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    RecyclerView recyclerView;
    public static SearchWorkerAdapter searchWorkerAdapter;
    public static List<Worker> searchedworkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_work);

        search=(AutoCompleteTextView) findViewById(R.id.search);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new ArrayList(works));
        search.setAdapter(adapter);

        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchedworkers= new ArrayList<>();
        searchWorkerAdapter=new SearchWorkerAdapter(this,searchedworkers,this);
        recyclerView.setAdapter(searchWorkerAdapter);


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
        getLocation();

    }

    public static void sort(List<Worker> list, int from, int to) {
        if (from < to) {
            int pivot = from;
            int left = from + 1;
            int right = to;
            double pivotValue = getDistance(list.get(pivot));
            while (left <= right) {
                // left <= to -> limit protection
                while (left <= to && pivotValue >= getDistance(list.get(left))) {
                    left++;
                }
                // right > from -> limit protection
                while (right > from && pivotValue < getDistance(list.get(right))) {
                    right--;
                }
                if (left < right) {
                    Collections.swap(list, left, right);
                }
            }
            Collections.swap(list, pivot, left - 1);
            sort(list, from, right - 1); // <-- pivot was wrong!
            sort(list, right + 1, to);   // <-- pivot was wrong!
        }
    }

    public static double getDistance(Worker worker) {
        tempLatRad = Math.toRadians(worker.getLatitude());
        tempLongRad = Math.toRadians(worker.getLongitude());
        double dlat = tempLatRad - curLatRad;
        double dlong = tempLongRad - curLongRad;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(curLatRad) * Math.cos(tempLatRad)
                * Math.pow(Math.sin(dlong / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;
        return (c * r);
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                SearchWork.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                SearchWork.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                curLat = locationGPS.getLatitude();
                curLong = locationGPS.getLongitude();
                curLatRad=Math.toRadians(curLat);
                curLongRad=Math.toRadians(curLong);
                for (Worker worker:workers)
                {
                    System.out.println(worker.getName()+curLat+curLong);
                }
                sort(workers,0,workers.size()-1);
                for (Worker worker:workers)
                {
                    System.out.println(worker.getName());
                }
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void search(View view)
    {
        searchedworkers.clear();
        for(Worker worker:workers)
        {
            if(worker.getWork().equalsIgnoreCase(search.getText().toString()))
            {
                searchedworkers.add(worker);
            }
        }
        searchWorkerAdapter.notifyDataSetChanged();
    }

    @Override
    public void oncustomclick(int position) {

        Worker selectedworker=searchedworkers.get(position);
        Intent intent=new Intent(this,WorkerDetails.class);
        intent.putExtra("name",selectedworker.getName());
        intent.putExtra("work",selectedworker.getWork());
        intent.putExtra("cost",selectedworker.getCost());
        intent.putExtra("phone",selectedworker.getContactno());
        intent.putExtra("address",selectedworker.getAddress());
        startActivity(intent);

    }
}
