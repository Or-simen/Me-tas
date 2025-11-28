package com.example.me_tas;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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


public class BakaiArrivalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spnendpoint;
    EditText etnumofbakai;
    CardView btnarrival;
    LatLngWrapper place;
    int index;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bakai_arrival);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
        String[] StartingPoints = new String[]{ "" ,"בחירת מיקום", "מיקומך הנוכחי"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, StartingPoints);
        spnendpoint.setAdapter(adapter);

        spnendpoint.setOnItemSelectedListener(this);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    private void onFlightLand() {
        // Create an Intent to trigger the BroadcastReceiver
        DataManager.setAlarm_managed_index(index);
        Intent intent = new Intent(this, FlightRemovelReciever.class);
        intent.putExtra("LAT",place.getLatitude());
        intent.putExtra("LNG",place.getLongitude());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the Alarm to trigger after 5 minutes (300,000 milliseconds)
        long triggerAtMillis = System.currentTimeMillis() + 10000;

        DataManager.getPilotFlights().get(index).setIn_air(false);
        DataManager.getPilotFlights().get(index).setEndlocation(place);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }

    @Override
    public void onClick(View v) {
        if(place!=null){
            //if(!etnumofbakai.getText().toString().isEmpty()){for (int i=0;i<Integer.parseInt(etnumofbakai.getText().toString());i++){}}
            String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
            index=DataManager.getUser_Flight_InAir();
            if (index!=-1) {
                DataBaseManager.updateFlightState(index,false,place);
                onFlightLand();
                Intent intent = new Intent();
                intent.putExtra("INDEX", index);
                setResult(Activity.RESULT_OK, intent);
            }
            else{
                Pilot_Flight pf1= new Pilot_Flight(null, place,email);
                pf1.setIn_air(false);
                DataManager.getPilotFlights().add(pf1);
                DataBaseManager.saveFlightToDB(true);
            }
            finish();
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
            spnendpoint.setSelection(2);
            place=new LatLngWrapper(data.getDoubleExtra("LAT",0.0),data.getDoubleExtra("LNG",0.0));
        }
    }
    public void InIt(){
        btnarrival=findViewById(R.id.btnarrival);
        btnarrival.setOnClickListener(this);
        spnendpoint=findViewById(R.id.spnendPoint);
    }

}