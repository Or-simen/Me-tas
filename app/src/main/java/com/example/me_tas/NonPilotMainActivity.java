package com.example.me_tas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class NonPilotMainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView btnlister,btnreporter,btnreportlist;
    LinearLayout llreportlistbtn;
    TextView tvreportnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_non_pilot_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            BtnToRed();
        }
    }

    @Override
    public void onClick(View v) {
        if(v==btnreportlist){
            if(!DataManager.getUndetectedFlights().isEmpty()){
                MoveToActivity(ReportListActivity.class,false);
            }
        }
        if(v==btnreporter){
            MoveToActivity(reportpageActivity.class,true);
        }
        if(v==btnlister){
            MoveToActivity(InAirBakaiActivity.class,false);
        }
    }
    public void InIt(){
        Intent serviceIntent = new Intent(NonPilotMainActivity.this, DataChgangesService.class);
        startService(serviceIntent);
        btnlister=findViewById(R.id.btnLister);
        btnlister.setOnClickListener(this);
        btnreporter=findViewById(R.id.btnReporter);
        btnreporter.setOnClickListener(this);
        btnreportlist =findViewById(R.id.btnreportlist);
        btnreportlist.setOnClickListener(this);
        llreportlistbtn=findViewById(R.id.llreportlistbtn);
        tvreportnum=findViewById(R.id.tvreportnum);
        if(!DataManager.getUndetectedFlights().isEmpty()){
            BtnToRed();
        }
    }

    private void MoveToActivity(Class<? extends Activity> activity, boolean isforresult){
        Intent intent=new Intent(this, activity);
        if(isforresult){
            startActivityForResult(intent,0);
        }
        else {
            intent.putExtra("RAVSHATZ",true);
            startActivity(intent);
        }

    }
    public void BtnToRed(){
        llreportlistbtn.setBackgroundResource(R.drawable.alert_button_red);
        tvreportnum.setText(DataManager.getUndetectedFlights().size()+" התרעות");
    }
}