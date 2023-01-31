package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.Model.Message;
import com.example.chatapp.adapter.MessageAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        TextView receiver_name;
        ImageView back_button, send_button;
        EditText msg_box;
        RecyclerView chat_scroll_recyclerView;

        receiver_name=findViewById(R.id.name);
        back_button=findViewById(R.id.imageView2);
        send_button=findViewById(R.id.sendBtn);
        msg_box=findViewById(R.id.messageBox);
        chat_scroll_recyclerView=findViewById(R.id.recyclerView);

        String name= getIntent().getStringExtra("receiver");
        receiver_name.setText(name);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, HomeActivity.class );
                startActivity(intent);
            }
        });

        String receiver_id=getIntent().getStringExtra("receiver_id");
        String sender_id= FirebaseAuth.getInstance().getUid();  //we are senders
        String sender_room= sender_id+receiver_id;
        String receiver_room= receiver_id+sender_id;

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = msg_box.getText().toString();
                if (!msg.isEmpty()) {
                    Date date = new Date();
                    long time = date.getTime();
                    Message message = new Message(sender_id, msg, time);
                    //sending the msg to firebasedatabase
                    firebaseDatabase.getReference().child("chats")
                            .child(sender_room).child("messages").push() //push is creating a unique folder to hold each and every msg separately
                            .setValue(message)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    firebaseDatabase.getReference().child("chats")
                                            .child(receiver_room).child("messages")
                                            .push().setValue(message);
                                }
                            });

                    //to set/update the last msgs of chats
                    firebaseDatabase.getReference().child("chats").child(sender_room)
                                    .child("last_msg").setValue(message) //value is passed without " "
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            firebaseDatabase.getReference().child("chats")
                                                    .child(receiver_room).child("last_msg")
                                                    .setValue(message);
                                        }
                                    });


                    msg_box.setText("");
                }
            }
        });

        //Fetching the messages from the firebaseDatabase
        ArrayList<Message> messages = new ArrayList<>(); //datasource that has all the messages
        MessageAdapter msgAdapter= new MessageAdapter(MessageActivity.this, messages);
        chat_scroll_recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        chat_scroll_recyclerView.setAdapter(msgAdapter);

        firebaseDatabase.getReference().child("chats").child(sender_room)
                .child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            Message message= snapshot1.getValue(Message.class); //typeCasting
                            messages.add(message); //adding the msgs in the arraylist(datasource) to pass in the adapter
                        }
                        msgAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}