package com.example.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


public class ProfileFragment extends Fragment {
ImageView profile;
TextView username;
android.widget.Button logout, updateProfile;

FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        profile=view.findViewById(R.id.getUserImageinImageView);
        username=view.findViewById(R.id.getUserName);
        logout=view.findViewById(R.id.logout);
        updateProfile=view.findViewById(R.id.profileUpd);
        firebaseAuth= FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut(); //signOut from firebase is necessary
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SetProfile.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        String user_id= firebaseAuth.getUid();  //storing user id of the current user to avoid making firebase instances again and again

//       fetching the profile from firebase storage
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        //getting the location of the profile image from firebaseStorage
//        firebaseStorage.getReference().child("Profile").child(user_id)
//                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        String image= uri.toString();
//                        Glide.with(getContext()).load(uri).into(profile);
//                    }
//                });

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("users").child(user_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user= snapshot.getValue(User.class) ;
                        username.setText(user.getUser_name());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return view;
    }
}