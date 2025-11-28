package com.example.me_tas;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FlightRemovelReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        LatLngWrapper latLngWrapper=new LatLngWrapper(intent.getDoubleExtra("LAT",0.0),intent.getDoubleExtra("LNG",0.0));

        DataBaseManager.updateFlightState(DataManager.getAlarm_managed_index(),false,latLngWrapper);
        DataManager.setAlarm_managed_index(-1);
    }
}
