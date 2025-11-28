package com.example.me_tas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class InAirBakaiActivity  extends AppCompatActivity {
    ListView lvflights;
    TextView tvnum;
    In_Air_Flight_Adaptor flightadaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_in_air_bakai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
        if(DataManager.getPilotFlights()!=null){
            tvnum.setText(Integer.toString(DataManager.getIn_Air_Flights().toArray().length));
        }
        flightadaptor=new In_Air_Flight_Adaptor(this,0,0
                ,DataManager.getIn_Air_Flights());
        lvflights.setAdapter(flightadaptor);

    }


    public void InIt(){
        lvflights=findViewById(R.id.lvAllFlights);
        tvnum=findViewById(R.id.tv2);
    }
}