package com.example.me_tas;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Undetected_Flight extends Flight{
    private String report_Reason;
    private String appeal_Reason;

    public Undetected_Flight(LatLngWrapper startlocation, String report_Reason) {
        setStartlocation(startlocation);
        this.report_Reason = report_Reason;
        this.appeal_Reason=null;
    }
    public Undetected_Flight(){}

    public String getReport_Reason() {
        return report_Reason;
    }

    public void setReport_Reason(String report_Reason) {
        this.report_Reason = report_Reason;
    }

    public String getAppeal_Reason() {
        return appeal_Reason;
    }

    public void setAppeal_Reason(String appeal_Reason) {
        this.appeal_Reason = appeal_Reason;
    }
}
