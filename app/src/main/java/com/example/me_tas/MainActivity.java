package com.example.me_tas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView btnlister,btnTakeoffreporter,btnreporter,btnlasttakeoffs,btnreportlist,btnarrivalreport;
    LinearLayout llreportlistbtn;
    TextView tvreportnum;
    int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
            if(data!=null){
                index=data.getIntExtra("INDEX",-1);
            }
            else{
                BtnToRed();
            }
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
        if(v==btnTakeoffreporter){
            MoveToActivity(BakaiLaunchActivity.class,false);
        }
        if(v==btnarrivalreport){
            MoveToActivity(BakaiArrivalActivity.class,true);
        }
        if(v==btnlister){
            MoveToActivity(InAirBakaiActivity.class,false);
        }
        if(v==btnlasttakeoffs){
            MoveToActivity(BakaiHistoryActivity.class,false);
        }
    }

    private void InIt(){
        Intent serviceIntent = new Intent(MainActivity.this, DataChgangesService.class);
        startService(serviceIntent);
        btnlister=findViewById(R.id.btnLister);
        btnlister.setOnClickListener(this);
        btnTakeoffreporter=findViewById(R.id.btnTakeoffreporter);
        btnTakeoffreporter.setOnClickListener(this);
        btnarrivalreport=findViewById(R.id.btnarrivalreporter);
        btnarrivalreport.setOnClickListener(this);
        btnreporter=findViewById(R.id.btnReporter);
        btnreporter.setOnClickListener(this);
        btnlasttakeoffs=findViewById(R.id.btnLastTakeOffs);
        btnlasttakeoffs.setOnClickListener(this);
        btnreportlist =findViewById(R.id.btnreportlist);
        btnreportlist.setOnClickListener(this);
        llreportlistbtn=findViewById(R.id.llreportlistbtn);
        tvreportnum=findViewById(R.id.tvreportnum);
        if(!DataManager.getUndetectedFlights().isEmpty()){
            BtnToRed();
        }
    }

    private void MoveToActivity(Class<? extends Activity> activity,boolean isforresult){
        Intent intent=new Intent(this, activity);
        if(index!=-1){
            intent.putExtra("INDEX",index);
        }
        if(isforresult){
            startActivityForResult(intent,0);
        }
        else {
            startActivity(intent);
        }

    }

    private void BtnToRed(){
        llreportlistbtn.setBackgroundResource(R.drawable.alert_button_red);
        tvreportnum.setText(DataManager.getUndetectedFlights().size()+" התרעות");
    }

}