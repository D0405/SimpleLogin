package com.example.dominik.simplelogin;



seid ihr alle behindert?
ja, seid ihr

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Passwort;
    private TextView Info;
    private Button Login;
    private int count = 5;
    private TextView Registration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView ForgotPass;
    private ImageView bild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Passwort = (EditText)findViewById(R.id.etUserPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        Registration = (TextView)findViewById(R.id.tvRegister);
        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        bild = (ImageView) findViewById(R.id.imBild);
        ForgotPass = (TextView)(findViewById(R.id.ForgotPassword));

        FirebaseUser user = firebaseAuth.getCurrentUser(); //checks if a user is already loged in

        if(user != null){//user already logged in
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                validate(Name.getText().toString(), Passwort.getText().toString());
            }
        });

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, PasswortActivity.class));
            }
        });

    }



    private void validate(String userName, String userPasswort){

        progressDialog.setMessage("Loading");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName + "@gmail.com", userPasswort).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailVerifiaction();
                }else{
                    progressDialog.dismiss();
                    count--;
                    Info.setText("No of attempts remaining: " + count);
                    Toast.makeText(MainActivity.this, "Login is not successfull", Toast.LENGTH_SHORT).show();
                    if(count == 0)Login.setEnabled(false);
                }
            }
        });
    }

    private void checkEmailVerifiaction(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        startActivity(new Intent(MainActivity.this, SecondActivity.class));

    /*
        if(emailFlag){
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
            finish();
        }
        else{
            Toast.makeText(this, "Please verify your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }*/
    }
}
