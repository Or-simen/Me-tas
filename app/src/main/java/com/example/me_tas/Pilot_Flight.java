package com.example.me_tas;

import com.google.android.gms.maps.model.LatLng;

public class Pilot_Flight extends Flight {
    private String Email;

    public Pilot_Flight(LatLngWrapper startlocation, String email) {
        this.startlocation = startlocation;
        this.in_air = true;
        this.endlocation = null;
        this.Email = email;
    }

    public Pilot_Flight(LatLngWrapper startlocation, LatLngWrapper endlocation, String email) {
        this.endlocation = endlocation;
        this.in_air = false;
        this.startlocation = startlocation;
        this.Email = email;
    }

    public Pilot_Flight(){

    }

    public String getEmail() {return Email;}
    public void setEmail(String email) {this.Email = email;}

}
