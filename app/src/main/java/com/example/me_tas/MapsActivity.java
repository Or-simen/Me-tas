package com.example.me_tas;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.me_tas.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Marker currentMarker;
    boolean isPlacingMarker = false;
    ImageButton btnmarkstart;
    Dialog dialog;
    CardView cvyes,cvno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        InIt();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        LatLng isreal = new LatLng(32.706570648941394, 35.10618107001763);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(isreal));
    }

    @Override
    public void onClick(View v) {
        if(v==btnmarkstart){
            if (!isPlacingMarker){
                btnmarkstart.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.location_transparant));
                isPlacingMarker=true;
            }
        }
        if(v==cvno){
            currentMarker.remove();
            currentMarker = null;
            isPlacingMarker=false;
            btnmarkstart.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.location_red));
            dialog.dismiss();
        }
        if(v==cvyes){
            dialog.dismiss();
            Intent intent=new Intent();
            intent.putExtra("LAT",currentMarker.getPosition().latitude);
            intent.putExtra("LNG",currentMarker.getPosition().longitude);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (isPlacingMarker) {
            currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(""));
            createcreateDialog();
        }
    }
    public void createcreateDialog(){
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.certinty_dialog_layout);
        dialog.setCancelable(false);
        cvno=dialog.findViewById(R.id.no);
        cvno.setOnClickListener(this);
        cvyes=dialog.findViewById(R.id.yes);
        cvyes.setOnClickListener(this);
        dialog.show();
    }
    public void InIt(){
        btnmarkstart=findViewById(R.id.btnmarkstart);
        btnmarkstart.setOnClickListener(this);
    }
}