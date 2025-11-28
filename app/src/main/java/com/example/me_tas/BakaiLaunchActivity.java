package com.example.me_tas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

public class BakaiLaunchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Spinner spnStartPoint;
    EditText etnumofbakai;
    CardView btndeparture;
    LatLngWrapper place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bakai_launch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
        String[] StartingPoints = new String[]{ "" ,"בחירת מיקום", "מיקומך הנוכחי"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, StartingPoints);
        spnStartPoint.setAdapter(adapter);

        spnStartPoint.setOnItemSelectedListener(this);
    }
    @Override
    public void onClick(View v) {
        if(place!=null){
            //if(!etnumofbakai.getText().toString().isEmpty()){ for (int i=0;i<Integer.parseInt(etnumofbakai.getText().toString());i++){}}

            if(DataManager.getUser_Flight_InAir()==-1){
                DataManager.getPilotFlights().add(new Pilot_Flight(place, FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                DataBaseManager.saveFlightToDB(true);
                Intent intent=new Intent(this,BakaiHistoryActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==1){
            Intent intent=new Intent(this, MapsActivity.class);
            startActivityForResult(intent,0);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            spnStartPoint.setSelection(2);
            double lat = data.getDoubleExtra("LAT",0.0);
            double lng=data.getDoubleExtra("LNG",0.0);
            place=new LatLngWrapper(lat,lng);
        }
    }
    public void InIt(){
        spnStartPoint=findViewById(R.id.spnStartPoint);
        //etnumofbakai =findViewById(R.id.etnumbakai);
        btndeparture=findViewById(R.id.btndeparture);
        btndeparture.setOnClickListener(this);
    }
}