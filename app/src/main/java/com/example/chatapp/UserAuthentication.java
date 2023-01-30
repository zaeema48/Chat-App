package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class UserAuthentication extends AppCompatActivity {
    EditText otp;
    android.widget.Button verify;
    ProgressBar progressBar;
    TextView changeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);

        otp=findViewById(R.id.number);
        verify=findViewById(R.id.verify);
        progressBar=findViewById(R.id.progressBarOfUser);
        changeNo=findViewById(R.id.changeNumber);

        changeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAuthentication.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //verifying otp
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent(); //make an object of intent to get the otp from MainActivity
                String otp_received= intent.getStringExtra("otp");
                String otp_entered= otp.getText().toString();   //otp entering is of type View, here we are converting that view into string type
                if(otp_entered.isEmpty())
                    Toast.makeText(UserAuthentication.this, "Enter OTP!!", Toast.LENGTH_SHORT).show();
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    //verifying the otp entered and otp received are same or not (only by firebaseAuth method )
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(otp_received, otp_entered);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //task is verification of otp
                        progressBar.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Intent intent= new Intent(UserAuthentication.this, SetProfile.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(UserAuthentication.this, "WRONG OTP!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}