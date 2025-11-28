package com.example.me_tas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    CardView btnenter,btntosignup;
    EditText etEmail,etpassword;
    LinearLayout ln;
    Intent intent;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
    }

    @Override
    public void onClick(View v) {
        if(v==btnenter){
            LogIn(etEmail.getText().toString() ,etpassword.getText().toString());
        }
        else if(v==btntosignup){
            intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }
    public void LogIn(String email, String password){
        if(password.equals(DataManager.getRavshatzcode())){
            intent=new Intent(this,NonPilotMainActivity.class);
            startActivity(intent);
        }
        else{
            if (email.isEmpty() || password.isEmpty()) {
                CreateErrorMessage("אין אימייל או סיסמא");
            }
            else {
                auth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                            intent=new Intent(this,MainActivity.class);
                            startActivity(intent);
                            }
                            else {
                                Exception exception=task.getException();
                                if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                    CreateErrorMessage("אימייל או סיסמה שגויים");
                                } else {
                                    // Some other unexpected error
                                    CreateErrorMessage("שגיאה לא צפויה");
                                }
                            }
                        });
            }
        }
    }

    public void CreateErrorMessage(String text){
        ln.removeAllViews();
        TextView textView=new TextView(this);
        textView.setTextSize(40);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setText(text);
        ln.addView(textView);
    }


    public void InIt(){
        auth=FirebaseAuth.getInstance();
        DataBaseManager.readList(true);
        DataBaseManager.readList(false);
        btnenter=findViewById(R.id.btnenter);
        btnenter.setOnClickListener(this);
        btntosignup=findViewById(R.id.btntosignup);
        btntosignup.setOnClickListener(this);
        etEmail=findViewById(R.id.etEmail);
        etpassword=findViewById(R.id.etpassword);
        ln=findViewById(R.id.ln);
    }
}