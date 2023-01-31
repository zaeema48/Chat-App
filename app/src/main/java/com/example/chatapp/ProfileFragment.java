package com.example.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
FirebaseStorage firebaseStorage;
FirebaseDatabase firebaseDatabase;

    @Nullable //gives the variable to hold null value
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile, null);
        profile=view.findViewById(R.id.getUserImageinImageView);
        username=view.findViewById(R.id.getUserName);
        logout=view.findViewById(R.id.logout);
        updateProfile=view.findViewById(R.id.profileUpd);


        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();

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
                Intent intent = new Intent(getContext(), SetProfile.class); // we are passing getcontext method to get the context in the parameters of Intent because ProfileFragment is a fragment and not an activit
                startActivity(intent);
                getActivity().finish();
            }
        });
        String user_id= firebaseAuth.getUid();  //storing user id of the current user to avoid making firebase instances again and again

//        firebaseStorage= FirebaseStorage.getInstance();
//       fetching the profile from firebase storage
//        getting the location of the profile image from firebaseStorage
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
                        Glide.with(getContext()).load(user.getImage_link()).placeholder(R.drawable.profile).into(profile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return view;
    }
}