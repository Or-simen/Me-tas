package com.example.me_tas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class BakaiHistoryActivity extends AppCompatActivity {
    ListView lvflights;
    TextView tvnum;
    Flight_Adaptor flightadaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bakai_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
        lvflights.setAdapter(flightadaptor);
    }


    //arraylist with only flights that have a start
    public ArrayList<Pilot_Flight> getstarters(){
        ArrayList<Pilot_Flight> pilotFlights1=new ArrayList<>();
        for (int i=0;i<DataManager.getUsersFlights().size();i++){
            if(DataManager.getUsersFlights().get(i).getStartlocation()!=null){
                pilotFlights1.add(DataManager.getUsersFlights().get(i));
            }
        }
        return pilotFlights1;
    }

    public void InIt(){
        lvflights=findViewById(R.id.lvLastFlights);
        tvnum=findViewById(R.id.tv2);
        if(getstarters()!=null){
            //using .toarray.length because ,if i use .size() it gives exception check why later
            tvnum.setText(Integer.toString(getstarters().toArray().length));
        }
        else{
            //תוסיף טקסט שאין רשימה
        }
        flightadaptor=new Flight_Adaptor(this,0,0,getstarters());
    }
}