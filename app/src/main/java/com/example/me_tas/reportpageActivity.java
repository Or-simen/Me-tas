package com.example.me_tas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class reportpageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spnlocation;
    CardView btnreport;
    EditText etreason;
    LatLngWrapper place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reportpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
        btnreport.setOnClickListener(this);
        String[] locs = new String[]{ "" ,"בחירת מיקום", "מיקומך הנוכחי"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,locs);
        spnlocation.setAdapter(adapter);

        spnlocation.setOnItemSelectedListener(this);
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
    public void onClick(View v) {
        if(spnlocation.getSelectedItemPosition()==2){
            if(place!=null){
                DataManager.getUndetectedFlights().add(new Undetected_Flight(place,etreason.getText().toString()));
                DataBaseManager.saveFlightToDB(false);
                setResult(Activity.RESULT_OK,null);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            spnlocation.setSelection(2);
            place=new LatLngWrapper(data.getDoubleExtra("LAT",0.0),data.getDoubleExtra("LNG",0.0));
        }
    }
    public void InIt(){
        etreason=findViewById(R.id.etreason);
        spnlocation=findViewById(R.id.spnlocation);
        btnreport=findViewById(R.id.btnreport);
    }
}