package com.example.dominik.simplelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswortActivity extends AppCompatActivity {

    EditText Email;
    Button Reset;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwort);

        Email = (EditText)(findViewById(R.id.etPasswordEmail));
        Reset =(Button)(findViewById(R.id.btnReset));
        firebaseAuth = FirebaseAuth.getInstance();


        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = (Email.getText().toString().trim()) + "@yahoo.de";
                if(email == "") Toast.makeText(PasswortActivity.this, "Please Enter Something", Toast.LENGTH_SHORT).show();
                else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PasswortActivity.this, "Reset link is sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswortActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(PasswortActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
             }

        });

    }
}
