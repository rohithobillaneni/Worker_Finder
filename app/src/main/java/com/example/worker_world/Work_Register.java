package com.example.worker_world;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

import static com.example.worker_world.MainActivity.databaseworkers;
import static com.example.worker_world.MainActivity.works;

public class Work_Register extends AppCompatActivity {
    private static  String Address;
    private static Double Lat;
    private static  Double Long;

    private  static EditText ename;
    private  static AutoCompleteTextView ework;
    private  static EditText ecost;
    private  static EditText emobileno;
    private static TextView elocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work__register);
        ename=(EditText)findViewById(R.id.name);
        ework=(AutoCompleteTextView)findViewById(R.id.workname);
        ecost=(EditText)findViewById(R.id.cost);
        emobileno=(EditText)findViewById(R.id.mobileno);
        elocation=(TextView)findViewById(R.id.locshow);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new ArrayList(works));
        ework.setAdapter(adapter);

    }
    public void getLocation(View view)
    {
        Intent i= new Intent(this,LocationPicker.class);
        startActivityForResult(i,1101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1101)
        {
            if(resultCode==RESULT_OK)
            {
                Lat=data.getDoubleExtra("Lat",0);
                Long=data.getDoubleExtra("Long",0);
                Address=data.getStringExtra("Address");
                elocation.setText(Address);
                elocation.setVisibility(View.VISIBLE);

            }
        }
    }
    public void register(View view)
    {
        String id=databaseworkers.push().getKey();
        databaseworkers.child(id).setValue(new Worker(ename.getText().toString(),ework.getText().toString(),ecost.getText().toString(),emobileno.getText().toString(),Address,Lat,Long));

        Intent intent=new Intent(this,SuccessfulRegistration.class);
        startActivity(intent);
    }
}
