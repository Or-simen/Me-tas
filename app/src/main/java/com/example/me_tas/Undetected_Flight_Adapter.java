package com.example.me_tas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Undetected_Flight_Adapter extends ArrayAdapter{
    Context context;
    Undetected_Flight temp;
    List<Undetected_Flight> objects;
    TextView reason,fullreason,delete,tvappeal,objectionfull;
    EditText appeal;
    CardView reportloc;
    LinearLayout ln;
    boolean IsRavshatz;
    int position;
    Dialog dialog;

    public Undetected_Flight_Adapter(Context context, int resource, int textviewresourceid, List<Undetected_Flight> objects,boolean isravshatz) {
        super(context, resource,textviewresourceid,objects);
        this.context=context;
        this.objects=objects;
        this.IsRavshatz=isravshatz;
    }

    @Override
    public View getView(int positionv, View convertView, ViewGroup parent) {
        LayoutInflater layoutinflater=((Activity)context).getLayoutInflater();
        View view=layoutinflater.inflate(R.layout.report_item_layout,parent,false);
        position=positionv;
        Init(view);

        temp=objects.get(position);
        addMiniMap();
        reason.setText(String.valueOf(temp.getReport_Reason()));
        reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createcreateDialog(null);
            }
        });
        return view;
    }
    private void addMiniMap() {
        // Local variable to hold the location, ensuring the value is captured
        // at this point in time to avoid issues with asynchronous execution.
        LatLngWrapper localLlw;
        localLlw = temp.getStartlocation();
        MapView minimap = new MapView(context);
        minimap.onCreate(null);
        minimap.onResume(); // Necessary to avoid crashes on some devices
        if (localLlw != null) {
            // Asynchronously get the map and set the location
            minimap.getMapAsync(new OnMapReadyCallback() {
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
            ln.addView(minimap);
        } else {
            TextView textView = new TextView(context);
            textView.setText("אין");
            textView.setTextSize(30);
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
            ln.addView(textView);
        }
    }

    private void createcreateDialog(LatLng pos) {
        dialog = new Dialog(context);
        if (pos!=null) {
            dialog.setContentView(R.layout.minimap_dialog);
            dialog.setCancelable(true);
            dialog.getWindow().setLayout(900, 900);
            MapView dialog_map = dialog.findViewById(R.id.mapView);
            dialog_map.onCreate(null);
            dialog_map.onResume();
            dialog_map.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 5));
                    googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, -30));
                    googleMap.addMarker(new MarkerOptions().position(pos));
                }
            });
            dialog.show();
        } else {
            if (IsRavshatz) {
                dialog.setContentView(R.layout.report_delete_dialog);
                dialog.setCancelable(true);
                fullreason = dialog.findViewById(R.id.tvreasonfull);
                fullreason.setText(reason.getText().toString());
                objectionfull = dialog.findViewById(R.id.tvobjectionfull);
                if (temp.getAppeal_Reason() != null && !temp.getAppeal_Reason().isEmpty()) {
                    objectionfull.setText(temp.getAppeal_Reason());
                }
                delete = dialog.findViewById(R.id.tvdelete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        objects.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else {
                dialog.setContentView(R.layout.report_appeal_dialog);
                dialog.setCancelable(true);
                fullreason = dialog.findViewById(R.id.tvfullreason);
                fullreason.setText(reason.getText().toString());
                appeal = dialog.findViewById(R.id.etappeal);
                tvappeal = dialog.findViewById(R.id.tvappeal);
                tvappeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = appeal.getText().toString();
                        if (!text.isEmpty()) {
                            temp.setAppeal_Reason(text);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        }
    }

    private void Init(View view){
        reason=view.findViewById(R.id.loreason);
        reportloc= view.findViewById(R.id.loreportlocation);
        ln=view.findViewById(R.id.startln);
    }
}
