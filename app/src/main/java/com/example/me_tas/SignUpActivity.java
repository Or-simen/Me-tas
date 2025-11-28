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

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth auth;
    LinearLayout ln;
    EditText etEmail,etpassword;
    CardView btnenter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InIt();
    }
    @Override
    public void onClick(View v) {
        if(v==btnenter) {
            SignUp();
        }
    }

    public void SignUp(){
        String email = etEmail.getText().toString();
        String password = etpassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            CreateErrorMessage("אין אימייל או סיסמא");
        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            intent=new Intent(this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Exception exception=task.getException();
                            if(exception instanceof FirebaseAuthInvalidCredentialsException){
                                CreateErrorMessage("אימייל או סיסמא לא נכונים");
                            }
                            else if (exception instanceof FirebaseAuthUserCollisionException) {
                                CreateErrorMessage("משתמש כבר קיים");
                            }
                        }
                    });
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
        etEmail=findViewById(R.id.etEmail);
        etpassword=findViewById(R.id.etpassword);
        ln=findViewById(R.id.ln);
        btnenter=findViewById(R.id.btnenter);
        btnenter.setOnClickListener(this);
    }
}