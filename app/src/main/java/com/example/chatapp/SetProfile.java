package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chatapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetProfile extends AppCompatActivity {
    ImageView profile;
    EditText user_name;
    android.widget.Button save_profile;
    ProgressBar progressBar;

    Uri imageURI;
    //creating instances of firebase
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        profile=findViewById(R.id.user_profile);
        user_name=findViewById(R.id.getUserName);
        progressBar=findViewById(R.id.progressBar);
        save_profile=findViewById(R.id.saveProfile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Steps for fetching the image in the phone
                Intent intent = new Intent ();
                intent.setAction(Intent.ACTION_GET_CONTENT); //GET_CONTENT will provide all the media from the phone
                intent.setType("image/*"); //defining which type of data is needed. fetch images from phone media
                startActivityForResult(intent, 30);
            }
        });

        //creating instances of firebase
         firebaseAuth= FirebaseAuth.getInstance();
         firebaseStorage= FirebaseStorage.getInstance();
         firebaseDatabase= FirebaseDatabase.getInstance();

        //storing the user details in firebase storage and database
        save_profile.setOnClickListener(new View.OnClickListener() {
            String user_id, username, phone_no, image_link;
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                username = user_name.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(SetProfile.this, "Enter Your Name!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(imageURI!=null){
                    //get the location where the images are being stored
                    StorageReference image_location= firebaseStorage.getReference().child("Profile").child(firebaseAuth.getUid());
                    image_location.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                image_location.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        image_link=uri.toString();
                                    }
                                });
                            }
                        }
                    });
                }
                else{
                    image_link= "NO IMAGE";
                }

                user_id= firebaseAuth.getUid();
                phone_no=firebaseAuth.getCurrentUser().getPhoneNumber();
                User user = new User(user_id, phone_no, username, image_link);

                firebaseDatabase.getReference()
                        .child("users")     //child is the folder in firebase
                        .child(user_id)
                        .setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent= new Intent(SetProfile.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

            }
        });

    }

    //we need to override onActivityResult method to set the dp in the profile
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            profile.setImageURI(data.getData());
            imageURI=data.getData();
        }
    }
}