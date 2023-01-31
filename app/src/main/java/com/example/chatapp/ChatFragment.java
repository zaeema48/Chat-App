package com.example.chatapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Model.User;
import com.example.chatapp.adapter.ChatRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView=view.findViewById(R.id.recyclerViewOfChatFragment);
        ChatRecyclerAdapter adapter;
        ArrayList<User> Users= new ArrayList<>();
        adapter=new ChatRecyclerAdapter(getContext(), Users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));    //defining recycler view to scroll vertically
        recyclerView.setAdapter(adapter);

        //fetching user's datasource from firebase database
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) { //snapshot of a user in which all data items {user id, username, image link...} are present
                        Users.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            User user = snapshot1.getValue(User.class);
                            if(!user.getUser_id().equals(FirebaseAuth.getInstance().getUid()))
                            Users.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;
    }

}