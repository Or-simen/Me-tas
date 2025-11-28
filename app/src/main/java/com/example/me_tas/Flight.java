package com.example.me_tas;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

public abstract class Flight {
    protected LatLngWrapper startlocation;
    protected LatLngWrapper endlocation;
    protected boolean in_air;

    public LatLngWrapper getStartlocation() {
        return startlocation;
    }

    public void setStartlocation(LatLngWrapper startlocation) {
        this.startlocation = startlocation;
    }

    public LatLngWrapper getEndlocation() {
        return endlocation;
    }
    public void setEndlocation(LatLngWrapper endlocation) {
        this.endlocation = endlocation;
    }

    public boolean isIn_air() {return in_air;}
    public void setIn_air(boolean in_air) {
        this.in_air = in_air;
    }
}