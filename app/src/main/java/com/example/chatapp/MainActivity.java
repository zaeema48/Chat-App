package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.storage.FirebaseStorage;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText phoneNo;
    android.widget.Button otp;
    ProgressBar progressBar;
    CountryCodePicker countryCode;
    String countryCODE;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNo=findViewById(R.id.number);
        otp=findViewById(R.id.sendOtp);
        progressBar=findViewById(R.id.progressBar);
        countryCode=findViewById(R.id.countryCodeHolder);

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance(); //all the methods of Firebase class can be accessed by its object

        countryCODE=countryCode.getSelectedCountryCodeWithPlus();
        //IF CHANGING THE COUNTRY CODE
        countryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCODE=countryCode.getSelectedCountryCodeWithPlus();
            }
        });

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number;
                number=phoneNo.getText().toString();
                if(number==null){
                    Toast.makeText(MainActivity.this, "Enter number!!", Toast.LENGTH_SHORT).show();
                }
                else if(number.length()!=10){
                    Toast.makeText(MainActivity.this, "Enter correct number!", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    number=countryCODE+number;

                    //Verifying the phone number (predefined class)
                    PhoneAuthOptions options= PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(number)
                            .setActivity(MainActivity.this)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setCallbacks(callBack)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });


//if number is verified and otp is sent, then verifying the otp. onCodeSent is firebase method which gives encrypted form of otp(s)
        callBack= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this, UserAuthentication.class);
                intent.putExtra("otp", s);
                startActivity(intent);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
        };
    }

    //Method to check whether the user is already log in
    @Override
    protected void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent= new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }


}