package com.example.me_tas;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    
    public static  DatabaseReference getListReference(String list_name){
        return FirebaseDatabase.getInstance().getReference(list_name);
    }

    public static void saveFlightToDB(boolean isdetected){
        int size;
        if (isdetected){
            size=DataManager.getPilotFlights().size()-1;
            getListReference("pilot_flights_list").child(String.valueOf(size))
                    .setValue(DataManager.getPilotFlights().get(size));
        }
        else {
            size=DataManager.getUndetectedFlights().size()-1;
            getListReference("undetected_fligts_list").child(String.valueOf(size))
                    .setValue(DataManager.getUndetectedFlights().get(size));
        }
    }

    public static void updateFlightState(int position,boolean is_in_air,LatLngWrapper latLngWrapper){
        getListReference("pilot_flights_list").child(String.valueOf(position)).child("in_air")
                .setValue(is_in_air);
        getListReference("pilot_flights_list").child(String.valueOf(position)).child("endlocation")
                .setValue(latLngWrapper);
    }

    public static void readList(boolean isdetected){
        if(isdetected){
            getListReference("pilot_flights_list").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<ArrayList<Pilot_Flight>> type = new GenericTypeIndicator<ArrayList<Pilot_Flight>>() {};
                    DataManager.setPilotFlights(snapshot.getValue(type));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            getListReference("undetected_fligts_list").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<ArrayList<Undetected_Flight>> type = new GenericTypeIndicator<ArrayList<Undetected_Flight>>() {};
                    DataManager.setUndetectedFlights(snapshot.getValue(type));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }


}