package com.example.me_tas;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ReportListActivity extends AppCompatActivity {
    ListView lvreports;
    Undetected_Flight_Adapter undetectedFlightAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
    }

    public void InIt(){
        lvreports=findViewById(R.id.lvreports);
        undetectedFlightAdaptor=new Undetected_Flight_Adapter(this,0,0
                ,DataManager.getUndetectedFlights() , getIntent().getBooleanExtra("RAVSHATZ",false));
        lvreports.setAdapter(undetectedFlightAdaptor);
    }
}