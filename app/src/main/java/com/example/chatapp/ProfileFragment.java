package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


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
        return view;
    }
}