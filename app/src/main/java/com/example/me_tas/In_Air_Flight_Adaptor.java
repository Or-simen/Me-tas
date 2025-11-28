package com.example.me_tas;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class In_Air_Flight_Adaptor extends ArrayAdapter<Pilot_Flight> {
    Context context;
    List<Pilot_Flight> objects;
    Flight temp;
    View view;
    Dialog dialog;
    TextView state;
    LinearLayout ln;
    CardView cardView;

    public In_Air_Flight_Adaptor(Context context, int resource, int textviewresourceid, List<Pilot_Flight> objects){
        super(context,resource,textviewresourceid,objects);
        this.context=context;
        this.objects=objects;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutinflater=((Activity)context).getLayoutInflater();
        view=layoutinflater.inflate(R.layout.in_air_bakai_flights_layout,parent,false);
        Init();

        temp=objects.get(position);
        addMiniMap(true);
        addMiniMap(false);
        StateChanger(temp,state);
        return view;
    }

    private void createcreateDialog(LatLng pos) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.minimap_dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(900, 900); // Set the dialog size to 800x800 (square)
        MapView dialog_map = dialog.findViewById(R.id.mapView);
        dialog_map.onCreate(null);
        dialog_map.onResume(); // Make sure to call onResume to avoid crashes
        dialog_map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 5));
                googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, -30)); // Adjust the vertical offset here
                googleMap.addMarker(new MarkerOptions().position(pos));
            }
        });
        dialog.show();
    }


    private void addMiniMap(boolean isstart) {
        // Local variable to hold the location, ensuring the value is captured
        // at this point in time to avoid issues with asynchronous execution.
        LatLngWrapper localLlw;
        if (isstart) {
            localLlw = temp.getStartlocation();
            ln = view.findViewById(R.id.startln);
            cardView=view.findViewById(R.id.lostartPoint);
        } else {
            localLlw = temp.getEndlocation();
            ln = view.findViewById(R.id.endln);
            cardView=view.findViewById(R.id.loendPoint);
        }
        MapView mapView = new MapView(context);
        mapView.onCreate(null);
        mapView.onResume(); // Necessary to avoid crashes on some devices
        if (localLlw != null) {
            // Asynchronously get the map and set the location
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    LatLng position = new LatLng(localLlw.getLatitude(), localLlw.getLongitude());
                    // Disables the toolbar which can include the "View in Google Maps" button.
                    // necessary for clicking the minimap because the button just appears without it and blocks clicking
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    //doing both onmap and onmarker to ensure every click would result in the dialog
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(@NonNull LatLng latLng) {
                            createcreateDialog(position);
                        }
                    });
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            createcreateDialog(position);
                            return false;
                        }
                    });
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 5));
                    googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, -30)); // Adjust the vertical offset here
                    googleMap.addMarker(new MarkerOptions().position(position));
                }
            });
            ln.addView(mapView);
        } else {
            TextView textView = new TextView(context);
            textView.setText("אין");
            textView.setTextSize(30);
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            ln.addView(textView);
        }
    }

    private void StateChanger(Flight f,TextView tv){
        if (f.isIn_air()) {
            tv.setText("באוויר");
        } else {
            tv.setText("נחת");
        }
    }
    private void Init(){
        state=view.findViewById(R.id.lostate);
    }
}
